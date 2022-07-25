package mz.org.fgh.sifmoz.migration.params;

import mz.org.fgh.sifmoz.migration.base.record.MigrationRecord;
import mz.org.fgh.sifmoz.migration.base.search.params.AbstractSearchParams;
import mz.org.fgh.sifmoz.migration.entity.patient.PatientMigrationRecord;

import java.util.List;

public class PatientSearchParams extends AbstractSearchParams<PatientMigrationRecord> {


    @Override
    public List<PatientMigrationRecord> doSearch(long limit) {
        return null;
    }
}
