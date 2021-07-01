package mz.org.fgh.sifmoz.backend.stock

import grails.gorm.services.Service
import mz.org.fgh.sifmoz.backend.stock.center.StockCenter

@Service(StockCenter)
interface StockCenterService {

    StockCenter get(Serializable id)

    List<StockCenter> list(Map args)

    Long count()

    StockCenter delete(Serializable id)

    StockCenter save(StockCenter stockCenter)

}
