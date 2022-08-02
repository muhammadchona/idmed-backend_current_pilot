package mz.org.fgh.sifmoz.backend.migration.params.prescription

import mz.org.fgh.sifmoz.backend.migration.base.search.params.AbstractMigrationSearchParams
import mz.org.fgh.sifmoz.backend.migration.entity.prescription.PrescriptionMigrationRecord
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.grails.web.json.JSONArray

class PrescriptionMigrationSearchParams extends AbstractMigrationSearchParams<PrescriptionMigrationRecord> {

    static Logger logger = LogManager.getLogger(PrescriptionMigrationSearchParams.class);
    @Override
    public List<PrescriptionMigrationRecord> doSearch(long limit) {
        JSONArray jsonArray = getRestServiceProvider().get("/prescription_migration_vw?limit=1");
        this.searchResults.clear();
        PrescriptionMigrationRecord[] prescriptionMigrationRecords = gson.fromJson(jsonArray.toString(), PrescriptionMigrationRecord[].class);
        if (prescriptionMigrationRecords != null && prescriptionMigrationRecords.length > 0) {
            this.searchResults.addAll(Arrays.asList(prescriptionMigrationRecords));
        }
        return this.searchResults;
    }
}
