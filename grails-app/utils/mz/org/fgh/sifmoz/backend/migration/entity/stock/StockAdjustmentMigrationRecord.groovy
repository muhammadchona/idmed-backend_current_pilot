package mz.org.fgh.sifmoz.backend.migration.entity.stock

import mz.org.fgh.sifmoz.backend.migration.base.log.AbstractMigrationLog
import mz.org.fgh.sifmoz.backend.migration.base.record.AbstractMigrationRecord
import mz.org.fgh.sifmoz.backend.migration.base.record.MigratedRecord


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
    int getId() {
        return 0
    }

    @Override
    String getEntityName() {
        return null
    }

    @Override
    MigratedRecord initMigratedRecord() {
        return null
    }
}
