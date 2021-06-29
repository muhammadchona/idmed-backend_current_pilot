package mz.org.fgh.sifmoz.backend.clinic

import grails.rest.Resource

@Resource(uri='/api/clinicSector')
class ClinicSector {

    String code
    String description
    String uuid = UUID.randomUUID().toString()

    static mapping = {
        version false
    }

    static constraints = {
        code nullable: false, unique: true
        description nullable: false
        uuid nullable: false, unique: true
    }

    @Override
    String toString() {
        description
    }
}
