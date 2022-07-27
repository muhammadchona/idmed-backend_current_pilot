package mz.org.fgh.sifmoz.migration.entity.prescription;

import mz.org.fgh.sifmoz.backend.migrationLog.MigrationLog;
import mz.org.fgh.sifmoz.migration.base.log.AbstractMigrationLog;
import mz.org.fgh.sifmoz.migration.base.record.AbstractMigrationRecord;
import mz.org.fgh.sifmoz.migration.base.record.MigratedRecord;
import mz.org.fgh.sifmoz.migration.base.record.MigrationRecord;

import java.util.List;

public class PrescribedDrugsMigrationRecord extends AbstractMigrationRecord {

    private Integer id;

    private PrescriptionMigrationRecord prescription;

    private String drug;

    private String takePeriod;

    private int timesPerDay;

    private char modified;

    private double amtPerTime;

    public void setId(Integer id) {
        this.id = id;
    }

    public PrescriptionMigrationRecord getPrescription() {
        return prescription;
    }

    public void setPrescription(PrescriptionMigrationRecord prescription) {
        this.prescription = prescription;
    }

    public String getDrug() {
        return drug;
    }

    public void setDrug(String drug) {
        this.drug = drug;
    }

    public String getTakePeriod() {
        return takePeriod;
    }

    public void setTakePeriod(String takePeriod) {
        this.takePeriod = takePeriod;
    }

    public int getTimesPerDay() {
        return timesPerDay;
    }

    public void setTimesPerDay(int timesPerDay) {
        this.timesPerDay = timesPerDay;
    }

    public char getModified() {
        return modified;
    }

    public void setModified(char modified) {
        this.modified = modified;
    }

    public double getAmtPerTime() {
        return amtPerTime;
    }

    public void setAmtPerTime(double amtPerTime) {
        this.amtPerTime = amtPerTime;
    }

    @Override
    public List<MigrationLog> migrate() {
        return null;
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
        return null;
    }

    @Override
    public void generateUnknowMigrationLog(MigrationRecord record, String message) {

    }

    @Override
    public MigratedRecord initMigratedRecord() {
        return null;
    }
}
