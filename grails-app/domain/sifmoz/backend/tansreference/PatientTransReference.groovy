package sifmoz.backend.tansreference

import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.patient.Patient
import mz.org.fgh.sifmoz.backend.patientIdentifier.PatientServiceIdentifier

class PatientTransReference {

    String id
    Clinic origin
    String destination
    PatientServiceIdentifier identifier
    String syncStatus
    PatientTransReferenceType operationType
    Date operationDate
    Date creationDate

    static belongsTo = [patient: Patient]
    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
    }
}
