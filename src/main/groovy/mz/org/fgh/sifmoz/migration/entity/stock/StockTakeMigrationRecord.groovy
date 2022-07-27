package mz.org.fgh.sifmoz.migration.entity.stock;

import mz.org.fgh.sifmoz.migration.base.log.AbstractMigrationLog
import mz.org.fgh.sifmoz.migration.base.record.AbstractMigrationRecord
import mz.org.fgh.sifmoz.migration.base.record.MigratedRecord
import mz.org.fgh.sifmoz.migration.base.record.MigrationRecord

class StockTakeMigrationRecord extends AbstractMigrationRecord {
    private Integer id

    private String stockTakeNumber

    private Date startDate

    private Date endDate

    private Set<StockAdjustmentMigrationRecord> adjustments

    private boolean open

    //---------------------------------------------------

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
