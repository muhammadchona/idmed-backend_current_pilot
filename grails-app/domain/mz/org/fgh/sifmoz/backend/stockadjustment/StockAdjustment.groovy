package mz.org.fgh.sifmoz.backend.stockadjustment

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.stock.Stock
import mz.org.fgh.sifmoz.backend.stockdestruction.DestroyedStock
import mz.org.fgh.sifmoz.backend.stockinventory.Inventory
import mz.org.fgh.sifmoz.backend.stockoperation.StockOperationType
import mz.org.fgh.sifmoz.backend.stockrefered.ReferedStockMoviment


abstract class StockAdjustment {

    Date captureDate
    String notes
    int stockTake
    int adjustedValue
    int finalised
    Stock adjustedStock
    StockOperationType operation

    StockAdjustment() {
    }

    StockAdjustment(Stock adjustedStock) {
        this.adjustedStock = adjustedStock
    }

    static mapping = {
        version false
    }

    static constraints = {
        /*captureDate(nullable: false, blank: false, validator: { captureDate, urc ->
            return ((captureDate <= new Date()))
        })
        notes(nullable: false, blank: false)*/
        stockTake(min: 0)
        adjustedValue(min: 0)
    }


    @Override
    String toString() {
        return "StockAdjustment{" +
                "captureDate=" + captureDate +
                ", notes='" + notes + '\'' +
                ", stockTake=" + stockTake +
                ", adjustedValue=" + adjustedValue +
                ", finalised=" + finalised +
                '}';
    }
}
