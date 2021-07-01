package mz.org.fgh.sifmoz.backend.stock.entrance

import grails.gorm.services.Service

@Service(StockEntrance)
interface StockEntranceService {

    StockEntrance get(Serializable id)

    List<StockEntrance> list(Map args)

    Long count()

    StockEntrance delete(Serializable id)

    StockEntrance save(StockEntrance stockEntrance)

}
