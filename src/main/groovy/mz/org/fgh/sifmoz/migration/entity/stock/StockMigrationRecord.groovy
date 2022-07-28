package mz.org.fgh.sifmoz.migration.entity.stock

import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.migrationLog.MigrationLog
import mz.org.fgh.sifmoz.backend.stock.Stock
import mz.org.fgh.sifmoz.backend.stockcenter.StockCenter
import mz.org.fgh.sifmoz.backend.stockentrance.StockEntrance
import mz.org.fgh.sifmoz.migration.base.log.AbstractMigrationLog;
import mz.org.fgh.sifmoz.migration.base.record.AbstractMigrationRecord
import mz.org.fgh.sifmoz.migration.base.record.MigratedRecord;
import mz.org.fgh.sifmoz.migration.base.record.MigrationRecord

import java.text.DateFormat
import java.text.SimpleDateFormat;


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

    String drug

    //----------------------------------------------------

    @Override
    List<MigrationLog> migrate() {
        List<MigrationLog> logs = new ArrayList<>()
        def clinic = Clinic.findWhere(main_clinic: true)
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy")
        String dateFormated = dateFormat.format(this.datereceived)

        // Setting StockEntrance
        getMigratedRecordEntrance().setClinic(clinic)
        getMigratedRecordEntrance().setDateReceived(this.datereceived)
        getMigratedRecordEntrance().setOrderNumber(dateFormated+"_NA")

        // Setting Stock
        getMigratedRecordStock().setClinic(clinic)
        getMigratedRecordStock().setBatchNumber(this.batchnumber)
        getMigratedRecordStock().setModified(this.modified == (char) 'T')
        getMigratedRecordStock().setUnitsReceived(this.unitsreceived)
        getMigratedRecordStock().setExpireDate(this.expirydate)
        getMigratedRecordStock().setShelfNumber(this.shelfnumber)
        getMigratedRecordStock().setManufacture(this.manufacturer)

        // Auxiliar
        def stockEntranceAux = StockEntrance.findWhere(this.datereceived)


        if (stockEntranceAux != null) {// Ja existe o stockEntrance
            // migrar apenas o Stock

            // Setting numero de guia => (id do stockEntrance)
            getMigratedRecordStock().setEntrance(stockEntranceAux)
        } else {// O stockEntrance ainda nao existe
            // migrar entrance e o Stock

            // Setting numero de guia  => (id do stockEntrance)
            getMigratedRecordStock().setEntrance()
        }

        return null
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
        return null
    }

    @Override
    void generateUnknowMigrationLog(MigrationRecord record, String message) {

    }

    @Override
    MigratedRecord initMigratedRecord() {
        return new Stock()
    }

    @Override
    Stock getMigratedRecordStock() {
        return (Stock) super.getMigratedRecord()
    }

    @Override
    StockEntrance getMigratedRecordEntrance() {
        return (StockEntrance) super.getMigratedRecord()
    }
}
