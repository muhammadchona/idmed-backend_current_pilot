package mz.org.fgh.sifmoz.backend.migration.params.prescription

import mz.org.fgh.sifmoz.backend.migration.base.search.params.AbstractMigrationSearchParams
import mz.org.fgh.sifmoz.backend.migration.entity.prescription.PrescribedDrugsMigrationRecord
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.grails.web.json.JSONArray

class PrescribedDrugsMigrationSearchParams extends AbstractMigrationSearchParams<PrescribedDrugsMigrationRecord> {

    static Logger logger = LogManager.getLogger(PrescribedDrugsMigrationSearchParams.class)

    @Override
    List<PrescribedDrugsMigrationRecord> doSearch(long limit) {
        JSONArray jsonArray = getRestServiceProvider().get("/prescribed_drugs_migration_vw?limit="+limit)
        this.searchResults.clear()
        PrescribedDrugsMigrationRecord[] prescribedMigrationRecords = gson.fromJson(jsonArray.toString(), PrescribedDrugsMigrationRecord[].class)
        if (prescribedMigrationRecords != null && prescribedMigrationRecords.length > 0) {
            this.searchResults.addAll(Arrays.asList(prescribedMigrationRecords))
        }
        for (PrescribedDrugsMigrationRecord record : this.searchResults) {
            record.setRestService(getRestServiceProvider())
        }
        return this.searchResults
    }
}
