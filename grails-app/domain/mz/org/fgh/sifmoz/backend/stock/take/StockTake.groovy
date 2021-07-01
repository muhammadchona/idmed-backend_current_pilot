package mz.org.fgh.sifmoz.backend.stock.take

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.stock.adjustment.StockAdjustment

@Resource(uri='/api/stockTake')
class StockTake {

    Date endDate
    Date startDate
    String stockTakeNumber
    boolean open
    StockAdjustment adjustment

    static mapping = {
        version false
    }

    static constraints = {
    }

    @Override
    public String toString() {
        return "StockTake{" +
                "endDate=" + endDate +
                ", startDate=" + startDate +
                ", stockTakeNumber='" + stockTakeNumber + '\'' +
                ", open=" + open +
                '}';
    }
}
