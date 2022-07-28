package mz.org.fgh.sifmoz.backend.packaging


import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import mz.org.fgh.sifmoz.backend.healthInformationSystem.HealthInformationSystem
import mz.org.fgh.sifmoz.backend.patient.Patient
import mz.org.fgh.sifmoz.backend.patientVisit.PatientVisit
import mz.org.fgh.sifmoz.backend.patientVisitDetails.PatientVisitDetails
import mz.org.fgh.sifmoz.backend.patientVisitDetails.PatientVisitDetailsService
import mz.org.fgh.sifmoz.backend.restUtils.RestOpenMRSClient
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

import java.text.SimpleDateFormat

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
            List<Pack> packList = Pack.findAll().findAll { it.syncStatus == 'R' }

            for (Pack pack : packList) {
                System.out.println('saving {} at {} ' + pack + ' ' + new SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(new Date()))

                try {
                    RestOpenMRSClient restPost = new RestOpenMRSClient()
                    PatientVisitDetails patientVisitDetails = patientVisitDetailsService.getByPack(pack)
                    System.out.println('Packing details ' + patientVisitDetails)
                    PatientVisit patientVisit = PatientVisit.get(patientVisitDetails.patientVisit.id)
                    Patient patient = Patient.get(patientVisit.patient.id)
                    if (patient.his == null ) return
                    HealthInformationSystem his = HealthInformationSystem.get(patient.his.id)
                    String urlBase = his.interoperabilityAttributes.find { it.interoperabilityType.code == "URL_BASE" }.value
                    String convertToJson = restPost.createOpenMRSFILA(pack, patient)
                    println(urlBase)
                    println(convertToJson)
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
