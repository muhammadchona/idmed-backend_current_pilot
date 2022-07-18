package mz.org.fgh.sifmoz.backend.migration.engine;

import mz.org.fgh.sifmoz.backend.migration.log.AbstractMigrationLog;
import mz.org.fgh.sifmoz.backend.migration.record.AbstractMigrationRecord;
import mz.org.fgh.sifmoz.backend.migration.search.SearchEngine;
import mz.org.fgh.sifmoz.backend.utilities.Utilities;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMigrationEngine<T extends AbstractMigrationRecord> implements MigrationEngine<T> {

    protected SearchEngine<T> searchEngine;
    protected List<T> recordList;

    protected void init() {
        this.searchEngine = initSearchEngine();
    }

    public void doMigration() {
        for (T record : recordList) {
            List<AbstractMigrationLog> migrationLogs = null;
            try {
                migrationLogs = record.migrate();
            } catch (Exception e) {
                generateUnknowMigrationLog(record, e.getMessage());
            } finally {
                if (Utilities.listHasElements((ArrayList<?>) migrationLogs)) {
                    record.setAsMigrationFailed();
                } else {
                    record.setAsMigrationSuccess();
                    record.updateIDMEDInfo();
                }
            }
        }
    }

    protected abstract void generateUnknowMigrationLog(T record, String message);

    public List<T> searchRecords () {
        this.recordList = this.searchEngine.doSearch();
        return this.recordList;
    }
}
