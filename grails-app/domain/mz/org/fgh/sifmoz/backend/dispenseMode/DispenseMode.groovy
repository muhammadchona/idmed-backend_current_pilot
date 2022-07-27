package mz.org.fgh.sifmoz.backend.dispenseMode

import mz.org.fgh.sifmoz.backend.base.BaseEntity

class DispenseMode extends BaseEntity {
    String id
    String code
    String description
    String openmrsUuid

    static mapping = {
        id generator: "assigned"
    }

    def beforeInsert() {
        if (!id) {
            id = UUID.randomUUID()
        }
    }

    static constraints = {
    }
}
