package mz.org.fgh.sifmoz.backend.migration.entity.stock;

import mz.org.fgh.sifmoz.backend.migration.base.log.AbstractMigrationLog
import mz.org.fgh.sifmoz.backend.migration.base.record.AbstractMigrationRecord
import mz.org.fgh.sifmoz.backend.migration.base.record.MigratedRecord

class StockCenterMigrationRecord extends AbstractMigrationRecord {
    private Integer id

    private String stockcentername

    private boolean preferred

    private Set<StockMigrationRecord> stockMigrationRecords

    private String clinicuuid

    private boolean migration_status

    //------------------------------------------------

    StockCenterService stockCenterService
    ClinicService clinicService

    @Override
    List<MigrationLog> migrate() {
        System.out.println("Inside migrate method...");
        List<MigrationLog> logs = new ArrayList<>()
        getMigratedRecord().setName(this.stockcentername)
        getMigratedRecord().setCode(this.stockcentername.replaceAll(" ", "_").toUpperCase())
        getMigratedRecord().setPrefered(this.preferred)
        Clinic clinicAux
        try {
            clinicAux = clinicService.findClinicByUuid(this.clinicuuid)
        } catch (Exception e) {
            System.out.println(e.getMessage())
        } finally {
            System.out.println("Cliniccccc")
        }
        getMigratedRecord().setClinic(clinicAux)
        stockCenterService.save(getMigratedRecord())
        return logs
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
        return new StockCenter()
    }

    @Override
    StockCenter getMigratedRecord() {
        return (StockCenter) super.getMigratedRecord();
    }
}
