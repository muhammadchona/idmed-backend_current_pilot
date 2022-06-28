package sifmoz.backend.tansreference

import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.patient.Patient
import mz.org.fgh.sifmoz.backend.service.ClinicalService

class PatientTransReference {

    String id
    Clinic origin
    Clinic destination
    ClinicalService clinicalService
    String syncStatus
    PatientTransReferenceType operationType

    static belongsTo = [patient: Patient]
    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
    }
}
