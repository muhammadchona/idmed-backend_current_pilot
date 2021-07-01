package mz.org.fgh.sifmoz.backend.stock.adjustment

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.stock.Stock
import mz.org.fgh.sifmoz.backend.stock.operationtype.StockOperationType

@Resource(uri='/api/stockAdjustment')
class StockAdjustment {

    Date captureDate
    String notes
    int stockTake
    int adjustedValue
    int finalised
    Stock adjustedStock
    StockOperationType operation

    static mapping = {
        version false
    }

    static constraints = {
        captureDate(nullable: false, blank: false, validator: { captureDate, urc ->
            return ((captureDate <= new Date()))
        })
        notes(nullable: false, blank: false)
        stockTake(min: 0)
        adjustedValue(min: 0)
    }

    @Override
    public String toString() {
        return "StockAdjustment{" +
                "captureDate=" + captureDate +
                ", notes='" + notes + '\'' +
                ", stockTake=" + stockTake +
                ", adjustedValue=" + adjustedValue +
                ", finalised=" + finalised +
                '}';
    }
}
