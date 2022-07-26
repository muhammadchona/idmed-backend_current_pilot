package mz.org.fgh.sifmoz.backend.provincialServer

import grails.gorm.services.Query
import grails.gorm.services.Service
import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.stock.Stock


interface IProvincialServerService {

    ProvincialServer get(Serializable id)

    List<ProvincialServer> list(Map args)

    Long count()

    ProvincialServer delete(Serializable id)

    ProvincialServer save(ProvincialServer provincialServer)

    ProvincialServer getByCodeAndDestination(String code, String destination)

}
