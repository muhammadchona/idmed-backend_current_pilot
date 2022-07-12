package mz.org.fgh.sifmoz.backend.identifierType

import mz.org.fgh.sifmoz.backend.base.BaseEntity

class IdentifierType extends BaseEntity {
    String id
    String code
    String description
    String pattern

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        code nullable: false, unique: true
        description nullable: false
        pattern nullable: true
    }
}
