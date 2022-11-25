package mz.org.fgh.sifmoz.backend.stockadjustment

import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.stock.Stock
import mz.org.fgh.sifmoz.backend.stockoperation.StockOperationType

abstract class StockAdjustment extends BaseEntity {
    String id
    Date captureDate
    String notes
    int stockTake
    int adjustedValue
    int balance
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
        id generator: "assigned"
    }

    static constraints = {
        notes(nullable: true)
        adjustedValue(min: 0)
        balance min: 0
        operation nullable: true
    }


    @Override
    String toString() {
        return "StockAdjustment{" +
                "captureDate=" + captureDate +
                ", notes='" + notes + '\'' +
                ", stockTake=" + stockTake +
                ", adjustedValue=" + adjustedValue +
                ", finalised=" + finalised +
                '}'
    }
}
