package mz.org.fgh.sifmoz.backend.migration.params.prescription

import mz.org.fgh.sifmoz.backend.migration.base.search.params.AbstractMigrationSearchParams
import mz.org.fgh.sifmoz.backend.migration.entity.prescription.PackagedDrugsMigrationRecord
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.grails.web.json.JSONArray

class PackagedDrugsMigrationSearchParams extends AbstractMigrationSearchParams<PackagedDrugsMigrationRecord> {

    static Logger logger = LogManager.getLogger(PackagedDrugsMigrationSearchParams.class)

    @Override
    List<PackagedDrugsMigrationRecord> doSearch(long limit) {
        JSONArray jsonArray = getRestServiceProvider().get("/packaged_drugs_migration_vw?limit="+limit)
        this.searchResults.clear()
        PackagedDrugsMigrationRecord[] packagedMigrationRecords = gson.fromJson(jsonArray.toString(), PackagedDrugsMigrationRecord[].class)
        if (packagedMigrationRecords != null && packagedMigrationRecords.length > 0) {
            this.searchResults.addAll(Arrays.asList(packagedMigrationRecords))
        }
        for (PackagedDrugsMigrationRecord record : this.searchResults) {
            record.setRestService(getRestServiceProvider())
        }
        return this.searchResults
    }
}
