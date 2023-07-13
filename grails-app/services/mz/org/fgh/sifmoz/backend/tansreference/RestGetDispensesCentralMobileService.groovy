package mz.org.fgh.sifmoz.backend.tansreference

import grails.gorm.transactions.Transactional
import groovy.util.logging.Slf4j
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
                //  String urlPath = "/sync_temp_dispense?mainclinicuuid=eq."+clinic.getUuid()+"&syncstatus=eq.P" + "&order=pickupdate.desc";
                String urlPath = "/sync_temp_dispense?syncstatus=eq.P" + "&order=pickupdate.desc";
                LOGGER.info("Iniciando a Busca de Dispensas")
                def response = restProvincialServerClient.getRequestProvincialServerClient(provincialServer, urlPath)
                LOGGER.info(MESSAGE)
                for (Object dispense : response) {

//                    String message = String.format(FORMAT_STRING,
////                            dispense.getAt("id").toString(),
//                            dispense.getAt("patientfirstname").toString(),
//                            dispense.getAt("patientid").toString())
//                    LOGGER.info("Processando" + message);

                    PatientServiceIdentifier patientServiceIdentifier = PatientServiceIdentifier.findByValue(dispense.getAt('patientid').toString())

                    if (patientServiceIdentifier != null) {
                        Episode episode = Episode.findByPatientServiceIdentifier(patientServiceIdentifier)

                        PatientVisit visit = PatientVisit.findByVisitDate(ConvertDateUtils.createDate(dispense.getAt("pickupdate").toString(), "yyyy-MM-dd"))

                        if (visit == null) {
                            Prescription idmedPrescription = createIdmedPrescription(dispense)
                            Pack idmedPack = createIdmedPack(dispense, idmedPrescription)
                            createIdmedVisit(dispense, idmedPrescription, idmedPack, episode, patientServiceIdentifier)
                        } else {
                            Prescription prescriptionEx = visit.getPatientVisitDetails().getAt(0).getPrescription()
                            Pack dispenseEx = visit.getPatientVisitDetails().getAt(0).getPack()
                            addDrugToPrescription(dispense, prescriptionEx)
                            addDrugToPack(dispense, dispenseEx)
                        }

                        def path = "/sync_temp_dispense?id=eq." + dispense.getAt("id")
                        println(path)
                        String obj = '{"syncstatus":"U"}'

                        restProvincialServerClient.patchRequestProvincialServerClient(provincialServer, path, obj)
                    } else {
                        LOGGER.info("Servico de Saude Nao encontrado Para o paciente com o nid:" + dispense.getAt("patientid").toString());
                    }
                }
            }
        }
    }

    private Prescription createIdmedPrescription(Object dispense) {
        Prescription prescription = new Prescription()
        prescription.setPrescriptionDate(ConvertDateUtils.createDate(dispense.getAt("date").toString(), "yyyy-MM-dd"))
        prescription.setExpiryDate(dispense.getAt("expiryDate") != null ? ConvertDateUtils.createDate(dispense.getAt("expiryDate").toString(), "yyyy-MM-dd") : null)
        prescription.setNotes(dispense.getAt("notes").toString())
        if (dispense.getAt("modified").toString().contains("T")) {
            prescription.setModified(true)
        } else {
            prescription.setModified(false)
        }
        prescription.setPrescriptionSeq(dispense.getAt("prescriptionid").toString())
        prescription.setDoctor(Doctor.findById("ff8081817f080727017f084062e50009"))
        prescription.setClinic(Clinic.findByUuid(dispense.getAt("mainclinicuuid").toString()))
        prescription.setPatientStatus(dispense.getAt('tipodt'))
        prescription.setPatientType()
        prescription.setDuration(Duration.findByWeeks(dispense.getAt('duration').toString()))

        PrescriptionDetail prescriptionDetail = new PrescriptionDetail()
        prescriptionDetail.setReasonForUpdate(dispense.getAt("reasonforupdate"))
        if (dispense.getAt("dispensatrimestral").toString().contains("1")) {
            prescriptionDetail.setDispenseType(DispenseType.findByCode("DT"));
        } else if (dispense.get("dispensasemestral").toString().contains("1")) {
            prescriptionDetail.setDispenseType(DispenseType.findByCode("DS"));
        } else {
            prescriptionDetail.setDispenseType(DispenseType.findByCode("DM"));
        }

        if (dispense.getAt("linhanome").toString().contains("1")) {
            prescriptionDetail.setTherapeuticLine(TherapeuticLine.findByCode("1"));
        } else if (dispense.get("linhanome").toString().contains("2")) {
            prescriptionDetail.setTherapeuticLine(TherapeuticLine.findByCode("2"));
        } else if (dispense.get("linhanome").toString().contains("3")) {
            prescriptionDetail.setTherapeuticLine(TherapeuticLine.findByCode("3"));
        }

        //  TherapeuticRegimen.findByCode(dispense.getAt("regimenome"))
        prescriptionDetail.setTherapeuticRegimen(TherapeuticRegimen.findByCode("TDF+3TC+DTG"))
        prescriptionDetail.setPrescription(prescription)

        PrescribedDrug prescribedDrug = setPrescribedDrug(dispense, prescription)

        prescription.setPrescriptionDetails(new HashSet<>(Arrays.asList(prescriptionDetail)))
        prescription.setPrescribedDrugs(new HashSet<>(Arrays.asList(prescribedDrug)))
        return prescriptionService.save(prescription)
    }

    private Pack createIdmedPack(Object dispense, Prescription prescription) {
        Pack lastPack = Pack.last()
        Pack dispenseIdmed = new Pack()
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
        dispenseIdmed.setDispenseMode(DispenseMode.findByCode("DD_FP"))
        dispenseIdmed.setWeeksSupply(dispense.getAt("weekssupply") == null || dispense.getAt("weekssupply") == "" ? 0 : Integer.valueOf(dispense.getAt("weekssupply").toString()))
        dispenseIdmed.setStockReturned(0)
        dispenseIdmed.setPackageReturned(0)
        PackagedDrug packagedDrug = setPackagedDrug(dispense, dispenseIdmed)

        dispenseIdmed.setPackagedDrugs(new HashSet<>(Arrays.asList(packagedDrug)))
        return packService.save(dispenseIdmed)
    }

    private PatientVisit createIdmedVisit(Object dispense, Prescription prescription, Pack dispenseIdmed, Episode episode, PatientServiceIdentifier patientServiceIdentifier) {
        PatientVisit patientVisit = new PatientVisit()
        patientVisit.setClinic(prescription.getClinic())
        patientVisit.setPatient(patientServiceIdentifier.patient)
        patientVisit.setVisitDate(ConvertDateUtils.createDate(dispense.getAt("pickupdate").toString(), "yyyy-MM-dd"))

        StartStopReason startStop = StartStopReason.findByCode("REFERIDO_PARA")

        PatientVisitDetails patientVisitDetails = new PatientVisitDetails()
        patientVisitDetails.setPack(dispenseIdmed)
        patientVisitDetails.setPrescription(prescription)
        patientVisitDetails.setEpisode(Episode.findByPatientServiceIdentifierAndStartStopReason(patientServiceIdentifier, startStop))
        patientVisitDetails.setPatientVisit(patientVisit)
        patientVisitDetails.setClinic(prescription.getClinic())
        patientVisitDetails.setEpisode(episode)

        patientVisitDetails.setPatientVisit(patientVisit)
        patientVisit.setPatientVisitDetails(new HashSet<>(Arrays.asList(patientVisitDetails)))
        return patientVisitService.save(patientVisit)
    }

    private PrescribedDrug setPrescribedDrug(Object dispense, Prescription prescription) {
        PrescribedDrug prescribedDrug = new PrescribedDrug()
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
        packagedDrug.setDrug(Drug.findByName(dispense.getAt('drugname').toString()))
        packagedDrug.setQuantitySupplied(Double.parseDouble(inHand))
        packagedDrug.setPack(dispenseIdmed)
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
