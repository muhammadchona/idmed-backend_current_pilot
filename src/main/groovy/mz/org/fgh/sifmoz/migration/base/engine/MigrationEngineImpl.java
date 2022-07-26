package mz.org.fgh.sifmoz.migration.base.engine;

import mz.org.fgh.sifmoz.migration.base.log.AbstractMigrationLog;
import mz.org.fgh.sifmoz.migration.base.record.AbstractMigrationRecord;
import mz.org.fgh.sifmoz.migration.base.record.MigrationRecord;
import mz.org.fgh.sifmoz.migration.base.search.SearchEngine;
import mz.org.fgh.sifmoz.backend.utilities.Utilities;
import mz.org.fgh.sifmoz.migration.base.search.params.AbstractMigrationSearchParams;

import java.util.ArrayList;
import java.util.List;

public class MigrationEngineImpl<T extends AbstractMigrationRecord> implements MigrationEngine<T> {

    protected SearchEngine<T> searchEngine;
    protected List<T> recordList;

    public static final String PARAMS_MIGRATION_STAGE = "PARAMS_MIGRATION_STAGE";
    public static final String STOCK_MIGRATION_STAGE = "STOCK_MIGRATION_STAGE";
    public static final String PATIENT_MIGRATION_STAGE = "PATIENT_MIGRATION_STAGE";

    public MigrationEngineImpl(AbstractMigrationSearchParams<T> searchParams) {
        this.searchEngine = this.initSearchEngine(searchParams);
    }

    public void doMigration() {
        this.searchRecords();

        if (Utilities.listHasElements((ArrayList<?>) this.recordList)) {
            for (MigrationRecord record : recordList) {
                // iniciando a migração do registo [record.entityName() : record.getId()]

                List<AbstractMigrationLog> migrationLogs = null;
                try {
                    migrationLogs = record.migrate();
                } catch (Exception e) {
                    record.generateUnknowMigrationLog(record, e.getMessage());
                } finally {
                    if (Utilities.listHasElements((ArrayList<?>) migrationLogs)) {
                        record.setAsRejectedForMigration();
                    } else {
                        record.setAsMigratedSuccessfully();
                        record.updateIDMEDInfo();
                    }
                }
            }
        }
    }


    public void searchRecords () {
        this.recordList = this.searchEngine.doSearch();
    }

    @Override
    public SearchEngine<T> initSearchEngine(AbstractMigrationSearchParams<T> searchParams) {
        return new SearchEngine<>(searchParams);
    }

    @Override
    public void run() {
        this.doMigration();
    }
}
