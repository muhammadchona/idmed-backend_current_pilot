package mz.org.fgh.sifmoz.backend.patientVisit

import grails.rest.Resource

@Resource(uri='/api/patientVisit')
class PatientVisit {

    Date visitDate

    static constraints = {
    }
}
