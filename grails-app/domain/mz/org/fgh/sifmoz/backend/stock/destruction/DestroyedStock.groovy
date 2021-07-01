package mz.org.fgh.sifmoz.backend.stock.destruction

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.stock.adjustment.StockAdjustment

@Resource(uri='/api/destroyedStock')
class DestroyedStock {

    String notes
    String updateStatus
    List<StockAdjustment> adjustments

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
