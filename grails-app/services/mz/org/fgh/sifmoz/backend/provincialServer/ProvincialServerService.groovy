package mz.org.fgh.sifmoz.backend.provincialServer

import grails.gorm.services.Query
import grails.gorm.services.Service
import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.stock.Stock

@Service(ProvincialServer)
interface ProvincialServerService {

    ProvincialServer get(Serializable id)

    List<ProvincialServer> list(Map args)

    Long count()

    ProvincialServer delete(Serializable id)

    ProvincialServer save(ProvincialServer provincialServer)

    @Query("select ${p} from ${ProvincialServer p} where p.code = ${code} and p.destination =  ${destination}")
    ProvincialServer getByCodeAndDestination(String code, String destination)

}
