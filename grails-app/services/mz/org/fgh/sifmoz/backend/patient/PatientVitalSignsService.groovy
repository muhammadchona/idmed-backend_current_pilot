package mz.org.fgh.sifmoz.backend.patient

import grails.gorm.services.Service
import mz.org.fgh.sifmoz.backend.patient.vitalsigns.PatientVitalSigns

@Service(PatientVitalSigns)
interface PatientVitalSignsService {

    PatientVitalSigns get(Serializable id)

    List<PatientVitalSigns> list(Map args)

    Long count()

    PatientVitalSigns delete(Serializable id)

    PatientVitalSigns save(PatientVitalSigns patientVitalSigns)

}
