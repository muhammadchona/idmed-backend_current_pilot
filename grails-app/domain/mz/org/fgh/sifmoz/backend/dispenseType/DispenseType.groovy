package mz.org.fgh.sifmoz.backend.dispenseType

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinic.Clinic

class DispenseType {
    String id
    String code
    String description

    static mapping = {
        id generator: "uuid"
    }
    static constraints = {
        code nullable: false, unique: true
        description nullable: false, blank: false

    }
}
