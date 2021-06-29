package mz.org.fgh.sifmoz.backend.appointment

import grails.rest.Resource


//@Resource(uri='/api/appointment')
class Appointment {

    Date appointmentDate
    Date visitDate

    static mapping = {
        version false
    }

    static constraints = {
        appointmentDate nullable: false
        visitDate nullable: false
    }

    @Override
    String toString() {
    }
}
