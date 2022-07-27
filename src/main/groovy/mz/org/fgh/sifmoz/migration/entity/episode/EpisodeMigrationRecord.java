package mz.org.fgh.sifmoz.migration.entity.episode;

import mz.org.fgh.sifmoz.backend.migrationLog.MigrationLog;
import mz.org.fgh.sifmoz.migration.base.log.AbstractMigrationLog;
import mz.org.fgh.sifmoz.migration.base.record.AbstractMigrationRecord;
import mz.org.fgh.sifmoz.migration.base.record.MigratedRecord;
import mz.org.fgh.sifmoz.migration.base.record.MigrationRecord;
import mz.org.fgh.sifmoz.migration.entity.patient.ClinicMigrationRecord;
import mz.org.fgh.sifmoz.migration.entity.patient.PatientMigrationRecord;

import java.util.Date;
import java.util.List;

public class EpisodeMigrationRecord extends AbstractMigrationRecord {

    private Integer id ;

    private PatientMigrationRecord patient;

    private ClinicMigrationRecord clinic;

    private Date startDate;

    private Date stopDate;

    private String startReason;

    private String stopReason;

    private String startNotes;

    private String stopNotes;

    public void setId(Integer id) {
        this.id = id;
    }

    public PatientMigrationRecord getPatient() {
        return patient;
    }

    public void setPatient(PatientMigrationRecord patient) {
        this.patient = patient;
    }

    public ClinicMigrationRecord getClinic() {
        return clinic;
    }

    public void setClinic(ClinicMigrationRecord clinic) {
        this.clinic = clinic;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStopDate() {
        return stopDate;
    }

    public void setStopDate(Date stopDate) {
        this.stopDate = stopDate;
    }

    public String getStartReason() {
        return startReason;
    }

    public void setStartReason(String startReason) {
        this.startReason = startReason;
    }

    public String getStopReason() {
        return stopReason;
    }

    public void setStopReason(String stopReason) {
        this.stopReason = stopReason;
    }

    public String getStartNotes() {
        return startNotes;
    }

    public void setStartNotes(String startNotes) {
        this.startNotes = startNotes;
    }

    public String getStopNotes() {
        return stopNotes;
    }

    public void setStopNotes(String stopNotes) {
        this.stopNotes = stopNotes;
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
