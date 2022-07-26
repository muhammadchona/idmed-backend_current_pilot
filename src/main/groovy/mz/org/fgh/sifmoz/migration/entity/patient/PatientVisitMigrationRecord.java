package mz.org.fgh.sifmoz.migration.entity.patient;

import mz.org.fgh.sifmoz.migration.base.log.AbstractMigrationLog;
import mz.org.fgh.sifmoz.migration.base.record.AbstractMigrationRecord;
import mz.org.fgh.sifmoz.migration.base.record.MigratedRecord;
import mz.org.fgh.sifmoz.migration.base.record.MigrationRecord;

import java.util.Date;
import java.util.List;

public class PatientVisitMigrationRecord extends AbstractMigrationRecord {

    private Integer id;
    private int patient_id;
    private Date dateofvisit;
    private String isscheduled;
    private int patientvisitreason_id;
    private String diagnosis;
    private String notes;

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public Date getDateofvisit() {
        return dateofvisit;
    }

    public void setDateofvisit(Date dateofvisit) {
        this.dateofvisit = dateofvisit;
    }

    public String getIsscheduled() {
        return isscheduled;
    }

    public void setIsscheduled(String isscheduled) {
        this.isscheduled = isscheduled;
    }

    public int getPatientvisitreason_id() {
        return patientvisitreason_id;
    }

    public void setPatientvisitreason_id(int patientvisitreason_id) {
        this.patientvisitreason_id = patientvisitreason_id;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public List<AbstractMigrationLog> migrate() {
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
