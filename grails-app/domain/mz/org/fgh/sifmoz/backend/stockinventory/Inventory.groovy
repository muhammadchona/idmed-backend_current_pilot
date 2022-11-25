package mz.org.fgh.sifmoz.backend.stockinventory


import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.stockadjustment.InventoryStockAdjustment

class Inventory extends BaseEntity {
    String id
    Date startDate
    Date endDate
    boolean open
    boolean generic
    int sequence
    static hasMany = [adjustments : InventoryStockAdjustment]
    static transients = ['inventoryDrugs']
    List<Drug> inventoryDrugs
    Clinic clinic
    static mapping = {
        id generator: "assigned"
    }

    static constraints = {
        startDate(nullable: false, blank: false, validator: { startDate, urc ->
            return ((startDate <= new Date() ))
        })

        endDate(nullable: true, blank: true)
        adjustments( nulable: true)
    }

     int  generateSequence(){
        return 0
    }

     void close() {
        this.open = false
        this.endDate = new Date()
    }

    @Override
    String toString() {
        return "Inventory{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", open=" + open +
                ", sequence=" + sequence +
                '}'
    }

}
