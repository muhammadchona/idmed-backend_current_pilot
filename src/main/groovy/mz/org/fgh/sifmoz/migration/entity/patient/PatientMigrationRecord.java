package mz.org.fgh.sifmoz.migration.entity.patient;

import mz.org.fgh.sifmoz.backend.patient.Patient;
import mz.org.fgh.sifmoz.migration.base.log.AbstractMigrationLog;
import mz.org.fgh.sifmoz.migration.base.record.AbstractMigrationRecord;
import mz.org.fgh.sifmoz.migration.base.record.MigrationRecord;

import java.util.List;

public class PatientMigrationRecord extends AbstractMigrationRecord {

    @Override
    public List<AbstractMigrationLog> migrate() {
        return null;
    }

    @Override
    public void setAsMigratedSuccessfully() {

    }

    @Override
    public void setAsRejectedForMigration() {

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
        return "Patient";
    }

    @Override
    public void generateUnknowMigrationLog(MigrationRecord record, String message) {

    }
}
