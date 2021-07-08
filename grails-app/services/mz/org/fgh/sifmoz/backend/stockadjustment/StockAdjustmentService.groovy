package mz.org.fgh.sifmoz.backend.stockadjustment

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional

@Transactional
@Service(StockAdjustment)
abstract class StockAdjustmentService implements IStockAdjustmentService{

    @Override
    void processAdjustment(StockAdjustment adjustment) {

        if (adjustment.isInventoryAdjustment()) {
            adjustment.getAdjustedStock().setStockMoviment(adjustment.getAdjustedValue())
        }else if (adjustment.isDestructionAdjustment()){
            adjustment.getAdjustedStock().setStockMoviment(adjustment.getAdjustedStock().getStockMoviment() - adjustment.getAdjustedValue())
        }else if (adjustment.isReferenceAdjustment()){
            if (adjustment.isPosetiveAdjustment()){
                if (adjustment.getAdjustedStock().getId() > 0){
                    adjustment.getAdjustedStock().setStockMoviment(adjustment.getAdjustedStock().getStockMoviment() + adjustment.getAdjustedValue())
                }
            }
        }

        adjustment.getAdjustedStock().save()
    }
}
