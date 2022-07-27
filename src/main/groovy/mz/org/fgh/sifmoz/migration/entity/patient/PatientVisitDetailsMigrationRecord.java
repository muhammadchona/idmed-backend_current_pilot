package mz.org.fgh.sifmoz.migration.entity.patient;

import mz.org.fgh.sifmoz.backend.migrationLog.MigrationLog;
import mz.org.fgh.sifmoz.migration.base.log.AbstractMigrationLog;
import mz.org.fgh.sifmoz.migration.base.record.AbstractMigrationRecord;
import mz.org.fgh.sifmoz.migration.base.record.MigratedRecord;
import mz.org.fgh.sifmoz.migration.base.record.MigrationRecord;

import java.util.Date;
import java.util.List;

public class PatientVisitDetailsMigrationRecord extends AbstractMigrationRecord {

    private Integer id;
    private Date dateofvisit;
    private String patient_id;
    private Integer patientvisitreason_id;
    private char isscheduled;
    private String notes;
    private String diagnosis;

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateofvisit() {
        return dateofvisit;
    }

    public void setDateofvisit(Date dateofvisit) {
        this.dateofvisit = dateofvisit;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public Integer getPatientvisitreason_id() {
        return patientvisitreason_id;
    }

    public void setPatientvisitreason_id(Integer patientvisitreason_id) {
        this.patientvisitreason_id = patientvisitreason_id;
    }

    public char getIsscheduled() {
        return isscheduled;
    }

    public void setIsscheduled(char isscheduled) {
        this.isscheduled = isscheduled;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    @Override
    public List<MigrationLog> migrate() {
        return null;
    }

    @Override
    public void updateIDMEDInfo() {

    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getEntityName() {
        return "PatientVisitDetails";
    }


    @Override
    public MigratedRecord initMigratedRecord() {
        return null;
    }
}
