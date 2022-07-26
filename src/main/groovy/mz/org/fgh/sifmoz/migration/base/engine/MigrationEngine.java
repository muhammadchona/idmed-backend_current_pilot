package mz.org.fgh.sifmoz.migration.base.engine;

import mz.org.fgh.sifmoz.migration.base.record.AbstractMigrationRecord;
import mz.org.fgh.sifmoz.migration.base.search.SearchEngine;
import mz.org.fgh.sifmoz.migration.base.search.params.AbstractMigrationSearchParams;

public interface MigrationEngine<T extends AbstractMigrationRecord> extends Runnable{

    SearchEngine initSearchEngine(AbstractMigrationSearchParams<T> searchParams);

}
