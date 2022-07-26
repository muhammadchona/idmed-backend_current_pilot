package mz.org.fgh.sifmoz.migration.params;

import mz.org.fgh.sifmoz.migration.base.search.params.AbstractMigrationSearchParams;
import mz.org.fgh.sifmoz.migration.entity.patient.PatientMigrationRecord;

import java.util.List;

public class PatientMigrationSearchParams extends AbstractMigrationSearchParams<PatientMigrationRecord> {


    @Override
    public List<PatientMigrationRecord> doSearch(long limit) {
        return null;
    }
}
