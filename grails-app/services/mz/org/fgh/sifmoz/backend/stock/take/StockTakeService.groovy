package mz.org.fgh.sifmoz.backend.stock.take

import grails.gorm.services.Service

@Service(StockTake)
interface StockTakeService {

    StockTake get(Serializable id)

    List<StockTake> list(Map args)

    Long count()

    StockTake delete(Serializable id)

    StockTake save(StockTake stockTake)

}
