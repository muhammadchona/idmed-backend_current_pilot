package mz.org.fgh.sifmoz.backend.clinicSectorType


import mz.org.fgh.sifmoz.backend.base.BaseEntity

class ClinicSectorType extends BaseEntity {

    String id
    String code
    String description

    static mapping = {
        id generator: "assigned"
    }

    def beforeInsert() {
        if (!id) {
            id = UUID.randomUUID()
        }
    }

    static constraints = {
        code nullable: false, unique: true
        description nullable: false
    }
}
