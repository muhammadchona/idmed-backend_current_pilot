package mz.org.fgh.sifmoz.backend.patient

import grails.rest.Resource


@Resource(uri='/api/patientIdentifier')
class PatientIdentifier {

    String value
    boolean prefered

    static mapping = {
        version false
    }

    static constraints = {

        value nullable: false
    }

    @Override
    String toString() {
        value
    }

}
