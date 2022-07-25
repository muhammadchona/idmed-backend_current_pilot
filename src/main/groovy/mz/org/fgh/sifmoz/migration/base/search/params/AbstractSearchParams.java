package mz.org.fgh.sifmoz.migration.base.search.params;

import mz.org.fgh.sifmoz.migration.base.record.AbstractMigrationRecord;
import mz.org.fgh.sifmoz.migration.base.record.MigrationRecord;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSearchParams<T extends AbstractMigrationRecord> {
    protected List<MigrationRecord> searchResults;


    public AbstractSearchParams() {
        this.searchResults = new ArrayList<>();
    }

    public abstract List<T> doSearch(long limit);
}
