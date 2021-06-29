package mz.org.fgh.sifmoz.backend.dispense

import grails.gorm.services.Service

@Service(DispensedDrug)
interface DispensedDrugService {

    DispensedDrug get(Serializable id)

    List<DispensedDrug> list(Map args)

    Long count()

    DispensedDrug delete(Serializable id)

    DispensedDrug save(DispensedDrug dispensedDrug)

}
