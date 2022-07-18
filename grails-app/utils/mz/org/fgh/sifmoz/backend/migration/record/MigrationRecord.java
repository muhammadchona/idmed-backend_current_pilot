package mz.org.fgh.sifmoz.backend.migration.record;

import mz.org.fgh.sifmoz.backend.migration.log.AbstractMigrationLog;

import java.util.List;

public interface MigrationRecord {

    List<AbstractMigrationLog> migrate();

    void setAsMigrationSuccess();

    void setAsMigrationFailed();

    void updateIDMEDInfo();
}
