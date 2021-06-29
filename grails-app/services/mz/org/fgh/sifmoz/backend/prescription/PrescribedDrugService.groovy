package mz.org.fgh.sifmoz.backend.prescription

import grails.gorm.services.Service

@Service(PrescribedDrug)
interface PrescribedDrugService {

    PrescribedDrug get(Serializable id)

    List<PrescribedDrug> list(Map args)

    Long count()

    PrescribedDrug delete(Serializable id)

    PrescribedDrug save(PrescribedDrug prescribedDrug)

}
