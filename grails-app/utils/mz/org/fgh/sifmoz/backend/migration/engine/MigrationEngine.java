package mz.org.fgh.sifmoz.backend.migration.engine;

import mz.org.fgh.sifmoz.backend.migration.record.AbstractMigrationRecord;
import mz.org.fgh.sifmoz.backend.migration.search.SearchEngine;

public interface MigrationEngine<T extends AbstractMigrationRecord> {

    SearchEngine<T> initSearchEngine();

}
