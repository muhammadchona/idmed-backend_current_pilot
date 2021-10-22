package mz.org.fgh.sifmoz.backend.healthInformationSystem

import mz.org.fgh.sifmoz.backend.interoperabilityAttribute.InteroperabilityAttribute

class HealthInformationSystem {

    String id
    String abbreviation
    String description

    static hasMany = [interoperabilityAttributes: InteroperabilityAttribute]

    static mapping = {
        id generator: "uuid"
    }
    static constraints = {
        abbreviation nullable: false, unique: true
        description nullable: false
    }
}
