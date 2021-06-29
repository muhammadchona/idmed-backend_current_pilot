package mz.org.fgh.sifmoz.backend.appointment

import grails.rest.Resource


//@Resource(uri='/api/visit')
class Visit {

    Date visitDate

    static mapping = {
        version false
    }

    static constraints = {
        visitDate nullable: false
    }

    @Override
    String toString() {
    }
}
