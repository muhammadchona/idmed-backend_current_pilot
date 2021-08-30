package mz.org.fgh.sifmoz.backend.patientVisit

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinic.Clinic

class PatientVisit {
    String id
    Date visitDate
    Clinic clinic

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
    }
}
