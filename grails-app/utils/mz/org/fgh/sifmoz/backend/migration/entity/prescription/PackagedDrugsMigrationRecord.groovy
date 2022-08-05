package mz.org.fgh.sifmoz.backend.migration.entity.prescription

import mz.org.fgh.sifmoz.backend.drug.Drug;
import mz.org.fgh.sifmoz.backend.migrationLog.MigrationLog;
import mz.org.fgh.sifmoz.backend.migration.base.record.AbstractMigrationRecord;
import mz.org.fgh.sifmoz.backend.migration.base.record.MigratedRecord
import mz.org.fgh.sifmoz.backend.packagedDrug.PackagedDrug
import mz.org.fgh.sifmoz.backend.packagedDrug.PackagedDrugStock
import mz.org.fgh.sifmoz.backend.packaging.Pack;
import mz.org.fgh.sifmoz.backend.prescription.Prescription
import mz.org.fgh.sifmoz.backend.stock.Stock
import mz.org.fgh.sifmoz.backend.utilities.Utilities;

import java.util.List;

public class PackagedDrugsMigrationRecord extends AbstractMigrationRecord {

    private Integer id;


    private Integer amount;

    private Integer stock;

    private Integer parentpackage;

    private char modified;

    private double packageddrugsindex;

    public void setId(Integer id) {
        this.id = id;
    }


    @Override
    public List<MigrationLog> migrate() {
        List<MigrationLog> logs = new ArrayList<>()
        PackagedDrug.withTransaction {
            MigrationLog stockMigrationLog = MigrationLog.findBySourceIdAndSourceEntity(this.stock, "stock")
            Stock stock = Stock.findById(stockMigrationLog.getiDMEDId())

            MigrationLog packMigrationLog = MigrationLog.findBySourceIdAndSourceEntity(this.parentpackage, "Package")
            Pack pack = Pack.findById(packMigrationLog.getiDMEDId())

            if (pack == null) throw new RuntimeException("Não foi encontrado o respectivo registo PACK.")

            PackagedDrug packagedDrug = new PackagedDrug()
            packagedDrug.setQuantitySupplied(this.amount)
            packagedDrug.setNextPickUpDate(pack.getNextPickUpDate())
            packagedDrug.setDrug(stock.getDrug())
            packagedDrug.setCreationDate(new Date())
            packagedDrug.setPack(pack)
            packagedDrug.setToContinue(true)

            //PackageDrugStock
            PackagedDrugStock packagedDrugStock = new PackagedDrugStock()
            packagedDrugStock.setQuantitySupplied(amount)
            packagedDrugStock.setDrug(stock.getDrug())
            packagedDrugStock.setStock(stock)
            packagedDrugStock.setCreationDate(new Date())
            packagedDrugStock.setPackagedDrug(packagedDrug)
            packagedDrugStock.setQuantitySupplied(this.amount)

            packagedDrug.validate()

            if (!packagedDrug.hasErrors()) {
                packagedDrug.save(flush: true)
                setMigratedRecord(packagedDrug)
            } else {
                logs.addAll(generateUnknowMigrationLog(this, packagedDrug.getErrors().toString()))
                return logs
            }

        }
        return logs

    }

    @Override
    public void updateIDMEDInfo() {

    }

    @Override
    public int getId() {
        return this.id;
    }


    @Override
    public String getEntityName() {
        return "PrescribedDrugs";
    }

    @Override
    public MigratedRecord initMigratedRecord() {
        return null;
    }

    @Override
    public void setMigratedRecord(MigratedRecord migratedRecord) {
        this.migratedRecord = migratedRecord;
    }
}
