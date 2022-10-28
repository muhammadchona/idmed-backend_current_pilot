package mz.org.fgh.sifmoz.backend.migration.entity.pack

import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.convertDateUtils.ConvertDateUtils
import mz.org.fgh.sifmoz.backend.dispenseMode.DispenseMode
import mz.org.fgh.sifmoz.backend.episode.Episode
import mz.org.fgh.sifmoz.backend.migration.base.record.AbstractMigrationRecord
import mz.org.fgh.sifmoz.backend.migration.base.record.MigratedRecord
import mz.org.fgh.sifmoz.backend.migrationLog.MigrationLog
import mz.org.fgh.sifmoz.backend.packaging.Pack
import mz.org.fgh.sifmoz.backend.patient.Patient
import mz.org.fgh.sifmoz.backend.patientVisit.PatientVisit
import mz.org.fgh.sifmoz.backend.patientVisitDetails.PatientVisitDetails
import mz.org.fgh.sifmoz.backend.prescription.Prescription
import mz.org.fgh.sifmoz.backend.service.ClinicalService
import org.apache.commons.lang3.StringUtils

class PackageMigrationRecord extends AbstractMigrationRecord{

    private Integer packid
    private int prescriptionid
    private int episodeid
    private String reasonforpackagereturn
    private boolean packagereturnedpack
    private String modifiedpack
    private Date datereceivedpack
    private boolean stockreturnedpack
    private Date dateleftpack
    private Date pickupdatepack
    private Date packdate
    private Integer weekssupply
    private Date nextpickupdate
    private String modedispenseuuid
    private Integer patientid
    private String clinicuuid

    @Override
    List<MigrationLog> migrate() {
        List<MigrationLog> logs = new ArrayList<>()
        Pack.withTransaction {
            MigrationLog patientMigrationLog = MigrationLog.findBySourceIdAndSourceEntityAndIDMEDIdIsNotNull(this.patientid, "Patient")
            MigrationLog prescriptionMigrationLog = MigrationLog.findBySourceIdAndSourceEntityAndIDMEDIdIsNotNull(this.prescriptionid, "Prescription")
            MigrationLog episodeMigrationLog
            if (this.episodeid > 0) {
                episodeMigrationLog = MigrationLog.findBySourceIdAndSourceEntityAndIDMEDIdIsNotNull(this.episodeid, "Episode")
            }
            if (patientMigrationLog == null) throw new RuntimeException("MigrationLog of Patient " + this.patientid + " not found.")
            if (prescriptionMigrationLog == null) throw new RuntimeException("MigrationLog of Prescription " + this.prescriptionid + " not found.")
            if (episodeMigrationLog == null && this.episodeid > 0) throw new RuntimeException("MigrationLog of Episode " + this.episodeid + " not found.")

            Episode episode
            if (episodeMigrationLog != null) {
                episode = Episode.findById(episodeMigrationLog.getiDMEDId())
            } else {
                episode = Episode.findByNotes("Episodio criado para prescricoes sem episodio no iDART")
            }
            if (episode == null)  throw new RuntimeException("Nao existe um episodio para associar a dispensa.")

            Prescription prescription = Prescription.findById(prescriptionMigrationLog.getiDMEDId())

            Clinic clinic = Clinic.findByUuid(clinicuuid)
            if (clinic == null) clinic = Clinic.findByMainClinic(true)

            Patient patient = Patient.findById(patientMigrationLog.getiDMEDId())

            Pack pack = getMigratedRecord()

            if (isDuplicatedPack(this.pickupdatepack, patient)) throw new RuntimeException("O paciente ja possui um registo de dispensa na data [" + this.pickupdatepack + "], impossivel gravar esta dispensa.")

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


            PatientVisit patientVisit = new PatientVisit()
            patientVisit.setClinic(clinic)
            patientVisit.setVisitDate(this.pickupdatepack)
            patientVisit.setPatient(patient)

            PatientVisitDetails patientVisitDetails = new PatientVisitDetails()
            patientVisitDetails.setClinic(clinic)
            patientVisitDetails.setPrescription(prescription)
            patientVisitDetails.setEpisode(episode)
            Set<PatientVisitDetails> patientVisitDetailsSet = new HashSet<>();
            patientVisitDetailsSet.add(patientVisitDetails)
            patientVisitDetails.setPatientVisit(patientVisit)
            patientVisitDetails.setPack(pack)

            pack.setPatientVisitDetails(patientVisitDetailsSet)

            //Date nxtPickDt = ConvertDateUtils.createDate(StringUtils.replace(this.nextpickupdate, " ", "-"), "dd-MMM-yyyy")
            pack.setNextPickUpDate(this.nextpickupdate)
            pack.setPatientVisitDetails(patientVisitDetailsSet)
            DispenseMode dsmode = DispenseMode.findById(modedispenseuuid)
            pack.setDispenseMode(dsmode == null ? DispenseMode.findByCode("US_FP_HN") : dsmode)
            pack.setClinic(clinic)

            pack.validate()
            patientVisit.validate()


            if (!patientVisit.hasErrors()) {
                patientVisit.save(flush: true)
            } else {
                logs.addAll(generateUnknowMigrationLog(this, patientVisit.getErrors().toString()))
                return logs
            }
            if (!pack.hasErrors()) {
                pack.save(flush: true)
                //pack.setPatientVisitDetails(new HashSet<PatientVisitDetails>())
                patientVisitDetails.save(flush: true)
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
        return this.packid
    }

    @Override
    String getEntityName() {
        return "package"
    }

    @Override
    Pack getMigratedRecord() {
        return super.getMigratedRecord() as Pack
    }

    @Override
    MigratedRecord initMigratedRecord() {
        return new Pack()
    }

    boolean isDuplicatedPack(Date pickUpDate, Patient patient) {
        PatientVisit patientVisit = PatientVisit.findByPatientAndVisitDate(patient, pickupdatepack)
        return patientVisit != null
    }
}
