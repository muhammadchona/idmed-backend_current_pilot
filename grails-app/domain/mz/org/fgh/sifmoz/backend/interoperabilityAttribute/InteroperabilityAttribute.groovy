package mz.org.fgh.sifmoz.backend.interoperabilityAttribute

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.healthInformationSystem.HealthInformationSystem
import mz.org.fgh.sifmoz.backend.interoperabilityType.InteroperabilityType

class InteroperabilityAttribute extends BaseEntity {

    String id
    @JsonManagedReference
    InteroperabilityType interoperabilityType
    String value

    @JsonBackReference
    HealthInformationSystem healthInformationSystem

    static belongsTo = [healthInformationSystem : HealthInformationSystem]

    static mapping = {
        id generator: "assigned"
    }

    def beforeInsert() {
        if (!id) {
            id = UUID.randomUUID()
        }
    }

    static constraints = {
        value nullable: false, blank: false, unique: ['healthInformationSystem','interoperabilityType']
    }
}
