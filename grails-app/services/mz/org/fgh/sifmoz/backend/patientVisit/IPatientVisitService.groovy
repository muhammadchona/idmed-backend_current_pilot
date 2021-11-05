package mz.org.fgh.sifmoz.backend.patientVisit

import grails.gorm.services.Service


interface IPatientVisitService {

    PatientVisit get(Serializable id)

    List<PatientVisit> list(Map args)

    Long count()

    PatientVisit delete(Serializable id)

    PatientVisit save(PatientVisit visit)

    List<PatientVisit> getAllByPatientId(String patientId)

}
