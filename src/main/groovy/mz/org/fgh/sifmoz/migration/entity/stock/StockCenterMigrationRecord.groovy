package mz.org.fgh.sifmoz.migration.entity.stock;

import mz.org.fgh.sifmoz.migration.base.log.AbstractMigrationLog
import mz.org.fgh.sifmoz.migration.base.record.AbstractMigrationRecord
import mz.org.fgh.sifmoz.migration.base.record.MigratedRecord
import mz.org.fgh.sifmoz.migration.base.record.MigrationRecord

class StockCenterMigrationRecord extends AbstractMigrationRecord {
    private Integer id

    private String stockCenterName

    private boolean preferred

    private Set<StockMigrationRecord> stockMigrationRecords

    private String clinicuuid

    //------------------------------------------------

    @Override
    List<AbstractMigrationLog> migrate() {
        return null
    }

    @Override
    void updateIDMEDInfo() {

    }

    @Override
    long getId() {
        return 0
    }

    @Override
    String getEntityName() {
        return null
    }

    @Override
    void generateUnknowMigrationLog(MigrationRecord record, String message) {

    }

    @Override
    MigratedRecord initMigratedRecord() {
        return null
    }
}
