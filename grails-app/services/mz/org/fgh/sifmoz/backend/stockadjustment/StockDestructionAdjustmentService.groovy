package mz.org.fgh.sifmoz.backend.stockadjustment

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional

@Transactional
@Service(StockDestructionAdjustment)
abstract class StockDestructionAdjustmentService extends StockAdjustmentService implements IStockDestructionAdjustmentService{

    def serviceMethod() {

    }
}
