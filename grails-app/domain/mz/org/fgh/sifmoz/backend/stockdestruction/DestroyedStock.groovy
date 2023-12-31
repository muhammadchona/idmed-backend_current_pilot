package mz.org.fgh.sifmoz.backend.stockdestruction

import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.stockadjustment.StockDestructionAdjustment

class DestroyedStock extends BaseEntity {
    String id
    String notes
    String updateStatus
    Date date
    Clinic clinic
    static hasMany = [adjustments : StockDestructionAdjustment]

    static mapping = {
        id generator: "assigned"
    }

    static constraints = {
        notes(nullable: false, blank: false)
    }

    @Override
    String toString() {
        return "DestroyedStock{" +
                "notes='" + notes + '\'' +
                ", updateStatus='" + updateStatus + '\'' +
                '}'
    }
}
