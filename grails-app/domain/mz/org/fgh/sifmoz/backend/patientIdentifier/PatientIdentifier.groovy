package mz.org.fgh.sifmoz.backend.patientIdentifier

import grails.rest.Resource

@Resource(uri='/api/patientIdentifier')
class PatientIdentifier {

    String value
    boolean prefered

    static constraints = {
    }
}
