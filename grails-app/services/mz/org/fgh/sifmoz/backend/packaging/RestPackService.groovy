package mz.org.fgh.sifmoz.backend.packaging


import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import mz.org.fgh.sifmoz.backend.episode.Episode
import mz.org.fgh.sifmoz.backend.healthInformationSystem.HealthInformationSystem
import mz.org.fgh.sifmoz.backend.openmrsErrorLog.OpenmrsErrorLog
import mz.org.fgh.sifmoz.backend.patient.Patient
import mz.org.fgh.sifmoz.backend.patientIdentifier.PatientServiceIdentifier
import mz.org.fgh.sifmoz.backend.patientVisit.PatientVisit
import mz.org.fgh.sifmoz.backend.patientVisitDetails.PatientVisitDetails
import mz.org.fgh.sifmoz.backend.patientVisitDetails.PatientVisitDetailsService
import mz.org.fgh.sifmoz.backend.restUtils.RestOpenMRSClient
import mz.org.fgh.sifmoz.backend.service.ClinicalService
import org.apache.logging.log4j.LogManager
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

import java.text.SimpleDateFormat
import java.util.logging.Logger

@Slf4j
@CompileStatic
@EnableScheduling
class RestPackService {

    RestOpenMRSClient restOpenMRSClient = new RestOpenMRSClient()
    PatientVisitDetailsService patientVisitDetailsService
    final String requestMethod_POST = "POST"
    final String requestMethod_PUT = "PUT"
    final String requestMethod_PATCH = "PATCH"
    final String requestMethod_GET = "GET"

    static lazyInit = false

    @Scheduled(fixedDelay = 30000L)
    void schedulerRequestRunning() {
        Pack.withTransaction {
            List<Pack> packList = Pack.findAllWhere(syncStatus: 'R' as char)

            for (Pack pack : packList) {
                try {
                    RestOpenMRSClient restPost = new RestOpenMRSClient()
                    PatientVisitDetails patientVisitDetails = patientVisitDetailsService.getByPack(pack)
                    PatientVisit patientVisit = PatientVisit.get(patientVisitDetails.patientVisit.id)
                    Patient patient = Patient.get(patientVisit.patient.id)
                    if (patient.his == null) {
                        pack.setSyncStatus('N' as char)
                        pack.save()
                        return
                    }
                    HealthInformationSystem his = HealthInformationSystem.get(patient.his.id)
                    String urlBase = his.interoperabilityAttributes.find { it.interoperabilityType.code == "URL_BASE" }.value
                    String convertToJson = restPost.createOpenMRSDispense(pack, patient)
                    String responsePost = restOpenMRSClient.requestOpenMRSClient(pack.providerUuid, convertToJson, urlBase, "encounter", requestMethod_POST)
                    if (responsePost.startsWith('-> Green')) {
                        pack.setSyncStatus('S' as char)
                        pack.save()

                        deleteErrorLog(patientVisitDetails)
                    } else {
                        saveErrorLog(pack, patient, responsePost, convertToJson)
                    }
                } catch (Exception e) {
                    e.printStackTrace()
                } finally {
                    continue
                }
            }
        }
    }

    def saveErrorLog(Pack pack, Patient patient, String errorResponse, String jSONRequest) {
        try {
            OpenmrsErrorLog errorLog = new OpenmrsErrorLog()
            errorLog.beforeInsert()
            PatientVisitDetails patientVisitDetails = PatientVisitDetails.findByPack(pack)
            Episode episode = Episode.get(patientVisitDetails.episode.id)
            PatientServiceIdentifier identifier = PatientServiceIdentifier.get(episode.patientServiceIdentifier.id)
            ClinicalService service = ClinicalService.get(identifier.service.id)
            errorLog.patient = patient.id
            errorLog.nid = identifier.value
            errorLog.servicoClinico = service.description
            errorLog.patientVisitDetails = patientVisitDetails.id
            errorLog.pickupDate = pack.pickupDate
            errorLog.returnPickupDate = null
            errorLog.errorDescription = errorResponse
            errorLog.jsonRequest = jSONRequest
            errorLog.save()
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    def deleteErrorLog(PatientVisitDetails patientVisitDetails) {
        OpenmrsErrorLog patientVisitDetailsinLog = OpenmrsErrorLog.findByPatientVisitDetails(patientVisitDetails.id)
        if (patientVisitDetailsinLog) {
            patientVisitDetailsinLog.delete()
        }
    }

}
