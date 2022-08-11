package mz.org.fgh.sifmoz.backend.migration.params.pack;

import mz.org.fgh.sifmoz.backend.migration.base.search.params.AbstractMigrationSearchParams;
import mz.org.fgh.sifmoz.backend.migration.entity.pack.PackageMigrationRecord;
import mz.org.fgh.sifmoz.backend.migration.entity.patient.PatientMigrationRecord;
import org.grails.web.json.JSONArray;

import java.util.Arrays;
import java.util.List;

public class PackageMigrationSearchParams extends AbstractMigrationSearchParams<PackageMigrationRecord> {
    @Override
    public List<PackageMigrationRecord> doSearch(long limit) {
        JSONArray jsonArray = getRestServiceProvider().get("/package_migration_vw?limit="+limit);
        this.searchResults.clear();
        PackageMigrationRecord[] patientMigrationRecords = gson.fromJson(jsonArray.toString(), PackageMigrationRecord[].class);
        if (patientMigrationRecords != null && patientMigrationRecords.length > 0) {
            this.searchResults.addAll(Arrays.asList(patientMigrationRecords));
        }
        return this.searchResults;
    }
}
