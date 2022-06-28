package sifmoz.backend.tansreference

import grails.gorm.services.Service

@Service(PatientTransReferenceType)
interface PatientTransReferenceTypeService {

    PatientTransReferenceType get(Serializable id)

    List<PatientTransReferenceType> list(Map args)

    Long count()

    PatientTransReferenceType delete(Serializable id)

    PatientTransReferenceType save(PatientTransReferenceType patientTransReferenceType)

}
