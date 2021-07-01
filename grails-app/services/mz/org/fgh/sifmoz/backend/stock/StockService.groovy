package mz.org.fgh.sifmoz.backend.stock

import grails.gorm.services.Service

@Service(Stock)
interface StockService {

    Stock get(Serializable id)

    List<Stock> list(Map args)

    Long count()

    Stock delete(Serializable id)

    Stock save(Stock stock)

}
