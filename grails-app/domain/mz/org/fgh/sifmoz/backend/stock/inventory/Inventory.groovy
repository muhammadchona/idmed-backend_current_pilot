package mz.org.fgh.sifmoz.backend.stock.inventory

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.stock.adjustment.StockAdjustment

@Resource(uri='/api/inventory')
class Inventory {

    Date startDate
    Date endDate
    boolean open
    int sequence
    List<StockAdjustment> adjustments

    static mapping = {
        version false
    }

    static constraints = {
        startDate(nullable: false, blank: false, validator: { startDate, urc ->
            return ((startDate <= new Date() && urc.endDate > startDate))
        })

        endDate(nullable: false, blank: false, validator: { endDate, urc ->
            return ((endDate <= new Date() && urc.startDate < endDate))
        })
    }

    public int  generateSequence(){
        return 0;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", open=" + open +
                ", sequence=" + sequence +
                '}';
    }
}
