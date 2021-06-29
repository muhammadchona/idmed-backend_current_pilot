package mz.org.fgh.sifmoz.backend.prescription

import grails.gorm.services.Service

@Service(Prescription)
interface PrescriptionService {

    Prescription get(Serializable id)

    List<Prescription> list(Map args)

    Long count()

    Prescription delete(Serializable id)

    Prescription save(Prescription prescription)

}
