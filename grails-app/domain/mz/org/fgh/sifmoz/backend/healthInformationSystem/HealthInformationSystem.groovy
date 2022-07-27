package mz.org.fgh.sifmoz.backend.healthInformationSystem

import com.fasterxml.jackson.annotation.JsonManagedReference
import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.interoperabilityAttribute.InteroperabilityAttribute

class HealthInformationSystem extends BaseEntity {

    String id
    String abbreviation
    String description
    boolean active

    @JsonManagedReference
    static hasMany = [interoperabilityAttributes: InteroperabilityAttribute]

    static mapping = {
        id generator: "assigned"
        interoperabilityAttributes (cascade: "all-delete-orphan")
    }

    def beforeInsert() {
        if (!id) {
            id = UUID.randomUUID()
        }
    }
    static constraints = {
        abbreviation nullable: false, unique: true
        description nullable: false
    }
}
