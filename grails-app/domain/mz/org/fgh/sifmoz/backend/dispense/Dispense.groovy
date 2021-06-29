package mz.org.fgh.sifmoz.backend.dispense

import grails.rest.Resource

@Resource(uri='/api/dispense')
class Dispense {

    Date dispenseDate
    boolean modified
    Date nextPickupDate
    int supply
    String uuid = UUID.randomUUID().toString()


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
