package mz.org.fgh.sifmoz.backend.packaging


import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import mz.org.fgh.sifmoz.backend.healthInformationSystem.HealthInformationSystem
import mz.org.fgh.sifmoz.backend.patient.Patient
import mz.org.fgh.sifmoz.backend.patientVisit.PatientVisit
import mz.org.fgh.sifmoz.backend.patientVisitDetails.PatientVisitDetails
import mz.org.fgh.sifmoz.backend.patientVisitDetails.PatientVisitDetailsService
import mz.org.fgh.sifmoz.backend.restUtils.RestOpenMRSClient
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
                    if (patient.his == null ){
                        pack.setSyncStatus('N' as char)
                        pack.save()
                        return
                    }
                    HealthInformationSystem his = HealthInformationSystem.get(patient.his.id)
                    String urlBase = his.interoperabilityAttributes.find { it.interoperabilityType.code == "URL_BASE" }.value
                    String convertToJson = restPost.createOpenMRSDispense(pack, patient)
                    String responsePost = restOpenMRSClient.requestOpenMRSClient(pack.providerUuid, convertToJson, urlBase, "encounter", requestMethod_POST)
                    if (responsePost.contains('Green')) {
                        pack.setSyncStatus('S' as char)
                        pack.save()
                    }
                } catch (Exception e) {
                    e.printStackTrace()
                } finally {
                    continue
                }
            }
        }
    }
}
