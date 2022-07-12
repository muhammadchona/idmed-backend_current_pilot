package mz.org.fgh.sifmoz.backend.serviceattributetype

import mz.org.fgh.sifmoz.backend.base.BaseEntity

class ClinicalServiceAttributeType extends BaseEntity {
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
