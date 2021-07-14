package mz.org.fgh.sifmoz.backend.stockadjustment

import grails.gorm.transactions.Transactional

@Transactional
abstract class StockAdjustmentService implements IStockAdjustmentService{

    @Override
    void processAdjustment(StockAdjustment adjustment) {

    }

    def serviceMethod() {

    }
}
