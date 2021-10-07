package mz.org.fgh.sifmoz.backend.stockadjustment

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.stock.Stock
import mz.org.fgh.sifmoz.backend.stockdestruction.DestroyedStock
import mz.org.fgh.sifmoz.backend.stockrefered.ReferedStockMoviment

// @Resource(uri='/api/referenceAdjustment')

class StockReferenceAdjustment extends StockAdjustment{

    static hasOne = [reference: ReferedStockMoviment]

    StockReferenceAdjustment() {
    }

    StockReferenceAdjustment(ReferedStockMoviment reference, Stock adjustedStock) {
        super(adjustedStock)
        this.reference = reference
    }
    static constraints = {
        reference nullable: false
    }
}
