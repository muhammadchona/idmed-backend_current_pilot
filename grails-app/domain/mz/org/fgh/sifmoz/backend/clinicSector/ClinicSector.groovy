package mz.org.fgh.sifmoz.backend.clinicSector

import grails.rest.Resource

@Resource(uri='/api/clinicSector')
class ClinicSector {

    String code
    String description
    String uuid = UUID.randomUUID().toString()

    static constraints = {
    }
}
