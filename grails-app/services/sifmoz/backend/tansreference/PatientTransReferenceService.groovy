package sifmoz.backend.tansreference

import grails.gorm.services.Service

@Service(PatientTransReference)
interface PatientTransReferenceService {

    PatientTransReference get(Serializable id)

    List<PatientTransReference> list(Map args)

    Long count()

    PatientTransReference delete(Serializable id)

    PatientTransReference save(PatientTransReference patientTransReference)

}
