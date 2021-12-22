package mz.org.fgh.sifmoz.backend.packaging


import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import mz.org.fgh.sifmoz.backend.healthInformationSystem.HealthInformationSystem
import mz.org.fgh.sifmoz.backend.patient.Patient
import mz.org.fgh.sifmoz.backend.patientVisit.PatientVisit
import mz.org.fgh.sifmoz.backend.patientVisitDetails.PatientVisitDetails
import mz.org.fgh.sifmoz.backend.restUtils.RestOpenMRSClient
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

import java.text.SimpleDateFormat

@Slf4j
@CompileStatic
@EnableScheduling
class RestPackService {

    PackService packService

    static lazyInit = false

    @Scheduled(fixedDelay = 30000L)
    void schedulerRequestRunning() {
        System.out.println('saving {} at {}' + new SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(new Date()))
        Pack.withTransaction {
            List<Pack> packList = Pack.findAll().findAll {it.syncStatus == 'R'}

            for (Pack pack : packList) {
                try {
                    RestOpenMRSClient restPost = new RestOpenMRSClient()
                    PatientVisitDetails patientVisitDetails = PatientVisitDetails.get(pack.patientVisitDetails.id)
                    PatientVisit patientVisit = PatientVisit.get(patientVisitDetails.patientVisit.id)
                    Patient patient = Patient.get(patientVisit.patient.id)
                    HealthInformationSystem his = HealthInformationSystem.get(patient.his.id)
                    String urlBase = his.interoperabilityAttributes.find { it.interoperabilityType.code == "URL_BASE" }.value
                    String convertToJson = restPost.createOpenMRSFILA(pack, patient)
                    println(urlBase)
                    println(convertToJson)
                    String responsePost = restClientPost(convertToJson, urlBase,"encounter" )
                    if (responsePost.contains('Green')){
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

    def restClientPost(String object, String urlBase, String urlPath) {
        String username = "admin"
        String password = "eSaude123"
        String restUrl = urlBase.concat(urlPath)
        String result = ""
        int code = 200
        try {
            String userCredentials = new StringBuffer(username).append(":").append(password).toString()
            String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()))
            println(restUrl)
            println(basicAuth)
            URL siteURL = new URL(restUrl)
            HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection()
            connection.setRequestProperty("Authorization", basicAuth)
            connection.setRequestMethod("POST")
            connection.setRequestProperty("Content-Type", "application/json; utf-8")
            connection.setDoInput(true)
            connection.setDoOutput(true);
//            connection.setConnectTimeout(3000)
            // Send post request
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream())
            wr.writeBytes(object)
            wr.flush()
            wr.close()

//            connection.connect()
            code = connection.getResponseCode()
            connection.disconnect()
            if (code == 201) {
                result = "-> Green <-\t" + "Code: " + code;
            } else {
                result = "-> Yellow <-\t" + "Code: " + code;
            }
        } catch (Exception e) {
            result = "-> Red <-\t" + "Wrong domain - Exception: " + e.getMessage();
        }
        println(result)
        return result
    }
}
