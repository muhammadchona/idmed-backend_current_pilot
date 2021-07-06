package mz.org.fgh.sifmoz.backend.packagedDrug

import grails.gorm.services.Service

@Service(PackagedDrug)
interface PackagedDrugService {

    PackagedDrug get(Serializable id)

    List<PackagedDrug> list(Map args)

    Long count()

    PackagedDrug delete(Serializable id)

    PackagedDrug save(PackagedDrug packagedDrug)

}
