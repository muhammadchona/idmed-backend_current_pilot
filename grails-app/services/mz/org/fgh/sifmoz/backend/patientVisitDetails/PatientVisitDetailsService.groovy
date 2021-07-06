package mz.org.fgh.sifmoz.backend.patientVisitDetails

import grails.gorm.services.Service

@Service(PatientVisitDetails)
interface PatientVisitDetailsService {

    PatientVisitDetails get(Serializable id)

    List<PatientVisitDetails> list(Map args)

    Long count()

    PatientVisitDetails delete(Serializable id)

    PatientVisitDetails save(PatientVisitDetails patientVisitDetails)

}
