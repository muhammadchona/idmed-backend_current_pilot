package mz.org.fgh.sifmoz.backend.patientProgram

import grails.gorm.services.Service

@Service(PatientProgram)
interface PatientProgramService {

    PatientProgram get(Serializable id)

    List<PatientProgram> list(Map args)

    Long count()

    PatientProgram delete(Serializable id)

    PatientProgram save(PatientProgram patientProgram)

}
