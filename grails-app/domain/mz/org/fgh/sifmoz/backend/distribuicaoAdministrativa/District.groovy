package mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa

import com.fasterxml.jackson.annotation.JsonBackReference
import mz.org.fgh.sifmoz.backend.base.BaseEntity

class District extends BaseEntity {
    String id
    String code
    String description

    @JsonBackReference
    Province province

    static belongsTo = [Province]

    static mapping = {
        id generator: "assigned"
    }

    def beforeInsert() {
        if (!id) {
            id = UUID.randomUUID()
        }
    }

    static constraints = {
        code nullable: false, unique: ['province']
        description nullable: false
    }
}
