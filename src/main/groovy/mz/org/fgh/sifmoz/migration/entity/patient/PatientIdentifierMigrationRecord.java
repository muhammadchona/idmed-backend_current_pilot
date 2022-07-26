package mz.org.fgh.sifmoz.migration.entity.patient;

import mz.org.fgh.sifmoz.migration.base.log.AbstractMigrationLog;
import mz.org.fgh.sifmoz.migration.base.record.AbstractMigrationRecord;
import mz.org.fgh.sifmoz.migration.base.record.MigratedRecord;
import mz.org.fgh.sifmoz.migration.base.record.MigrationRecord;

import java.util.List;

public class PatientIdentifierMigrationRecord extends AbstractMigrationRecord {


    private Integer id;

    private PatientMigrationRecord patient;

    private String value;

    private IdentifierTypeMigrationRecord type;

    private String valueEdit;

    public void setId(Integer id) {
        this.id = id;
    }

    public PatientMigrationRecord getPatient() {
        return patient;
    }

    public void setPatient(PatientMigrationRecord patient) {
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
