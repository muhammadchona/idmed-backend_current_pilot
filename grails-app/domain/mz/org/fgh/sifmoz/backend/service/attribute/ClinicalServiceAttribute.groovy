package mz.org.fgh.sifmoz.backend.service.attribute

import mz.org.fgh.sifmoz.backend.service.ClinicalService
import mz.org.fgh.sifmoz.backend.service.attribute.type.ClinicalServiceAttributeType

class ClinicalServiceAttribute {
    String id
    ClinicalServiceAttributeType clinicalServiceAttributeType

    static belongsTo = [clinicalService: ClinicalService]

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
    }
}
