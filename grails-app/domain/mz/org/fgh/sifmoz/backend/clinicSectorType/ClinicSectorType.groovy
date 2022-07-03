package mz.org.fgh.sifmoz.backend.clinicSectorType

import com.fasterxml.jackson.annotation.JsonIgnore
import mz.org.fgh.sifmoz.backend.clinicSector.ClinicSector

class ClinicSectorType {

    String id
    String code
    String description

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        code nullable: false, unique: true
        description nullable: false
    }
}
