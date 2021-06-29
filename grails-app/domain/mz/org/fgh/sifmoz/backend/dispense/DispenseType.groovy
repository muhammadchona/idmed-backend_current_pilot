package mz.org.fgh.sifmoz.backend.dispense

import grails.rest.Resource

@Resource(uri='/api/dispenseType')
class DispenseType {

    String code
    String description

    static mapping = {
        version false
    }

    static constraints = {
        code nullable: false, unique: true
        description nullable: false
    }

    @Override
    String toString() {
        description
    }
}
