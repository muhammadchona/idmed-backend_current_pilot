package mz.org.fgh.sifmoz.backend.migration.params.prescription

import mz.org.fgh.sifmoz.backend.migration.base.search.params.AbstractMigrationSearchParams
import mz.org.fgh.sifmoz.backend.migration.entity.dispense.PackageMigrationRecord
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.grails.web.json.JSONArray

class PackagedDrugsMigrationSearchParams extends AbstractMigrationSearchParams<PackageMigrationRecord> {

    static Logger logger = LogManager.getLogger(PackagedDrugsMigrationSearchParams.class)

    @Override
    List<PackageMigrationRecord> doSearch(long limit) {
        JSONArray jsonArray = getRestServiceProvider().get("/packaged_drugs_migration_vw?limit="+limit)
        this.searchResults.clear()
        PackageMigrationRecord[] packagedMigrationRecords = gson.fromJson(jsonArray.toString(), PackageMigrationRecord[].class)
        if (packagedMigrationRecords != null && packagedMigrationRecords.length > 0) {
            this.searchResults.addAll(Arrays.asList(packagedMigrationRecords))
        }
        for (PackageMigrationRecord record : this.searchResults) {
            record.setRestService(getRestServiceProvider())
        }
        return this.searchResults
    }
}
