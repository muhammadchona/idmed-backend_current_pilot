package mz.org.fgh.sifmoz.migration.params;

import com.google.gson.JsonArray;
import mz.org.fgh.sifmoz.migration.base.record.AbstractMigrationRecord;
import mz.org.fgh.sifmoz.migration.base.search.params.AbstractMigrationSearchParams;
import mz.org.fgh.sifmoz.migration.entity.patient.PatientMigrationRecord;
import net.bytebuddy.description.method.MethodDescription;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.grails.web.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PatientMigrationSearchParams extends AbstractMigrationSearchParams<PatientMigrationRecord> {


    static Logger logger = LogManager.getLogger(PatientMigrationSearchParams.class);
    @Override
    public List<PatientMigrationRecord> doSearch(long limit) {
        JSONArray jsonArray = getRestServiceProvider().get("/patient_migration_vw?limit=15");
        this.searchResults.clear();
        PatientMigrationRecord[] patientMigrationRecords = gson.fromJson(jsonArray.toString(), PatientMigrationRecord[].class);
        if (patientMigrationRecords != null && patientMigrationRecords.length > 0) {
            this.searchResults.addAll(Arrays.asList(patientMigrationRecords));
        }
        return this.searchResults;
    }
}
