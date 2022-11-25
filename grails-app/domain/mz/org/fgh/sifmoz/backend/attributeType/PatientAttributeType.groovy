package mz.org.fgh.sifmoz.backend.attributeType


import mz.org.fgh.sifmoz.backend.base.BaseEntity

class PatientAttributeType extends BaseEntity {
    String id
    String code
    String name
    String description
    String datatype

    static mapping = {
        id generator: "assigned"
    }

    static constraints = {
        code nullable: false, unique: true
        name nullable: false, unique: true
        description nullable: true, blank: true
        datatype nullable: true, blank: true
    }
}
