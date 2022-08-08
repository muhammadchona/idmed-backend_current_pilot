package mz.org.fgh.sifmoz.backend.migration.entity.prescription

import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.migration.base.record.AbstractMigrationRecord
import mz.org.fgh.sifmoz.backend.migration.base.record.MigratedRecord
import mz.org.fgh.sifmoz.backend.migrationLog.MigrationLog
import mz.org.fgh.sifmoz.backend.packagedDrug.PackagedDrug
import mz.org.fgh.sifmoz.backend.packagedDrug.PackagedDrugStock
import mz.org.fgh.sifmoz.backend.packaging.Pack
import mz.org.fgh.sifmoz.backend.prescription.Prescription
import mz.org.fgh.sifmoz.backend.prescriptionDrug.PrescribedDrug
import mz.org.fgh.sifmoz.backend.stock.Stock

public class PrescribedDrugsMigrationRecord extends AbstractMigrationRecord {

    private Integer id;


    private Integer amtpertime;

    private Integer drug;

    private Integer prescription;

    private Integer timesperday;

    private String modified;

    private String takeperiod;
    private Integer prescribeddrugsindex


    public void setId(Integer id) {
        this.id = id;
    }


    @Override
    public List<MigrationLog> migrate() {
        List<MigrationLog> logs = new ArrayList<>()
        PrescribedDrug.withTransaction {

            MigrationLog prescriptionMigrationLog = MigrationLog.findBySourceIdAndSourceEntityAndIDMEDIdIsNotNull(this.prescription, "Prescription")
            if (prescriptionMigrationLog == null) throw new RuntimeException("Não foi encontrado o respectivo log da migracao da prescricao")

            Prescription savedPrescription = Prescription.findById(prescriptionMigrationLog.getiDMEDId())

            MigrationLog drugkMigrationLog = MigrationLog.findBySourceIdAndSourceEntityAndIDMEDIdIsNotNull(this.drug, "drug")

            PrescribedDrug prescribedDrug = new PrescribedDrug()
            prescribedDrug.setDrug(Drug.findById(drugkMigrationLog.getiDMEDId()))
            prescribedDrug.setModified(this.modified == "T")
            prescribedDrug.setPrescription(savedPrescription)
            prescribedDrug.setAmtPerTime(this.amtpertime.intValue())
            prescribedDrug.setQtyPrescribed(this.amtpertime * this.timesperday)
            prescribedDrug.setTimesPerDay(this.timesperday)
            prescribedDrug.setForm(prescribedDrug.getDrug().getDefaultPeriodTreatment())
            if (!prescribedDrug.hasErrors()) {
                prescribedDrug.save(flush: true)
                setMigratedRecord(prescribedDrug)
            } else {
                logs.addAll(generateUnknowMigrationLog(this, prescribedDrug.getErrors().toString()))
                return logs
            }

        }
        return logs

    }

    @Override
    public void updateIDMEDInfo() {

    }

    @Override
    public int getId() {
        return this.id;
    }


    @Override
    public String getEntityName() {
        return "PrescribedDrugs";
    }

    @Override
    public MigratedRecord initMigratedRecord() {
        return null;
    }

    @Override
    public void setMigratedRecord(MigratedRecord migratedRecord) {
        this.migratedRecord = migratedRecord;
    }
}
