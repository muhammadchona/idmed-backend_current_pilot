package mz.org.fgh.sifmoz.migration.entity.prescription

import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.clinicSector.ClinicSector
import mz.org.fgh.sifmoz.backend.dispenseMode.DispenseMode
import mz.org.fgh.sifmoz.backend.dispenseType.DispenseType
import mz.org.fgh.sifmoz.backend.doctor.Doctor
import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.duration.Duration
import mz.org.fgh.sifmoz.backend.episode.Episode
import mz.org.fgh.sifmoz.backend.episodeType.EpisodeType
import mz.org.fgh.sifmoz.backend.migrationLog.MigrationLog
import mz.org.fgh.sifmoz.backend.packagedDrug.PackagedDrug
import mz.org.fgh.sifmoz.backend.packagedDrug.PackagedDrugStock
import mz.org.fgh.sifmoz.backend.packaging.Pack
import mz.org.fgh.sifmoz.backend.patient.Patient
import mz.org.fgh.sifmoz.backend.patientIdentifier.PatientServiceIdentifier
import mz.org.fgh.sifmoz.backend.patientVisit.PatientVisit
import mz.org.fgh.sifmoz.backend.patientVisitDetails.PatientVisitDetails
import mz.org.fgh.sifmoz.backend.prescription.Prescription
import mz.org.fgh.sifmoz.backend.prescriptionDetail.PrescriptionDetail
import mz.org.fgh.sifmoz.backend.service.ClinicalService
import mz.org.fgh.sifmoz.backend.startStopReason.StartStopReason
import mz.org.fgh.sifmoz.backend.therapeuticLine.TherapeuticLine
import mz.org.fgh.sifmoz.backend.therapeuticRegimen.TherapeuticRegimen
import mz.org.fgh.sifmoz.migration.base.record.AbstractMigrationRecord
import mz.org.fgh.sifmoz.migration.base.record.MigratedRecord
import mz.org.fgh.sifmoz.migration.base.record.MigrationRecord

import java.time.LocalDate

class PrescriptionMigrationRecord extends AbstractMigrationRecord {

    private Integer id;


    private Date prescriptiondate;

    private String firstnamedoctor ;
    private String lastnamedoctor
    private int duration;

    private char modifiedprescription;

    private Date enddateprescription;

    private int dispensaTrimestral;

    private int dispensaSemestral;

    private String tipodoenca;

    private char tb;

    private char saaj;

    private char cpn;
    private char ccr;

    private String cliniccode;

    private String uuidopenmrs;

    private String patientid;

    private Date startdate;
    private Date stopdate;
    private String startnotes;
    private String startreason;
    private String stopreason;

    private String notesprescription;

    private char currentprescrtiption;

    private String therapeuticlinecode;
    private String therapeuticregimencode;

    //package
    private String reasonforpackagereturn;
    private boolean packagereturnedpack;
    private String modifiedpack;
    private Date datereceivedpack;
    private boolean stockreturnedpack;
    private Date dateleftpack;
    private Date pickupdatepack;
    private Date packdate
    private Integer weekssupply
    private String nextpickupdate
    private String modedispenseuuid

    //PackageDrug
    private Integer qtyinhand
    private String drugname
    private Date dispensedate
    private Integer stockid


    @Override
    List<MigrationLog> migrate() {
        List<MigrationLog> logs = new ArrayList<>();
        PatientServiceIdentifier psi = new PatientServiceIdentifier();
        Episode episode = new Episode();
        psi.setStartDate(this.prescriptiondate);
        psi.setPatient(Patient.findById(this.uuidopenmrs));
        ClinicalService clinicalService = ClinicalService.findByCode(this.tipodoenca);
        psi.setIdentifierType(clinicalService.getIdentifierType());
        Clinic clinic = Clinic.findByCode(cliniccode)
        psi.setClinic(clinic);
        Patient patient = Patient.get(this.uuidopenmrs);
        psi.setPatient(patient);
        psi.setService(clinicalService);
        List<PatientServiceIdentifier> listPSI = PatientServiceIdentifier.findAllWhere(patient: Patient.findById(this.uuidopenmrs))
        psi.setValue(this.patientid);
        if (listPSI.isEmpty()) {
            psi.setPrefered(true);
            PatientServiceIdentifier.withTransaction {
                psi.validate()
                if (!psi.hasErrors()) {
                    psi.save(flush: true)
                } else {
                    logs.addAll(generateUnknowMigrationLog(this, psi.getErrors().toString()))
                    return logs
                }
            }

            // A lista de prescricoes vem ordenada por  idprescricao e paciente
            // primeira prescricao
            //Cria o episodio

            episode.setClinic(clinic);
            episode.setEpisodeDate(this.prescriptiondate);
            String codeEpisodeType = this.stopdate == null ? "INICIO" : "FIM";
            episode.setEpisodeType(EpisodeType.findByCode(codeEpisodeType));
            StartStopReason startStopReason = StartStopReason.findByReason(this.stopdate == null ? this.startreason : this.stopreason);
            episode.setStartStopReason(startStopReason);
            episode.setCreationDate(this.prescriptiondate);
            ClinicSector clinicSector = ClinicSector.findByCode(getClinicSectorCode())
            episode.setClinicSector(clinicSector);
            episode.setNotes(this.stopdate == null ? this.startnotes : this.startnotes);
            episode.setReferralClinic(clinic);
            episode.setEpisodeDate(this.stopdate == null ? this.startdate : this.stopdate);
            episode.setPatientServiceIdentifier(psi);

            Episode.withTransaction {
                episode.validate()
                if (!episode.hasErrors()) {
                    episode.save(flush: true)
                } else {
                    logs.addAll(generateUnknowMigrationLog(this, episode.getErrors().toString()))
                    return logs
                }
            }

        } else if ("TARV".equalsIgnoreCase(this.tipodoenca)) {

            //patientServiceIdentifierService.updatePatientServiceIdentifier(patientid, psi.getValue());
            List<PatientServiceIdentifier> listPSIs = PatientServiceIdentifier.findAllWhere(patient: Patient.findById(psi.getValue()))
            for (PatientServiceIdentifier psiObj in listPSIs) {
                psiObj.setPrefered(false)
                psiObj.setValue(psi.getValue())
                psiObj.withTransaction {
                    psiObj.validate()
                    if (!psiObj.hasErrors()) {
                        psiObj.save(flush: true)
                    } else {
                        logs.addAll(generateUnknowMigrationLog(this, psi.getErrors().toString()))
                        return logs
                    }
                }
            }

            psi.setPrefered(true);
            psi.setValue(this.patientid);
            PatientServiceIdentifier.withTransaction {
                psi.validate()
                if (!psi.hasErrors()) {
                    psi.save(flush: true)
                } else {
                    logs.addAll(generateUnknowMigrationLog(this, psi.getErrors().toString()))
                    return logs
                }
            }
        }

        Prescription prescription = new Prescription();
        prescription.setClinic(clinic);
        prescription.setDoctor(Doctor.findByFirstnamesAndLastname(this.firstnamedoctor,this.lastnamedoctor));
        prescription.setModified(this.modifiedprescription == 'T' ? true : false);
        prescription.setExpiryDate(this.enddateprescription);
        prescription.setCurrent(this.currentprescrtiption == 'T' ? true : false);
        prescription.setDuration(Duration.findByWeeks(this.duration));
        prescription.setNotes(this.notesprescription)
        prescription.setPatientType("")
        prescription.setPatientStatus("")


        PrescriptionDetail prescriptionDetail = new PrescriptionDetail();
        prescriptionDetail.setPrescription(prescription)
        TherapeuticLine therapeuticLine = TherapeuticLine.findByCode(this.therapeuticlinecode)
        prescriptionDetail.setTherapeuticLine(therapeuticLine)
        prescriptionDetail.setTherapeuticRegimen(TherapeuticRegimen.findByCode(therapeuticregimencode))
        prescriptionDetail.setDispenseType(DispenseType.findByCode(getTipoDispensa()).getId())
        prescriptionDetail.setPrescription(prescription)

        PatientVisitDetails patientVisitDetails = new PatientVisitDetails();
        patientVisitDetails.setClinic(clinic);
        patientVisitDetails.setPrescription(prescription);
        patientVisitDetails.setEpisode(episode)
        List<PatientVisitDetails> patientVisitDetailsList = new ArrayList<>()
        patientVisitDetailsList.add(patientVisitDetails)

        PatientVisit patientVisit = new PatientVisit();
        patientVisit.setClinic(clinic);
        patientVisit.setVisitDate(this.prescriptiondate);
        patientVisit.setPatient(patient);
        patientVisit.setPatientVisitDetails(patientVisitDetailsList)

        Pack pack = new Pack()
        pack.setReasonForPackageReturn(this.reasonforpackagereturn)
        pack.setPickupDate(this.pickupdatepack)
        pack.setPackageReturned(this.packagereturnedpack ? 1 : 0)
        pack.setModified(this.modifiedpack == "T")
        pack.setDateReceived(this.datereceivedpack)
        pack.setStockReturned(this.stockreturnedpack ? 1 : 0)
        pack.setDateLeft(this.dateleftpack)
        pack.setWeeksSupply(this.weekssupply)
        pack.setPackDate(this.packdate)
        //TODO:rever
        pack.setPatientVisitDetails(patientVisitDetailsList)
        def pattern = "yyyy-MM-dd"
        Date nxtPickDt = LocalDate.parse(this.nextpickupdate, pattern)
        pack.setNextPickUpDate(nxtPickDt)
        pack.setPatientVisitDetails(patientVisitDetails)
        pack.setDispenseMode(DispenseMode.findById(modedispenseuuid))
        pack.setClinic(clinic)

        //PackageDrug
        Drug drug = Drug.findByName(drugname)
        PackagedDrug packagedDrug = new PackagedDrug()
        packagedDrug.setQuantitySupplied(qtyinhand)
        packagedDrug.setNextPickUpDate(nextpickupdate)
        packagedDrug.setDrug(drug)
        packagedDrug.setCreationDate(this.dispensedate)
        packagedDrug.setPack(pack)

        //PackageDrugStock
        PackagedDrugStock packagedDrugStock = new PackagedDrugStock()
        packagedDrugStock.setQuantitySupplied(qtyinhand)
        packagedDrugStock.setDrug(drug)
        packagedDrugStock.setStock(MigrationLog.findBySourceIdAndEntity(stockid, "Stock").getiDMEDId())
        packagedDrugStock.setCreationDate(this.dispensedate)
        packagedDrugStock.setPackagedDrug(packagedDrug)


        //marcar migrationstatus prescricao e package

        Prescription.withTransaction {
            prescription.validate()
            pack.validate()
            patientVisitDetails.validate()
            patientVisit.validate()
            if (!prescription.hasErrors()) {
                // 1-prescr ,pack,patientvisit
                prescription.save(flush: true)
                pack.save(flush: true)
                patientVisit.save(flush: true)

            } else {
                logs.addAll(generateUnknowMigrationLog(this, prescription.getErrors().toString()))
                return logs
            }
        }

        return logs;
    }

    @Override
    public void updateIDMEDInfo() {

    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public String getEntityName() {
        return "Prescription";
    }

    @Override
    public void generateUnknowMigrationLog(MigrationRecord record, String message) {

    }

    @Override
    public MigratedRecord initMigratedRecord() {
        return new Prescription();
    }

    @Override
    public Prescription getMigratedRecord() {
        return (Prescription) super.getMigratedRecord();
    }


    String getClinicSectorCode() {
        if (this.cpn == 'T') return "CPN";
        if (this.tb == 'T') return "TB";
        if (this.ccr == 'T') return "CCR";
        if (this.saaj == 'T') return "SAAJ";
        return "NORMAL";
    }

    String getTipoDispensa() {
        if (this.dispensaSemestral == 1 && this.dispensaTrimestral == 1) return "DM"
        else if (this.dispensaSemestral == 1) return "DS"
        else if (this.dispensaTrimestral == 1) return "DT"
        return "DA";
    }

}
