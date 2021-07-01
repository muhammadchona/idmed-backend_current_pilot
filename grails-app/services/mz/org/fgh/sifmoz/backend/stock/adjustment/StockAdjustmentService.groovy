package mz.org.fgh.sifmoz.backend.stock.adjustment

import grails.gorm.services.Service

@Service(StockAdjustment)
interface StockAdjustmentService {

    StockAdjustment get(Serializable id)

    List<StockAdjustment> list(Map args)

    Long count()

    StockAdjustment delete(Serializable id)

    StockAdjustment save(StockAdjustment stockAdjustment)

}
