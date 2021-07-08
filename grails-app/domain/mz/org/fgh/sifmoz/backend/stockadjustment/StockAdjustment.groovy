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
    Inventory inventory
    ReferedStockMoviment referedStockMoviment
    DestroyedStock destroyedStock
    //static hasMany = [inventories: Inventory, stockReferences: ReferedStockMoviment, stckDestructions: DestroyedStock]

    StockAdjustment() {
    }

    StockAdjustment(Inventory inventory, Stock adjustedStock) {
        this.adjustedStock = adjustedStock
        this.inventory = inventory
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

    boolean isInventoryAdjustment() {
        return this.getInventory() != null && this.getInventoryId() > 0
    }

    boolean isDestructionAdjustment() {
        return this.getDestroyedStock()!= null && this.getDestroyedStockId() > 0
    }

    boolean isReferenceAdjustment() {
        return this.getReferedStockMoviment()!= null && this.getReferedStockMovimentId() > 0
    }
}
