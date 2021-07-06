package mz.org.fgh.sifmoz.backend.patientVisit

import grails.gorm.services.Service
import mz.org.fgh.sifmoz.backend.patientVisit.PatientVisit

@Service(PatientVisit)
interface PatientVisitService {

    PatientVisit get(Serializable id)

    List<PatientVisit> list(Map args)

    Long count()

    PatientVisit delete(Serializable id)

    PatientVisit save(PatientVisit visit)

}
