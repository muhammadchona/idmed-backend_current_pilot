package mz.org.fgh.sifmoz.backend.service

import mz.org.fgh.sifmoz.backend.episode.Episode
import mz.org.fgh.sifmoz.backend.serviceattribute.ClinicalServiceAttribute
import mz.org.fgh.sifmoz.backend.therapeuticRegimen.TherapeuticRegimen

class ClinicalService {

    String id
    String code
    String description

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
