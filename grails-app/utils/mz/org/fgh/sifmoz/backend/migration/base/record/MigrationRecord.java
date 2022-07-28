package mz.org.fgh.sifmoz.backend.migration.base.record;

import grails.validation.Validateable;
import mz.org.fgh.sifmoz.backend.migrationLog.MigrationLog;
import mz.org.fgh.sifmoz.backend.restUtils.RestService;

import java.util.List;

public interface MigrationRecord extends Validateable {

    List<MigrationLog> migrate();

    void setAsMigratedSuccessfully(RestService restServiceProvider);

    void setAsRejectedForMigration(RestService restServiceProvider);

    void updateIDMEDInfo();

    int getId();

    String getEntityName();

    List<MigrationLog> generateUnknowMigrationLog(MigrationRecord record, String message);

    MigratedRecord initMigratedRecord();

    void saveMigrationLogs(List<MigrationLog> migrationLogs);

    MigratedRecord getMigratedRecord();
}
