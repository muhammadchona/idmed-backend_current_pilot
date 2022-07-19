package sifmoz.backend.tansreference

import grails.gorm.transactions.Transactional
import groovy.util.logging.Slf4j
import mz.org.fgh.sifmoz.backend.Task.ISynchronizerTask
import mz.org.fgh.sifmoz.backend.Task.SynchronizerTask
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.clinicSector.ClinicSector
import mz.org.fgh.sifmoz.backend.episode.Episode
import mz.org.fgh.sifmoz.backend.episode.IEpisodeService
import mz.org.fgh.sifmoz.backend.patientIdentifier.PatientServiceIdentifier
import mz.org.fgh.sifmoz.backend.patientVisitDetails.IPatientVisitDetailsService
import mz.org.fgh.sifmoz.backend.patientVisitDetails.PatientVisitDetails
import mz.org.fgh.sifmoz.backend.prescriptionDrug.PrescribedDrug
import mz.org.fgh.sifmoz.backend.provincialServer.ProvincialServer
import mz.org.fgh.sifmoz.backend.restUtils.RestProvincialServerMobileClient
import mz.org.fgh.sifmoz.backend.service.ClinicalService
import mz.org.fgh.sifmoz.backend.therapeuticLine.TherapeuticLine
import mz.org.fgh.sifmoz.backend.utilities.Utilities
import mz.org.fgh.sifmoz.backend.tansreference.PatientTransReferenceService
import org.apache.commons.logging.LogFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@Transactional
@EnableScheduling
@Slf4j
class RestPostPatientCentralMobileService extends SynchronizerTask implements ISynchronizerTask {

    private static final NAME = "PostPatientCentralMobile"

    @Autowired
    IPatientVisitDetailsService visitDetailsService
    IEpisodeService episodeService
    @Autowired
    PatientTransReferenceService patientTransReferenceService
    RestProvincialServerMobileClient restProvincialServerClient = new RestProvincialServerMobileClient()

    static lazyInit = false

    private static final Logger LOGGER = LoggerFactory
            .getLogger("RestMobileDataPostPatient");

    private static final String FORMAT_STRING = '| %1$-10s |  %2$-40s|  %3$-30s|';

    private static final String MESSAGE = String.format(
            FORMAT_STRING,
            "Id Episodio",
            "Nome",
            "NID");

    //@Scheduled(cron = "0 0 */2 * * ?")
    void execute() {

        if(this.isProvincialOrUs()) {
            List <PatientTransReferenceType> patientTransReferenceTypes = PatientTransReferenceType.findAllByCodeInList(Arrays.asList('REFERENCIA_FP','REFERENCIA_DC'))
            List<PatientTransReference> patientsTransferees = PatientTransReference.findAllBySyncStatusAndOperationTypeInList('P',patientTransReferenceTypes)

            ProvincialServer provincialServer = ProvincialServer.findByCodeAndDestination("Test" , "mobile")

            char SyncP = 'P'
            char SyncS = 'S'
            char False = 'F'

            LOGGER.info("Iniciando o Envio de Pacientes" )
            LOGGER.info(MESSAGE)

            for (PatientTransReference pt : patientsTransferees) {
                String message = String.format(FORMAT_STRING,
                        pt.matchId ,
                        pt.patient.firstNames,
                        pt.identifier.value)
                LOGGER.info("Processando" +message);
                try {

                    SyncTempPatient syncTempPatient = new SyncTempPatient()

                    Episode episode = episodeService.getLastInitialEpisodeByIdentifier(pt.identifier.id)
                    PatientVisitDetails lastVisitDetails = visitDetailsService.getLastVisitByEpisodeId(episode.id)

                    syncTempPatient.setId(pt.matchId)
                    syncTempPatient.setAccountstatus(false)
                    syncTempPatient.setCellphone(pt.identifier.patient.cellphone)
                    syncTempPatient.setDateofbirth(pt.identifier.patient.dateOfBirth)


                    // destination passou a ser uuid de fp ou dispensa comunitaria

                    if(pt.operationType.code.equals("REFERENCIA_FP")) {
                        Clinic clinicDestination = Clinic.findByUuid(pt.destination)
                        syncTempPatient.setClinicname(clinicDestination.clinicName)
                        syncTempPatient.setClinicuuid(clinicDestination.uuid)
                        syncTempPatient.setClinic(clinicDestination.matchId)
                    } else if(pt.operationType.code.equals("REFERENCIA_DC")) {
                        ClinicSector clinicSectorDestination = ClinicSector.findByUuid(pt.destination)
                        syncTempPatient.setClinicname(clinicSectorDestination.clinicName)
                        syncTempPatient.setClinicuuid(clinicSectorDestination.uuid)
                        syncTempPatient.setClinic(0)
                    }
                    syncTempPatient.setMainclinic(0)
                    syncTempPatient.setMainclinicname(pt.origin.clinicName)
                    syncTempPatient.setMainclinicuuid(pt.origin.uuid)
                    syncTempPatient.setFirstnames(pt.identifier.patient.firstNames)
                    syncTempPatient.setHomephone(pt.identifier.patient.alternativeCellphone)
                    syncTempPatient.setLastname(pt.identifier.patient.lastNames)
                    syncTempPatient.setProvince(pt.identifier.patient.province.description)
                    syncTempPatient.setPatientid(pt.identifier.value)
                    if(pt.identifier.patient.gender == 'Masculino') {
                        syncTempPatient.setSex("M")
                    } else {
                        syncTempPatient.setSex("F")
                    }
                    syncTempPatient.setSyncstatus(SyncP)
                    syncTempPatient.setWorkphone('')
                    syncTempPatient.setAddress1(pt.identifier.patient.address)
                    syncTempPatient.setAddress2(pt.identifier.patient.addressReference)
                    syncTempPatient.setAddress3('')
                    syncTempPatient.setRace('')
                    syncTempPatient.setUuidopenmrs(pt.identifier.patient.hisUuid)
                    syncTempPatient.setDatainiciotarv(pt.identifier.startDate)
                    syncTempPatient.setSyncuuid(UUID.randomUUID().toString())
                    syncTempPatient.setPrescriptiondate(lastVisitDetails.prescription.prescriptionDate)
                    syncTempPatient.setDuration(lastVisitDetails.prescription.duration.weeks)
                    syncTempPatient.setPrescriptionenddate(lastVisitDetails.prescription.expiryDate)
                    syncTempPatient.setRegimenome(lastVisitDetails.prescription.prescriptionDetails[0].therapeuticRegimen.regimenScheme)
                  //  syncTempPatient.setLinhanome(lastVisitDetails.prescription.prescriptionDetails[0].therapeuticLine.description)
                    if (lastVisitDetails.prescription.prescriptionDetails[0].therapeuticLine.description.contains("1")) {
                        syncTempPatient.setLinhanome("1ª Linha")
                    } else if (lastVisitDetails.prescription.prescriptionDetails[0].therapeuticLine.description.contains("2")) {
                        syncTempPatient.setLinhanome("2ª Linha")
                    } else if (lastVisitDetails.prescription.prescriptionDetails[0].therapeuticLine.description.contains("3")) {
                        syncTempPatient.setLinhanome("3ª Linha")
                    }
                    if (lastVisitDetails.prescription.prescriptionDetails[0].dispenseType.code == 'DM') {
                        syncTempPatient.setDispensasemestral(0)
                        syncTempPatient.setDispensatrimestral(0)
                    } else if (lastVisitDetails.prescription.prescriptionDetails[0].dispenseType.code == 'DT') {
                        syncTempPatient.setDispensasemestral(0)
                        syncTempPatient.setDispensatrimestral(1)
                    } else if (lastVisitDetails.prescription.prescriptionDetails[0].dispenseType.code == 'DS') {
                        syncTempPatient.setDispensasemestral(1)
                        syncTempPatient.setDispensatrimestral(0)
                    }
                    syncTempPatient.setPrescriptionid(lastVisitDetails.prescription.prescriptionSeq)
                    syncTempPatient.setPrescricaoespecial('F')
                    syncTempPatient.setMotivocriacaoespecial('')
                    if (!lastVisitDetails.prescription.getPrescribedDrugs().isEmpty()) {

                        Map<String, Object> pd = new HashMap<String, Object>()
                        ArrayList listPD = new ArrayList()

                        for (PrescribedDrug prescribedDrugs : lastVisitDetails.prescription.getPrescribedDrugs()) {
                            pd.put("drugId", prescribedDrugs.getDrug().getId())
                            pd.put("drugcode", prescribedDrugs.getDrug().getFnmCode())
                            pd.put("timesperday", prescribedDrugs.getTimesPerDay())
                            listPD.add(pd);
                        }
                        syncTempPatient.setJsonprescribeddrugs(listPD.toString());
                        syncTempPatient.setEstadopaciente('Activo');
                        syncTempPatient.setExclusaopaciente(false);
                        syncTempPatient.setModified(False)
                        def obj = Utilities.parseToJSON(syncTempPatient)
                     def response =  restProvincialServerClient.postRequestProvincialServerClient(provincialServer,"/sync_temp_patients",obj)
                        if (Integer.parseInt(response) == HttpURLConnection.HTTP_CREATED) {
                        // def ptToUpdate = PatientTransReference.findById(pt.id)
                            pt.syncStatus = SyncS
                           patientTransReferenceService.save(pt)
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
