package mz.org.fgh.sifmoz.backend.stockinventory

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.stockadjustment.StockAdjustment

@Resource(uri='/api/stockInventory')
class Inventory {

    Date startDate
    Date endDate
    boolean open
    int sequence
    static hasMany = [adjustments : StockAdjustment]

    static constraints = {
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
