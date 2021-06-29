package mz.org.fgh.sifmoz.backend.patient

import grails.gorm.services.Service

@Service(PatientAttribute)
interface PatientAttributeService {

    PatientAttribute get(Serializable id)

    List<PatientAttribute> list(Map args)

    Long count()

    PatientAttribute delete(Serializable id)

    PatientAttribute save(PatientAttribute patientAttribute)

}
