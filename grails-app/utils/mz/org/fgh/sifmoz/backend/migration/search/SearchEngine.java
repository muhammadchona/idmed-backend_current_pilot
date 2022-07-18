package mz.org.fgh.sifmoz.backend.migration.search;

import mz.org.fgh.sifmoz.backend.migration.record.AbstractMigrationRecord;
import mz.org.fgh.sifmoz.backend.migration.record.MigrationRecord;

import java.util.List;

public abstract class SearchEngine<T extends AbstractMigrationRecord> {

    public List<T> doSearch() {
        return null;
    }
}
