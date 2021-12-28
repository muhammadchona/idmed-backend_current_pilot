package mz.org.fgh.sifmoz.backend.stockadjustment

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.appointment.Appointment

@Service(StockAdjustment)
interface StockAdjustmentService {

    StockAdjustment get(Serializable id)

    List<StockAdjustment> list(Map args)

    Long count()

    StockAdjustment delete(Serializable id)

    StockAdjustment save(StockAdjustment stockAdjustment)
}
