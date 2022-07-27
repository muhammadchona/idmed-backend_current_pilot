package mz.org.fgh.sifmoz.migration.base.record;

import mz.org.fgh.sifmoz.backend.migrationLog.MigrationLog;
import mz.org.fgh.sifmoz.migration.base.log.AbstractMigrationLog;

import java.util.List;

public interface MigrationRecord {

    List<MigrationLog> migrate();

    void setAsMigratedSuccessfully();

    void setAsRejectedForMigration();

    void updateIDMEDInfo();

    long getId();

    String getEntityName();

    void generateUnknowMigrationLog(MigrationRecord record, String message);

    MigratedRecord initMigratedRecord();

}
