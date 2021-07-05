package mz.org.fgh.sifmoz.backend.prescriptionDetail

import grails.gorm.services.Service

@Service(PrescriptionDetail)
interface PrescriptionDetailService {

    PrescriptionDetail get(Serializable id)

    List<PrescriptionDetail> list(Map args)

    Long count()

    PrescriptionDetail delete(Serializable id)

    PrescriptionDetail save(PrescriptionDetail prescriptionDetail)

}
