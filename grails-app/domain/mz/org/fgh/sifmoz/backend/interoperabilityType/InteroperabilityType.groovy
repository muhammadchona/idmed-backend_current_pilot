package mz.org.fgh.sifmoz.backend.interoperabilityType

import mz.org.fgh.sifmoz.backend.base.BaseEntity

class InteroperabilityType extends BaseEntity {

    String code
    String description

    static mapping = {
    }
    static constraints = {
        code nullable: false, unique: true
        description nullable: false
    }
}
