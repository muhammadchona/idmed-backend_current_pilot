package mz.org.fgh.sifmoz.backend.prescription

import mz.org.fgh.sifmoz.backend.base.BaseEntity

class SpetialPrescriptionMotive extends BaseEntity {

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
