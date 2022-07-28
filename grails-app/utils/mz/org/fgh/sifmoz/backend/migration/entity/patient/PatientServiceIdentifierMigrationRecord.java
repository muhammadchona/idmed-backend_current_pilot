package mz.org.fgh.sifmoz.backend.migration.entity.patient;

import mz.org.fgh.sifmoz.backend.migrationLog.MigrationLog;
import mz.org.fgh.sifmoz.backend.patientIdentifier.PatientServiceIdentifier;
import mz.org.fgh.sifmoz.backend.migration.base.record.AbstractMigrationRecord;
import mz.org.fgh.sifmoz.backend.migration.base.record.MigratedRecord;

import java.util.ArrayList;
import java.util.List;

public class PatientServiceIdentifierMigrationRecord extends AbstractMigrationRecord {


    private Integer id;

    private PatientMigrationRecordOld patient;

    private String value;

    private IdentifierTypeMigrationRecord type;

    private String valueEdit;

    public void setId(Integer id) {
        this.id = id;
    }

    public PatientMigrationRecordOld getPatient() {
        return patient;
    }

    public void setPatient(PatientMigrationRecordOld patient) {
        this.patient = patient;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public IdentifierTypeMigrationRecord getType() {
        return type;
    }

    public void setType(IdentifierTypeMigrationRecord type) {
        this.type = type;
    }

    public String getValueEdit() {
        return valueEdit;
    }

    public void setValueEdit(String valueEdit) {
        this.valueEdit = valueEdit;
    }

    @Override
    public List<MigrationLog> migrate() {
        List<MigrationLog> logs = new ArrayList<>();
        return logs;
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
        return "PatientServiceIdentifier";
    }


    @Override
    public MigratedRecord initMigratedRecord() {
        return new PatientServiceIdentifier();
    }

    @Override
    public PatientServiceIdentifier getMigratedRecord() {
        return (PatientServiceIdentifier) super.getMigratedRecord();
    }

}
