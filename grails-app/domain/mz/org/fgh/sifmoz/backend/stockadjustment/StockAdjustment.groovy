package mz.org.fgh.sifmoz.backend.stockadjustment

import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.stock.Stock
import mz.org.fgh.sifmoz.backend.stockdestruction.DestroyedStock
import mz.org.fgh.sifmoz.backend.stockinventory.Inventory
import mz.org.fgh.sifmoz.backend.stockoperation.StockOperationType
import mz.org.fgh.sifmoz.backend.stockrefered.ReferedStockMoviment


abstract class StockAdjustment {
    String id
    Date captureDate
    String notes
    int stockTake
    int adjustedValue
    boolean finalised
    Stock adjustedStock
    StockOperationType operation
    Clinic clinic

    StockAdjustment() {
    }

    StockAdjustment(Stock adjustedStock) {
        this.adjustedStock = adjustedStock
    }

    static mapping = {
        id generator: "uuid"
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
