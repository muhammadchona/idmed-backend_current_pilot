package mz.org.fgh.sifmoz.backend.clinicSector

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinic.Clinic

class ClinicSector {
    String id
    String code
    String description
   boolean active

    static belongsTo = [clinic: Clinic]

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        code nullable: false, unique: true
        description nullable: false
    }
}
