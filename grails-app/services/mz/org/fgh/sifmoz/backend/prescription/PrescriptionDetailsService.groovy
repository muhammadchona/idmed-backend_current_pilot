package mz.org.fgh.sifmoz.backend.prescription

import grails.gorm.services.Service

@Service(PrescriptionDetails)
interface PrescriptionDetailsService {

    PrescriptionDetails get(Serializable id)

    List<PrescriptionDetails> list(Map args)

    Long count()

    PrescriptionDetails delete(Serializable id)

    PrescriptionDetails save(PrescriptionDetails prescriptionDetails)

}
