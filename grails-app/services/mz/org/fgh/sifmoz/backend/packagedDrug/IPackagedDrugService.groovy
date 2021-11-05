package mz.org.fgh.sifmoz.backend.packagedDrug

import grails.gorm.services.Service
import mz.org.fgh.sifmoz.backend.prescriptionDrug.PrescribedDrug


interface IPackagedDrugService {

    PackagedDrug get(Serializable id)

    List<PackagedDrug> list(Map args)

    Long count()

    PackagedDrug delete(Serializable id)

    PackagedDrug save(PackagedDrug packagedDrug)

    List<PackagedDrug> getAllByPackId(String packId)

}
