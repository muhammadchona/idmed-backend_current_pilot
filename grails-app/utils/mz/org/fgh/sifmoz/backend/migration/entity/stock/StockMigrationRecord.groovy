package mz.org.fgh.sifmoz.backend.migration.entity.stock

import mz.org.fgh.sifmoz.backend.migration.base.record.AbstractMigrationRecord
import mz.org.fgh.sifmoz.backend.migration.base.record.MigratedRecord
import mz.org.fgh.sifmoz.backend.migrationLog.MigrationLog

class StockMigrationRecord extends AbstractMigrationRecord {

    private Integer id;

    private String batchnumber;

    private Date datereceived;

    private Date expirydate;

    private char modified;

    private String shelfnumber;

    private String numeroguia;

    private int unitsreceived;

    private String manufacturer;

    private char hasunitsremaining;

    private BigDecimal unitprice;

    private StockCenterMigrationRecord stockCenterMigrationRecord;

    private StockTakeMigrationRecord stockTakeMigrationRecord;

    private StockAdjustmentMigrationRecord stockAdjustmentMigrationRecord;

    String drug;

    //----------------------------------------------------

    @Override
    List<MigrationLog> migrate() {
        return null;
    }

    @Override
    void updateIDMEDInfo() {

    }

    @Override
    int getId() {
        return 0;
    }

    @Override
    String getEntityName() {
        return null;
    }

    @Override
    MigratedRecord initMigratedRecord() {
        return null
    }
}
