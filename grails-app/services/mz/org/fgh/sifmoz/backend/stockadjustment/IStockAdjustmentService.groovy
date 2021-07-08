package mz.org.fgh.sifmoz.backend.stockadjustment

import grails.gorm.services.Service


interface IStockAdjustmentService {

    StockAdjustment get(Serializable id)

    List<StockAdjustment> list(Map args)

    Long count()

    StockAdjustment delete(Serializable id)

    StockAdjustment save(StockAdjustment stockAdjustment)

    void processAdjustment(StockAdjustment adjustment)

}
