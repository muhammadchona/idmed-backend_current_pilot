package mz.org.fgh.sifmoz.migration.entity.stock

import mz.org.fgh.sifmoz.backend.migrationLog.MigrationLog
import mz.org.fgh.sifmoz.migration.base.log.AbstractMigrationLog;
import mz.org.fgh.sifmoz.migration.base.record.AbstractMigrationRecord
import mz.org.fgh.sifmoz.migration.base.record.MigratedRecord;
import mz.org.fgh.sifmoz.migration.base.record.MigrationRecord;


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
    long getId() {
        return 0;
    }

    @Override
    String getEntityName() {
        return null;
    }

    @Override
    void generateUnknowMigrationLog(MigrationRecord record, String message) {

    }

    @Override
    MigratedRecord initMigratedRecord() {
        return null
    }
}
