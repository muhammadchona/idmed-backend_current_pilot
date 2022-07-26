package mz.org.fgh.sifmoz.migration.entity.patient;

import mz.org.fgh.sifmoz.migration.base.log.AbstractMigrationLog;
import mz.org.fgh.sifmoz.migration.base.record.AbstractMigrationRecord;
import mz.org.fgh.sifmoz.migration.base.record.MigratedRecord;
import mz.org.fgh.sifmoz.migration.base.record.MigrationRecord;

import java.util.List;

public class ClinicSectorMigrationRecord extends AbstractMigrationRecord {
    private Integer id;
    private String code;
    private String sectorname;
    private String telephone;
    private String uuid;
    private String clinicId;
    private String clinicSectorTypeId;
    private String clinicuuid;

    @Override
    public List<AbstractMigrationLog> migrate() {
        return null;
    }

    @Override
    public void updateIDMEDInfo() {

    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public String getEntityName() {
        return null;
    }

    @Override
    public void generateUnknowMigrationLog(MigrationRecord record, String message) {

    }

    @Override
    public MigratedRecord initMigratedRecord() {
        return null;
    }
}
