package mz.org.fgh.sifmoz.backend.migration.entity.prescription

import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.clinicSector.ClinicSector
import mz.org.fgh.sifmoz.backend.convertDateUtils.ConvertDateUtils
import mz.org.fgh.sifmoz.backend.dispenseMode.DispenseMode
import mz.org.fgh.sifmoz.backend.dispenseType.DispenseType
import mz.org.fgh.sifmoz.backend.doctor.Doctor
import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.duration.Duration
import mz.org.fgh.sifmoz.backend.episode.Episode
import mz.org.fgh.sifmoz.backend.episodeType.EpisodeType
import mz.org.fgh.sifmoz.backend.migration.base.record.AbstractMigrationRecord
import mz.org.fgh.sifmoz.backend.migration.base.record.MigratedRecord
import mz.org.fgh.sifmoz.backend.migration.common.MigrationError
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
import mz.org.fgh.sifmoz.backend.stock.Stock
import mz.org.fgh.sifmoz.backend.therapeuticLine.TherapeuticLine
import mz.org.fgh.sifmoz.backend.therapeuticRegimen.TherapeuticRegimen
import mz.org.fgh.sifmoz.backend.utilities.Utilities
import org.apache.commons.lang3.StringUtils


class PrescriptionMigrationRecord extends AbstractMigrationRecord {

    private Integer id
    private Date prescriptiondate
    private String firstnamedoctor
    private Integer patientid
    private String nid;
    private String lastnamedoctor
    private int durationprescription
    private char modifiedprescription
    private Date enddateprescription
    private int dispensaTrimestral
    private int dispensaSemestral
    private String tipodoenca
    private char tb
    private char saaj
    private char cpn
    private char ccr
    private String clinicuuid
    private String uuidopenmrs
    private Date startdate
    private Date stopdate
    private String startnotes
    private String stopnotes
    private String startreason
    private String stopreason
    private String notesprescription
    private char currentprescrtiption
    private String therapeuticlinecode
    private String therapeuticregimencode
    //package
    private Integer packid
    private String reasonforpackagereturn
    private boolean packagereturnedpack
    private String modifiedpack
    private Date datereceivedpack
    private boolean stockreturnedpack
    private Date dateleftpack
    private Date pickupdatepack
    private Date packdate
    private Integer weekssupply
    private String nextpickupdate
    private String modedispenseuuid
    //PackageDrug
    private String qtyinhand
    private String drugname
    private Date dispensedate
    private Integer stockid
    private Integer episodeid


    @Override
    List<MigrationLog> migrate() {
        List<MigrationLog> logs = new ArrayList<>()
        Prescription.withTransaction {
            MigrationLog patientMigrationLog = MigrationLog.findBySourceIdAndSourceEntityAndIDMEDIdIsNotNull(this.patientid, "Patient")
            if (patientMigrationLog == null) throw new RuntimeException("MigrationLog of Patient " + this.patientid + " not found.")
            ClinicalService clinicalService = ClinicalService.findByCode(this.tipodoenca)
            Clinic clinic = Clinic.findByMainClinic(true)
            Patient patient = Patient.findById(patientMigrationLog.getiDMEDId())
            PatientServiceIdentifier psi = PatientServiceIdentifier.findByPatientAndService(patient, clinicalService)

            if (psi == null ) {
                psi = new PatientServiceIdentifier()
                psi.setStartDate(this.prescriptiondate)
                psi.setPatient(patient);
                psi.setClinic(clinic)
                psi.setService(clinicalService)
                psi.setIdentifierType(clinicalService.getIdentifierType())
                psi.setValue(this.nid)
                psi.setState("Activo")
                psi.setPrefered(clinicalService.isTarv())
                psi.setValue(this.nid)

                List<PatientServiceIdentifier> listPSI = PatientServiceIdentifier.findAllByPatient(psi.getPatient())

                if (!Utilities.listHasElements(listPSI)) {
                    psi.setPrefered(true)
                } else if (psi.isPrefered()) {
                    for (PatientServiceIdentifier psiObj : listPSI) {
                        psiObj.setPrefered(false)
                        psiObj.setValue(psi.getValue())
                        psiObj.setState("Activo")
                        psiObj.validate()
                        psiObj.save(flush: true)
                    }
                }
            } else {
                psi.setEpisodes(Episode.findAllByPatientServiceIdentifier(psi) as Set<Episode>)
            }

            Episode episode
            if (!psi.hasEpisodes()) {
                episode = createEpisodeFromMigrationData(clinic, psi)

            } else {
                MigrationLog episodeMigrationLog = MigrationLog.findBySourceIdAndSourceEntityAndIDMEDIdIsNotNull(this.episodeid, "Episode")
                if (episodeMigrationLog != null) {
                    episode = Episode.findById(episodeMigrationLog.getiDMEDId())
                } else
                if (episode == null) {
                    episode = createEpisodeFromMigrationData(clinic, psi)
                }
            }

            EpisodeMigrationRecord episodeMigrationRecord = createEpisodeMigrationRecord(episode)

            Prescription prescription = getMigratedRecord()
            prescription.setClinic(clinic)

            Doctor doctor = Doctor.findByFirstnamesAndLastname("Generic", "Provider")
            if (doctor == null) doctor = Doctor.findByFirstnamesAndLastname("Provedor", "Desconhecido")
            if (doctor == null) doctor = Doctor.findByFirstnamesAndLastname("Clinico", "Generico")
            prescription.setDoctor(doctor)

            prescription.setModified(this.modifiedprescription == 'T' ? true : false)
            prescription.setExpiryDate(this.enddateprescription)
            prescription.setCurrent(this.currentprescrtiption == 'T' ? true : false)
            prescription.setDuration(Duration.findByWeeks(this.durationprescription))
            if (prescription.duration == null) {
                DispenseType dispenseType = DispenseType.findByCode(getTipoDispensa())
                if (dispenseType.isDM()) {
                    prescription.setDuration(Duration.findByWeeks(4))
                } else if (dispenseType.isDT()) {
                    prescription.setDuration(Duration.findByWeeks(12))
                } else if (dispenseType.isDS()) {
                    prescription.setDuration(Duration.findByWeeks(24))
                }
                else prescription.setDuration(Duration.findByWeeks(4))
                prescription.setNotes("A duracao da prescricao foi atribuida devido a problemas de qualidade de dados")
            } else {
                prescription.setNotes(this.notesprescription)
            }
            prescription.setPrescriptionDate(this.prescriptiondate)
            prescription.setPatientType("")
            prescription.setPatientStatus("")

            PrescriptionDetail prescriptionDetail = new PrescriptionDetail()
            prescriptionDetail.setPrescription(prescription)
            TherapeuticLine therapeuticLine = getLinhaTerapeutica(this.therapeuticlinecode)
            prescriptionDetail.setTherapeuticLine(therapeuticLine)
            TherapeuticRegimen regimen = TherapeuticRegimen.findByCode(StringUtils.trim(therapeuticregimencode))
            prescriptionDetail.setTherapeuticRegimen(regimen)
            prescriptionDetail.setDispenseType(DispenseType.findByCode(getTipoDispensa()))
            prescription.setPrescriptionDetails(new ArrayList<>() as Set<PrescriptionDetail>)
            prescription.getPrescriptionDetails().add(prescriptionDetail)

            PatientVisit patientVisit = new PatientVisit()
            patientVisit.setClinic(clinic)
            patientVisit.setVisitDate(this.prescriptiondate)
            patientVisit.setPatient(psi.getPatient())

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
            pack.setPickupDate(this.pickupdatepack)

            PackMigrationRecord packM = new PackMigrationRecord()
            packM.setId(this.packid)
            packM.setMigratedRecord(pack)


            PatientVisitDetails patientVisitDetails = new PatientVisitDetails()
            patientVisitDetails.setClinic(clinic)
            patientVisitDetails.setPrescription(prescription)
            patientVisitDetails.setEpisode(episode)
            Set<PatientVisitDetails> patientVisitDetailsSet = new HashSet<>();
            patientVisitDetailsSet.add(patientVisitDetails)
            patientVisitDetails.setPatientVisit(patientVisit)
            patientVisitDetails.setPack(pack)

            pack.setPatientVisitDetails(patientVisitDetailsSet)

            Date nxtPickDt = ConvertDateUtils.createDate(StringUtils.replace(this.nextpickupdate, " ", "-"), "dd-MMM-yyyy")
            pack.setNextPickUpDate(nxtPickDt)
            pack.setPatientVisitDetails(patientVisitDetailsSet)
            DispenseMode dsmode = DispenseMode.findById(modedispenseuuid)
            pack.setDispenseMode(dsmode == null ? DispenseMode.findByCode("US_FP_HN") : dsmode)
            pack.setClinic(clinic)

            prescription.validate()
            pack.validate()
            patientVisit.validate()
            if (!Utilities.stringHasValue(psi.id)) {
                psi.validate()
                if (!psi.hasErrors()) {
                    psi.save(flush: true)
                } else {
                    logs.addAll(generateUnknowMigrationLog(this, psi.getErrors().toString()))
                    return logs
                }
            }
            if (!Utilities.stringHasValue(episode.id)) {
                episode.validate()
                if (!episode.hasErrors()) {
                    episode.save(flush: true)
                    episodeMigrationRecord.setAsMigratedSuccessfully(getRestService())
                } else {
                    logs.addAll(generateUnknowMigrationLog(this, episode.getErrors().toString()))
                    return logs
                }
            }
            if (!prescription.hasErrors()) {
                prescription.save(flush: true)
            } else {
                logs.addAll(generateUnknowMigrationLog(this, prescription.getErrors().toString()))
                return logs
            }

            if (!patientVisit.hasErrors()) {
                patientVisit.save(flush: true)
            } else {
                logs.addAll(generateUnknowMigrationLog(this, patientVisit.getErrors().toString()))
                return logs
            }
            if (!pack.hasErrors()) {
                pack.save(flush: true)
            } else {
                logs.addAll(generateUnknowMigrationLog(this, pack.getErrors().toString()))
                return logs
            }
        }
        return logs
    }

    @Override
    void updateIDMEDInfo() {

    }

    @Override
    int getId() {
        return this.id
    }

    @Override
    String getEntityName() {
        return "Prescription"
    }

    @Override
    MigratedRecord initMigratedRecord() {
        return new Prescription()
    }

    @Override
    Prescription getMigratedRecord() {
        return (Prescription) super.getMigratedRecord()
    }


    String getClinicSectorCode() {
        if (this.cpn == 'T') return "CPN"
        if (this.tb == 'T') return "TB"
        if (this.ccr == 'T') return "CCR"
        if (this.saaj == 'T') return "SAAJ"
        return "NORMAL"
    }

    String getTipoDispensa() {
        if (this.dispensaSemestral == 1 && this.dispensaTrimestral == 1) return "DM"
        else if (this.dispensaSemestral == 1) return "DS"
        else if (this.dispensaTrimestral == 1) return "DT"
        return "DM"
    }

    TherapeuticLine getLinhaTerapeutica(String linhaTerap) {

        if (linhaTerap.toString().contains("1")) {
            return TherapeuticLine.findByCode("1")
        } else if (linhaTerap.toString().contains("2")) {
            return TherapeuticLine.findByCode("2")
        } else if (linhaTerap.toString().contains("3")) {
            return TherapeuticLine.findByCode("3")
        } else if (linhaTerap.toString().contains("ALT")) {
            return TherapeuticLine.findByCode("1_ALT")
        }
    }

    private Episode createEpisodeFromMigrationData(Clinic clinic, PatientServiceIdentifier psi) {
        Episode episode = new Episode()
        episode.setClinic(clinic)
        episode.setEpisodeDate(this.prescriptiondate)
        String codeEpisodeType = this.stopdate == null ? "INICIO" : "FIM"
        episode.setEpisodeType(EpisodeType.findByCode(codeEpisodeType))
        //StartStopReason startStopReason = StartStopReason.findByReason(this.stopdate == null ? this.startreason : this.stopreason)
        String reason = this.stopdate == null ? this.startreason : this.stopreason

        StartStopReason startStopReason = StartStopReason.findByReasonIlike(this.stopdate == null ? "%"+this.startreason+"%" : "%"+this.stopreason+"%")
        if (startStopReason == null) {
            List<StartStopReason> startStopReasonList = StartStopReason.all
            for (StartStopReason startStopReason1 : startStopReasonList) {
                if (Utilities.compareStringIgnoringAccents(startStopReason1.getReason(), reason)) {
                    startStopReason = startStopReason1
                    break
                }
            }
            if (startStopReason == null) {
                for (StartStopReason startStopReason1 : startStopReasonList) {
                    if (Utilities.stripAccents(reason).toLowerCase().contains(Utilities.stripAccents(startStopReason1.getReason()).toLowerCase())) {
                        startStopReason = startStopReason1
                        break
                    }
                }
            }
            if (startStopReason == null) throw new RuntimeException("Nao foi possivel identificar o motivo do episodio correspondente a ["+ reason + "]")
        }
        episode.setStartStopReason(startStopReason)
        episode.setCreationDate(new Date())
        ClinicSector clinicSector = ClinicSector.findByCode(getClinicSectorCode())
        episode.setClinicSector(clinicSector)
        episode.setNotes(this.stopdate == null ? this.startnotes : this.stopnotes)
        episode.setEpisodeDate(this.stopdate == null ? this.startdate : this.stopdate)
        if (!Utilities.stringHasValue(episode.getNotes()) && this.startdate != null) {
            episode.setNotes(Utilities.stringHasValue(this.startreason) ? this.startreason : this.stopreason)
        }
        episode.setPatientServiceIdentifier(psi)

        return episode
    }

    private EpisodeMigrationRecord createEpisodeMigrationRecord(Episode episode) {
        EpisodeMigrationRecord episodeM = new EpisodeMigrationRecord()
        episodeM.setId(this.episodeid)
        episodeM.setMigratedRecord(episode)
        return episodeM
    }
}