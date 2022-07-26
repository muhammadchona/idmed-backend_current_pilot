package mz.org.fgh.sifmoz.migration.entity.stock

import mz.org.fgh.sifmoz.migration.base.log.AbstractMigrationLog
import mz.org.fgh.sifmoz.migration.base.record.AbstractMigrationRecord
import mz.org.fgh.sifmoz.migration.base.record.MigratedRecord
import mz.org.fgh.sifmoz.migration.base.record.MigrationRecord

class StockAdjustmentMigrationRecord extends AbstractMigrationRecord {
    private Integer id

    private StockMigrationRecord stock

    private Date captureDate

    private int stockCount

    private int adjustedValue

    private String notes

    private StockTakeMigrationRecord stockTake

    //-----------------------------------------------------
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
