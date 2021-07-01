package mz.org.fgh.sifmoz.backend.stock.operationtype

import grails.gorm.services.Service

@Service(StockOperationType)
interface StockOperationTypeService {

    StockOperationType get(Serializable id)

    List<StockOperationType> list(Map args)

    Long count()

    StockOperationType delete(Serializable id)

    StockOperationType save(StockOperationType stockOperationType)

}
