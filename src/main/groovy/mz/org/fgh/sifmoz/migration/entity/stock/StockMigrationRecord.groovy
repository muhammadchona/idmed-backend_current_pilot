package mz.org.fgh.sifmoz.migration.entity.stock


import mz.org.fgh.sifmoz.migration.base.log.AbstractMigrationLog;
import mz.org.fgh.sifmoz.migration.base.record.AbstractMigrationRecord
import mz.org.fgh.sifmoz.migration.base.record.MigratedRecord;
import mz.org.fgh.sifmoz.migration.base.record.MigrationRecord;


class StockMigrationRecord extends AbstractMigrationRecord {

    private Integer id;

    private String batchNumber;

    private Date dateReceived;

    private Date expiryDate;

    private char modified;

    private String shelfNumber;

    private String numeroGuia;

    private int unitsReceived;

    private String manufacturer;

    private char hasUnitsRemaining;

    private BigDecimal unitPrice;

    private StockCenterMigrationRecord stockCenterMigrationRecord;

    private StockTakeMigrationRecord stockTakeMigrationRecord;

    private StockAdjustmentMigrationRecord stockAdjustmentMigrationRecord;

    String drug;

    //----------------------------------------------------

    @Override
    List<AbstractMigrationLog> migrate() {
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
