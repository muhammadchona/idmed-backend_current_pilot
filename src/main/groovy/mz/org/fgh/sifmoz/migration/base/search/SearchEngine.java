package mz.org.fgh.sifmoz.migration.base.search;

import mz.org.fgh.sifmoz.migration.base.record.AbstractMigrationRecord;
import mz.org.fgh.sifmoz.migration.base.record.MigrationRecord;
import mz.org.fgh.sifmoz.migration.base.search.params.AbstractSearchParams;

import java.util.ArrayList;
import java.util.List;

public class SearchEngine<T extends AbstractMigrationRecord>{

    public static final long RECORDS_PER_SEARCH = 100;

    public static final String PREPARING_SEARCH = "PREPARING_SEARCH";

    public static final String PERFORMING_SEARCH = "PERFORMING_SEARCH";

    public static final String LOADING_MORE_RECORDS = "LOADING_MORE_RECORDS";

    public static final String SEARCH_FINISHED = "SEARCH_FINISHED";


    protected AbstractSearchParams<T> searchParams;

    public SearchEngine(AbstractSearchParams<T> searchParams) {
        this.searchParams = searchParams;
    }

    public List<T> doSearch() {
        return searchParams.doSearch(RECORDS_PER_SEARCH);
    }

    public AbstractSearchParams<T> getSearchParams() {
        return searchParams;
    }
}
