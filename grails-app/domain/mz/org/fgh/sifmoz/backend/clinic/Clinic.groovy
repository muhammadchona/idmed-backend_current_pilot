package mz.org.fgh.sifmoz.backend.clinic

import grails.rest.Resource


@Resource(uri='/api/clinic')
class Clinic {

    String notes
    String telephone
    String clinicName

    static mapping = {
        version false
    }

    static constraints = {

    }

    @Override
    String toString() {
    }
}
