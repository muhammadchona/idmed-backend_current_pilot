package mz.org.fgh.sifmoz.backend.stockentrance

import grails.gorm.services.Service


interface IStockEntranceService {

    StockEntrance get(Serializable id)

    List<StockEntrance> list(Map args)

    Long count()

    StockEntrance delete(Serializable id)

    StockEntrance save(StockEntrance stockEntrance)

    List<StockEntrance> getAllByClinicId(String clinicId, int offset, int max)

}
