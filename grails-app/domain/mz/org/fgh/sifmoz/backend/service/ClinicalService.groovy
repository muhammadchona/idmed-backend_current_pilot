package mz.org.fgh.sifmoz.backend.service

import com.fasterxml.jackson.annotation.JsonManagedReference
import mz.org.fgh.sifmoz.backend.episode.Episode
import mz.org.fgh.sifmoz.backend.identifierType.IdentifierType
import mz.org.fgh.sifmoz.backend.serviceattribute.ClinicalServiceAttribute
import mz.org.fgh.sifmoz.backend.therapeuticRegimen.TherapeuticRegimen

class ClinicalService {

    String id
    String code
    String description
    @JsonManagedReference
    IdentifierType identifierType

    @JsonManagedReference
    static hasMany = [attributes: ClinicalServiceAttribute,
                      therapeuticRegimens: TherapeuticRegimen]

    static mapping = {
        id generator: "uuid"
    }
    static constraints = {
        code nullable: false, unique: true
        description nullable: false
        attributes nullable: true
        therapeuticRegimens nullable: true

    }
}
