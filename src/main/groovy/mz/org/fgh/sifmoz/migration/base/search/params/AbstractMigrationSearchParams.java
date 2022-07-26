package mz.org.fgh.sifmoz.migration.base.search.params;

import mz.org.fgh.sifmoz.migration.base.record.AbstractMigrationRecord;
import mz.org.fgh.sifmoz.migration.base.record.MigrationRecord;
import mz.org.fgh.sifmoz.backend.restUtils.RestServiceProvider;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMigrationSearchParams<T extends AbstractMigrationRecord> {
    public static final String MIGRATION_STATUS_MIGRATED = "MIGRATED";
    public static final String MIGRATION_STATUS_REJECTED = "REJECTED";
    public static final String MIGRATION_STATUS_CORRECTED = "CORRECTED";

    protected List<MigrationRecord> searchResults;
    protected String migrationStatus;
    protected String searchCondition;
    protected String migrationUrl;
    protected RestServiceProvider restServiceProvider;


    public AbstractMigrationSearchParams() {
        this.searchResults = new ArrayList<>();
        this.restServiceProvider = new RestServiceProvider("", "");
    }

    public abstract List<T> doSearch(long limit);

    public RestServiceProvider getRestServiceProvider() {
        return restServiceProvider;
    }
}
