package mz.org.fgh.sifmoz.backend.stockadjustment

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional

@Transactional
@Service(StockReferenceAdjustment)
abstract class StockReferenceAdjustmentService extends StockAdjustmentService implements IStockReferenceAdjustmentService{

    def serviceMethod() {

    }
}
