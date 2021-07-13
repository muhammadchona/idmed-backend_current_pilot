package mz.org.fgh.sifmoz.backend.stockdestruction

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.stockadjustment.StockAdjustment
import mz.org.fgh.sifmoz.backend.stockadjustment.StockDestructionAdjustment

@Resource(uri='/api/destroyedStock')
class DestroyedStock {

    String notes
    String updateStatus
    static hasMany = [adjustments : StockDestructionAdjustment]

    static mapping = {
        version false
    }

    static constraints = {
        notes(nullable: false, blank: false)
    }

    @Override
    public String toString() {
        return "DestroyedStock{" +
                "notes='" + notes + '\'' +
                ", updateStatus='" + updateStatus + '\'' +
                '}';
    }
}
