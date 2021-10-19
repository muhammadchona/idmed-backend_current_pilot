package mz.org.fgh.sifmoz.backend.interoperabilityAttribute

import mz.org.fgh.sifmoz.backend.healthInformationSystem.HealthInformationSystem
import mz.org.fgh.sifmoz.backend.interoperabilityType.InteroperabilityType

class InteroperabilityAttribute {

    InteroperabilityType interoperabilityType
    String value

    static belongsTo = [healthInformationSystem : HealthInformationSystem]

    static constraints = {
        value nullable: false, blank: false, unique: ['healthInformationSystem','interoperabilityType']
    }
}
