package mz.org.fgh.sifmoz.backend.tansreference

import grails.gorm.transactions.Transactional
import groovy.util.logging.Slf4j
import mz.org.fgh.sifmoz.backend.clinicSector.ClinicSector
import mz.org.fgh.sifmoz.backend.task.SynchronizerTask
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.convertDateUtils.ConvertDateUtils
import mz.org.fgh.sifmoz.backend.dispenseMode.DispenseMode
import mz.org.fgh.sifmoz.backend.dispenseType.DispenseType
import mz.org.fgh.sifmoz.backend.doctor.Doctor
import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.duration.Duration
import mz.org.fgh.sifmoz.backend.episode.Episode
import mz.org.fgh.sifmoz.backend.episode.IEpisodeService
import mz.org.fgh.sifmoz.backend.packagedDrug.IPackagedDrugService
import mz.org.fgh.sifmoz.backend.packagedDrug.PackagedDrug
import mz.org.fgh.sifmoz.backend.packaging.IPackService
import mz.org.fgh.sifmoz.backend.packaging.Pack
import mz.org.fgh.sifmoz.backend.patientIdentifier.IPatientServiceIdentifierService
import mz.org.fgh.sifmoz.backend.patientIdentifier.PatientServiceIdentifier
import mz.org.fgh.sifmoz.backend.patientVisit.IPatientVisitService
import mz.org.fgh.sifmoz.backend.patientVisit.PatientVisit
import mz.org.fgh.sifmoz.backend.patientVisitDetails.IPatientVisitDetailsService
import mz.org.fgh.sifmoz.backend.patientVisitDetails.PatientVisitDetails
import mz.org.fgh.sifmoz.backend.prescription.IPrescriptionService
import mz.org.fgh.sifmoz.backend.prescription.Prescription
import mz.org.fgh.sifmoz.backend.prescriptionDetail.IPrescriptionDetailService
import mz.org.fgh.sifmoz.backend.prescriptionDetail.PrescriptionDetail
import mz.org.fgh.sifmoz.backend.prescriptionDrug.IPrescribedDrugService
import mz.org.fgh.sifmoz.backend.prescriptionDrug.PrescribedDrug
import mz.org.fgh.sifmoz.backend.provincialServer.ProvincialServer
import mz.org.fgh.sifmoz.backend.restUtils.RestProvincialServerMobileClient
import mz.org.fgh.sifmoz.backend.startStopReason.StartStopReason
import mz.org.fgh.sifmoz.backend.therapeuticLine.TherapeuticLine
import mz.org.fgh.sifmoz.backend.therapeuticRegimen.TherapeuticRegimen
import org.apache.http.entity.StringEntity
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@Transactional
@EnableScheduling
@Slf4j
class RestGetDispensesCentralMobileService extends SynchronizerTask {

    private static final NAME = "PostPatientCentralMobile"
    @Autowired
    IPatientVisitDetailsService visitDetailsService
    IEpisodeService episodeService
    IPatientServiceIdentifierService patientServiceIdentifierService
    IPatientVisitService patientVisitService
    IPrescriptionService prescriptionService
    IPrescriptionDetailService prescriptionDetailService
    IPrescribedDrugService prescribedDrugService
    IPackService packService
    IPackagedDrugService packagedDrugService
    @Autowired
    IPatientTransReferenceService patientTransReferenceService
    RestProvincialServerMobileClient restProvincialServerClient = new RestProvincialServerMobileClient()

    private static final Logger LOGGER = LoggerFactory
            .getLogger("RestSendMobileDataGetDispense");

    private static final String FORMAT_STRING = '| %1$-10s |  %2$-40s|  %3$-30s|';

    private static final String MESSAGE = String.format(
            FORMAT_STRING,
            "Id Dispensa",
            "Nome",
            "NID");

    static lazyInit = false


    @Scheduled(fixedDelay = 60000L)
    void execute() {
        Pack.withTransaction {
            if (!this.isProvincial()) {
                Clinic clinic = Clinic.findByUuid(this.getUsOrProvince())
                //  ProvincialServer provincialServer = ProvincialServer.findByCodeAndDestination(clinic.getProvince().code, "MOBILE")
                ProvincialServer provincialServer = ProvincialServer.findByCodeAndDestination("12", "MOBILE")
                String urlPath = "/sync_temp_dispense?mainclinicuuid=eq." + clinic.getUuid() + "&syncstatus=eq.P" + "&order=pickupdate.desc";
                LOGGER.info("Iniciando a Busca de Dispensas")
                def response = restProvincialServerClient.getRequestProvincialServerClient(provincialServer, urlPath)
                LOGGER.info(MESSAGE)
                for (Object dispense : response) {
                    PatientServiceIdentifier patientServiceIdentifier = PatientServiceIdentifier.findByValue(dispense.getAt('patientid').toString())
                    def clinicuuid = dispense.getAt('clinicuuid').toString()
                    def dispenseMode = DispenseMode.findByCode('US_FP_FHN')
                    def priviteClinic = Clinic.findByUuid(clinicuuid)

                    if (priviteClinic)
                        dispenseMode = DispenseMode.findByCode('DD_FP')
                    else {
                        def clinicSector = ClinicSector.findByUuid(clinicuuid)
                        if (clinicSector?.clinicSectorType?.code?.equalsIgnoreCase("PROVEDOR"))
                            dispenseMode = DispenseMode.findByCode('DC_PS')
                        if (clinicSector?.clinicSectorType?.code?.equalsIgnoreCase("APE"))
                            dispenseMode = DispenseMode.findByCode('DC_APE')
                        if (clinicSector?.clinicSectorType?.code?.equalsIgnoreCase("CLINICA_MOVEL"))
                            dispenseMode = DispenseMode.findByCode('DC_CM_HN')
                        if (clinicSector?.clinicSectorType?.code?.equalsIgnoreCase("BRIGADA_MOVEL"))
                            dispenseMode = DispenseMode.findByCode('DC_BM_HN')
                    }

                    if (patientServiceIdentifier != null) {
                        def startStop = StartStopReason.findAllByIsStartReason(true)
                        Episode episode = Episode.findAllByPatientServiceIdentifier(patientServiceIdentifier, [sort: 'episodeDate', order: 'desc']).first()
                        def prescriprionDate = ConvertDateUtils.createDate(dispense.getAt("date").toString(), "yyyy-MM-dd")
                        def patientVisitDetailsList = PatientVisitDetails.findAllByEpisode(episode)

                        if (patientVisitDetailsList.isEmpty()) {
                            episode = Episode.findAllByPatientServiceIdentifierAndStartStopReasonInList(patientServiceIdentifier, startStop, [sort: 'episodeDate', order: 'desc']).first()
                            patientVisitDetailsList = PatientVisitDetails.findAllByEpisode(episode)
                        }

                        def lastPrescriprion = Prescription.findByIdInListAndPrescriptionDate(patientVisitDetailsList.prescription.id, prescriprionDate)
                        if (!lastPrescriprion)
                            lastPrescriprion = createIdmedPrescription(dispense, patientVisitDetailsList.prescription.id)

                        Pack idmedPack = createIdmedPack(dispense, lastPrescriprion, episode, dispenseMode)
                        createIdmedVisit(dispense, lastPrescriprion, idmedPack, episode, patientServiceIdentifier)

                        def path = "/sync_temp_dispense?mainclinicuuid=eq." + clinic.getUuid() + "&id=eq." + dispense.getAt("id")
                        println(path)
                        String obj = '{"syncstatus":"U"}'
                        def convertedObj = new StringEntity(obj, "UTF-8");

                        restProvincialServerClient.patchRequestProvincialServerClient(provincialServer, path, convertedObj)
                    } else {
                        LOGGER.info("Servico de Saude Nao encontrado Para o paciente com o nid:" + dispense.getAt("patientid").toString());
                    }
                }
            }
        }
    }

    private Prescription createIdmedPrescription(Object dispense, List prescriprionList) {

        def prescriprionDate = ConvertDateUtils.createDate(dispense.getAt("date").toString(), "yyyy-MM-dd")
        def lastPrescription = Prescription.findAllByIdInList(prescriprionList, [sort: 'prescriptionDate', order: 'desc']).first()

        Prescription prescription = new Prescription()
        prescription.beforeInsert()
        prescription.setPrescriptionDate(prescriprionDate)
        prescription.setExpiryDate(dispense.getAt("expiryDate") != null ? ConvertDateUtils.createDate(dispense.getAt("expiryDate").toString(), "yyyy-MM-dd") : null)
        prescription.setNotes(dispense.getAt("notes").toString())
        if (dispense.getAt("modified").toString().contains("T")) {
            prescription.setModified(true)
        } else {
            prescription.setModified(false)
        }
        prescription.setPrescriptionSeq(dispense.getAt("prescriptionid").toString())
        prescription.setDoctor(lastPrescription.doctor)
        prescription.setClinic(Clinic.findByUuid(dispense.getAt("mainclinicuuid").toString()))
        prescription.setPatientStatus(dispense.getAt('tipodt').toString())
        prescription.setPatientType(lastPrescription.patientType)
        prescription.setDuration(Duration.findByWeeks(dispense.getAt('duration').toString() as int))

        PrescriptionDetail prescriptionDetail = new PrescriptionDetail()
        prescriptionDetail.beforeInsert()
        prescriptionDetail.setReasonForUpdate(dispense.getAt("reasonforupdate").toString())
        if (dispense.getAt("dispensatrimestral").toString().contains("1")) {
            prescriptionDetail.setDispenseType(DispenseType.findByCode("DT"))
        } else if (dispense.getAt("dispensasemestral").toString().contains("1")) {
            prescriptionDetail.setDispenseType(DispenseType.findByCode("DS"))
        } else {
            prescriptionDetail.setDispenseType(DispenseType.findByCode("DM"))
        }

        if (dispense.getAt("linhanome").toString().contains("1")) {
            prescriptionDetail.setTherapeuticLine(TherapeuticLine.findByCode("1"));
        } else if (dispense.get("linhanome").toString().contains("2")) {
            prescriptionDetail.setTherapeuticLine(TherapeuticLine.findByCode("2"));
        } else if (dispense.get("linhanome").toString().contains("3")) {
            prescriptionDetail.setTherapeuticLine(TherapeuticLine.findByCode("3"));
        }
        prescriptionDetail.setTherapeuticRegimen(TherapeuticRegimen.findByRegimenScheme(dispense.getAt("regimenome").toString()))
        prescriptionDetail.setPrescription(prescription)

        PrescribedDrug prescribedDrug = setPrescribedDrug(dispense, prescription)

        prescription.addToPrescriptionDetails(prescriptionDetail)
        prescription.addToPrescribedDrugs(prescribedDrug)
        prescription.save()
        return prescription
    }

    private Pack createIdmedPack(Object dispense, Prescription prescription, Episode episode, DispenseMode dispenseMode) {
        def pickUpDate = ConvertDateUtils.createDate(dispense.getAt("pickupdate").toString(), "yyyy-MM-dd")
        def patientVisitDetailsList = PatientVisitDetails.findAllByEpisode(episode)

        def lastPack = Pack.findByPickupDateAndIdInList(pickUpDate, patientVisitDetailsList.pack.id)

        if (lastPack) {
            addDrugToPack(dispense, lastPack)
            return lastPack
        } else {
            lastPack = Pack.findAllByIdInList(patientVisitDetailsList.pack.id).first()
            Pack dispenseIdmed = new Pack()
            dispenseIdmed.beforeInsert()
            dispenseIdmed.setClinic(prescription.getClinic())
            dispenseIdmed.setModified(false)
            dispenseIdmed.setDateReceived(ConvertDateUtils.createDate(dispense.getAt("pickupdate").toString(), "yyyy-MM-dd"))
            dispenseIdmed.setNextPickUpDate(ConvertDateUtils.getUtilDateFromString(dispense.get("dateexpectedstring").toString(), "dd MMM yyyy"))
            dispenseIdmed.setPickupDate(ConvertDateUtils.createDate(dispense.getAt("pickupdate").toString(), "yyyy-MM-dd"))
            // check
            dispenseIdmed.setDateLeft()
            dispenseIdmed.setPackDate(ConvertDateUtils.createDate(dispense.getAt("pickupdate").toString(), "yyyy-MM-dd"))
            dispenseIdmed.syncStatus = 'R'
            dispenseIdmed.providerUuid = lastPack.providerUuid
            dispenseIdmed.setDispenseMode(dispenseMode)
            dispenseIdmed.setWeeksSupply(dispense.getAt("weekssupply") == null || dispense.getAt("weekssupply") == "" ? 0 : Integer.valueOf(dispense.getAt("weekssupply").toString()))
            dispenseIdmed.setStockReturned(0)
            dispenseIdmed.setPackageReturned(0)
            PackagedDrug packagedDrug = setPackagedDrug(dispense, dispenseIdmed)
            dispenseIdmed.addToPackagedDrugs(packagedDrug)
            dispenseIdmed.save()

            return dispenseIdmed
        }


    }

    private PatientVisit createIdmedVisit(Object dispense, Prescription prescription, Pack dispenseIdmed, Episode episode, PatientServiceIdentifier patientServiceIdentifier) {
        def startStop = StartStopReason.findAllByCodeIlike("%REFERIDO_%")
        def pickUpDate = ConvertDateUtils.createDate(dispense.getAt("pickupdate").toString(), "yyyy-MM-dd")
        def patientVisit = PatientVisit.findByVisitDateAndPatient(pickUpDate, patientServiceIdentifier.patient)


        if (!patientVisit) {
            patientVisit = new PatientVisit()
            patientVisit.beforeInsert()
            patientVisit.setClinic(prescription.getClinic())
            patientVisit.setPatient(patientServiceIdentifier.patient)
            patientVisit.setVisitDate(pickUpDate)
        }

        PatientVisitDetails patientVisitDetails = new PatientVisitDetails()
        patientVisitDetails.beforeInsert()
        patientVisitDetails.setPack(dispenseIdmed)
        patientVisitDetails.setPrescription(prescription)
        patientVisitDetails.setEpisode(Episode.findByPatientServiceIdentifierAndStartStopReasonInList(patientServiceIdentifier, startStop))
        patientVisitDetails.setPatientVisit(patientVisit)
        patientVisitDetails.setClinic(prescription.getClinic())
        patientVisitDetails.setEpisode(episode)
        patientVisitDetails.setPatientVisit(patientVisit)

        patientVisit.addToPatientVisitDetails(patientVisitDetails)
        patientVisit.save()
        return patientVisit
    }

    private PrescribedDrug setPrescribedDrug(Object dispense, Prescription prescription) {
        PrescribedDrug prescribedDrug = new PrescribedDrug()
        prescribedDrug.beforeInsert()
        prescribedDrug.setPrescription(prescription)
        prescribedDrug.setDrug(Drug.findByName(dispense.getAt('drugname').toString()))
        prescribedDrug.setModified(false)
        prescribedDrug.setAmtPerTime(dispense.getAt('amountpertime') != null ? Integer.parseInt(dispense.getAt('amountpertime').toString()) : 1)
        prescribedDrug.setForm('Dia')
        prescribedDrug.setTimesPerDay(1)

        //Quantidade Levadae e Prescrita
        String inHand = dispense.get("qtyinhand").toString();

        if (!inHand.isEmpty())
            inHand = inHand.replace('(', ' ').replace(')', ' ').replaceAll("\\s+", "");
        else
            inHand = "0";
        prescribedDrug.setQtyPrescribed(Integer.parseInt(inHand))
        return prescribedDrug
    }

    private PackagedDrug setPackagedDrug(Object dispense, Pack dispenseIdmed) {
        //Quantidade Levadae e Prescrita
        String inHand = dispense.get("qtyinhand").toString();

        if (!inHand.isEmpty())
            inHand = inHand.replace('(', ' ').replace(')', ' ').replaceAll("\\s+", "");
        else
            inHand = "0";

        PackagedDrug packagedDrug = new PackagedDrug()
        packagedDrug.beforeInsert()
        packagedDrug.setDrug(Drug.findByName(dispense.getAt('drugname').toString()))
        packagedDrug.setQuantitySupplied(Double.parseDouble(inHand))
        packagedDrug.setPack(dispenseIdmed)
        packagedDrug.setToContinue(true)
        return packagedDrug
    }

    private void addDrugToPrescription(Object dispense, Prescription prescription) {
        PrescribedDrug prescribedDrug = setPrescribedDrug(dispense, prescription)
        prescribedDrugService.save(prescribedDrug)
    }

    private void addDrugToPack(Object dispense, Pack dispenseIdmed) {
        PackagedDrug packagedDrug = setPackagedDrug(dispense, dispenseIdmed)
        packagedDrugService.save(packagedDrug)
    }

}
