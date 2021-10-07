package mz.org.fgh.sifmoz.backend.stockadjustment

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.stock.Stock
import mz.org.fgh.sifmoz.backend.stockinventory.Inventory

// @Resource(uri='/api/inventoryAdjustment')
class InventoryStockAdjustment extends StockAdjustment{
    static hasOne = [inventory: Inventory]

    InventoryStockAdjustment() {
    }

    InventoryStockAdjustment(Inventory inventory, Stock adjustedStock) {
        super(adjustedStock)
        this.inventory = inventory
    }

    static constraints = {
    }
}
