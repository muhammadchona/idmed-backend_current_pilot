package mz.org.fgh.sifmoz.backend.patientIdentifier

import grails.gorm.services.Service

@Service(PatientServiceIdentifier)
interface PatientIdentifierService {

    PatientServiceIdentifier get(Serializable id)

    List<PatientServiceIdentifier> list(Map args)

    Long count()

    PatientServiceIdentifier delete(Serializable id)

    PatientServiceIdentifier save(PatientServiceIdentifier patientIdentifier)

}
