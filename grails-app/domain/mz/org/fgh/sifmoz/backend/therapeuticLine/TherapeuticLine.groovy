package mz.org.fgh.sifmoz.backend.therapeuticLine

import mz.org.fgh.sifmoz.backend.base.BaseEntity

class TherapeuticLine extends BaseEntity {
    String id
    String code
    String description
    String uuid = UUID.randomUUID().toString()

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
    }
}
