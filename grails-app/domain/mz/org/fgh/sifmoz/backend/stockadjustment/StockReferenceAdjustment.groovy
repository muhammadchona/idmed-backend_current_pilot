package mz.org.fgh.sifmoz.backend.stockadjustment


import mz.org.fgh.sifmoz.backend.stock.Stock
import mz.org.fgh.sifmoz.backend.stockrefered.ReferedStockMoviment

class StockReferenceAdjustment extends StockAdjustment{
    String id
    static hasOne = [reference: ReferedStockMoviment]

    StockReferenceAdjustment() {
    }

    StockReferenceAdjustment(ReferedStockMoviment reference, Stock adjustedStock) {
        super(adjustedStock)
        this.reference = reference
    }

    static mapping = {
        id generator: "assigned"
    }

    static constraints = {
        reference nullable: false
    }
}
