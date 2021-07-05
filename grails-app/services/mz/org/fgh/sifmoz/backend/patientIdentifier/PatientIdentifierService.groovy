package mz.org.fgh.sifmoz.backend.patientIdentifier

import grails.gorm.services.Service

@Service(PatientIdentifier)
interface PatientIdentifierService {

    PatientIdentifier get(Serializable id)

    List<PatientIdentifier> list(Map args)

    Long count()

    PatientIdentifier delete(Serializable id)

    PatientIdentifier save(PatientIdentifier patientIdentifier)

}
