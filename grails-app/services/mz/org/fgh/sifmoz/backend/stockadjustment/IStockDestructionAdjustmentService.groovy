package mz.org.fgh.sifmoz.backend.stockadjustment

import grails.gorm.services.Service


interface IStockDestructionAdjustmentService extends IStockAdjustmentService{

    StockDestructionAdjustment get(Serializable id)

    List<StockDestructionAdjustment> list(Map args)

    Long count()

    StockDestructionAdjustment delete(Serializable id)

    StockDestructionAdjustment save(StockDestructionAdjustment stockDestructionAdjustment)

}
