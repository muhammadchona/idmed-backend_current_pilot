package mz.org.fgh.sifmoz.backend.clinicSector

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinic.Clinic

@Resource(uri='/api/clinicSector')
class ClinicSector {

    String code
    String description
    String uuid = UUID.randomUUID().toString()

    static belongsTo = [clinic: Clinic]

    static constraints = {
        code nullable: false, unique: true
        description nullable: false
    }
}
