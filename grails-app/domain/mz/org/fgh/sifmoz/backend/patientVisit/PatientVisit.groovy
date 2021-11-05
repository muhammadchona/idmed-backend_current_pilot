package mz.org.fgh.sifmoz.backend.patientVisit

import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.patientVisitDetails.PatientVisitDetails

class PatientVisit {
    String id
    Date visitDate
    Clinic clinic

    static hasMany = [patientVisitDetails: PatientVisitDetails]

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
    }
}
