package mz.org.fgh.sifmoz.backend.duration

import mz.org.fgh.sifmoz.backend.base.BaseEntity

class Duration extends BaseEntity {

    String id
    int weeks
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
        weeks unique: true
    }
}
