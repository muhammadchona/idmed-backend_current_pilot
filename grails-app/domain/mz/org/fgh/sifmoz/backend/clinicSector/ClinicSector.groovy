package mz.org.fgh.sifmoz.backend.clinicSector

import com.fasterxml.jackson.annotation.JsonIgnore
import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.clinicSectorType.ClinicSectorType

class ClinicSector {
    String id
    String code
    String description
    String uuid
    boolean active
    ClinicSectorType clinicSectorType

    @JsonIgnore
    Clinic clinic
    static belongsTo = [Clinic]

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        code nullable: false, unique: true
        description nullable: false
        uuid unique: true
    }
}
