package mz.org.fgh.sifmoz.backend.stock

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional

@Transactional
@Service(Stock)
abstract class StockService implements IStockService{


}
