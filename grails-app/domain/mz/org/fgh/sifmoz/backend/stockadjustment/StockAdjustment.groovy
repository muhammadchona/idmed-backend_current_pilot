package mz.org.fgh.sifmoz.backend.stockadjustment

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.stock.Stock
import mz.org.fgh.sifmoz.backend.stockdestruction.DestroyedStock
import mz.org.fgh.sifmoz.backend.stockinventory.Inventory
import mz.org.fgh.sifmoz.backend.stockoperation.StockOperationType
import mz.org.fgh.sifmoz.backend.stockrefered.ReferedStockMoviment

@Resource(uri='/api/stockAdjustment')
class StockAdjustment {

    Date captureDate
    String notes
    int stockTake
    int adjustedValue
    int finalised
    Stock adjustedStock
    StockOperationType operation
    static belongsTo = [Inventory, ReferedStockMoviment, DestroyedStock]
    static hasMany = [inventories: Inventory, stockReferences: ReferedStockMoviment, stckDestructions: DestroyedStock]

    static mapping = {
    }
    static constraints = {

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
