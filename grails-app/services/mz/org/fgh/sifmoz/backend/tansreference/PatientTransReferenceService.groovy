package mz.org.fgh.sifmoz.backend.tansreference

import grails.gorm.services.Service
import mz.org.fgh.sifmoz.backend.tansreference.PatientTransReference

@Service(PatientTransReference)
interface PatientTransReferenceService {

    PatientTransReference get(Serializable id)

    List<PatientTransReference> list(Map args)

    Long count()

    PatientTransReference delete(Serializable id)

    PatientTransReference save(PatientTransReference patientTransReference)

}
