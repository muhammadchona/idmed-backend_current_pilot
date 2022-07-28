package mz.org.fgh.sifmoz.backend.migration.entity.patient;

import mz.org.fgh.sifmoz.backend.migrationLog.MigrationLog;
import mz.org.fgh.sifmoz.backend.migration.base.record.AbstractMigrationRecord;
import mz.org.fgh.sifmoz.backend.migration.base.record.MigratedRecord;

import java.util.List;

public class NationalClinicsMigrationRecord extends AbstractMigrationRecord {

    private Integer id;

    private String province;

    private String district;

    private String subDistrict;

    private String facilityName;


    private String facilityType;

    @Override
    public List<MigrationLog> migrate() {
        return null;
    }

    @Override
    public void updateIDMEDInfo() {

    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getEntityName() {
        return null;
    }

    @Override
    public MigratedRecord initMigratedRecord() {
        return null;
    }
}
