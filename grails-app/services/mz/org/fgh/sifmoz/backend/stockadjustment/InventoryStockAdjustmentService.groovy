package mz.org.fgh.sifmoz.backend.stockadjustment

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional

@Transactional
@Service(InventoryStockAdjustment)
abstract class InventoryStockAdjustmentService implements IInventoryStockAdjustmentService{


}
