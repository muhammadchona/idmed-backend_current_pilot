package mz.org.fgh.sifmoz.backend.clinicSector

import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.provincialServer.ProvincialServer
import mz.org.fgh.sifmoz.backend.restUtils.RestProvincialServerMobileClient
import mz.org.fgh.sifmoz.backend.tansreference.PatientTransReference
import mz.org.fgh.sifmoz.backend.task.SynchronizerTask
import mz.org.fgh.sifmoz.backend.utilities.Utilities
import org.apache.http.HttpResponse
import org.apache.http.entity.StringEntity
import org.springframework.scheduling.annotation.Scheduled

@Transactional
class RestClinicSectorService extends SynchronizerTask {

    RestProvincialServerMobileClient restProvincialServerClient = new RestProvincialServerMobileClient()
    IClinicSectorService clinicSectorService

    def serviceMethod() {

    }

    @Override
    @Scheduled(fixedDelay = 30000L)
    void execute() {
        ClinicSector.withTransaction {
            if (!this.isProvincial()) {
                Clinic clinicLoged = Clinic.findByUuid(this.getUsOrProvince())
                List<ClinicSector> clinicSectorList = ClinicSector.findAllBySyncStatus('P')

                // Alterar para a linha abaixo quando for em Producao
//            ProvincialServer provincialServer = ProvincialServer.findByCodeAndDestination(clinicLoged.code , "MOBILE")
                ProvincialServer provincialServer = ProvincialServer.findByCodeAndDestination("12", "MOBILE")

                char SyncP = 'P'
                char SyncS = 'S'
                char False = 'F'

                for (ClinicSector clinicSector : clinicSectorList) {
                    try {
                        if(postClinicFirst(clinicSector.clinic, provincialServer)) {

                            def sectorTypeID = 0

                            if (clinicSector.clinicSectorType.code.equalsIgnoreCase(("PARAGEM_UNICA")))
                                sectorTypeID = 1
                            else if (clinicSector.clinicSectorType.code.equalsIgnoreCase(("PROVEDOR")))
                                sectorTypeID = 2
                            else if (clinicSector.clinicSectorType.code.equalsIgnoreCase(("APE")))
                                sectorTypeID = 3
                            else if (clinicSector.clinicSectorType.code.equalsIgnoreCase(("CLINICA_MOVEL")))
                                sectorTypeID = 4
                            if (clinicSector.clinicSectorType.code.equalsIgnoreCase(("BRIGADA_MOVEL")))
                                sectorTypeID = 5

                            String clinicJSONObject = "{\"id\": \"" + getRandomNumber() + "\", \"code\": \"" + clinicSector.getCode() + "\", \"sectorname\": \"" + clinicSector.getDescription() + "\", "
                            +"\"clinicsectortype\":\"" + sectorTypeID + "\", \"telephone\":\"\", "
                            +"\"clinic\":\"" + clinicSector.getClinic().getUuid() + "\",\"clinicuuid\":\"" + clinicSector.getClinic().getUuid() + "\",\"uuid\":\"" + clinicSector.getUuid() + "\"}";

                            StringEntity inputAddDispense = new StringEntity(clinicJSONObject, "UTF-8");
                            inputAddDispense.setContentType("application/json");

                            def response = restProvincialServerClient.postRequestProvincialServerClient(provincialServer, "/clinicsector", inputAddDispense)
                            if (!response.contains("Wrong")) {
                                clinicSector.syncStatus = SyncS
                                clinicSectorService.save(clinicSector)
                            }
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

    def postClinicFirst(Clinic clinic, provincialServer){
        String facilitytype = "Unidade Sanitária";

        String clinicJSONObject = "{\"id\": \"" + getRandomNumber() + "\", \"mainclinic\": \"" + false + "\", \"notes\": \"" + clinic.getNotes() + "\", "
        + "\"code\":\"" + clinic.getCode() + "\", \"telephone\":\"" + clinic.getTelephone() + "\", \"facilitytype\":\"" + facilitytype + "\", "
        + "\"clinicname\":\"" + clinic.getClinicName() + "\",\"province\":\"" + clinic?.province?.description + "\",\"district\":\"" + clinic?.district?.description + "\", "
        + "\"subdistrict\":\"-\",\"uuid\":\"" + clinic.getUuid() + "\"}";

        StringEntity inputAddDispense = new StringEntity(clinicJSONObject, "UTF-8");

        inputAddDispense.setContentType("application/json");

        def response = restProvincialServerClient.postRequestProvincialServerClient(provincialServer, "/clinic", inputAddDispense)

        return !response.contains("Wrong")

    }

    def getRandomNumber(){
        Random r = new Random()
        int low = 60000000
        int high = 1000000000;
        int genId = r.nextInt(high - low) + low;
        return genId
    }
}
