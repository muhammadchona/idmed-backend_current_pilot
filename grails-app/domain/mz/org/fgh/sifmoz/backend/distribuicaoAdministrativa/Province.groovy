package mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonManagedReference
import mz.org.fgh.sifmoz.backend.base.BaseEntity

@JsonInclude(JsonInclude.Include.NON_NULL)
class Province extends BaseEntity {
    String id
    String code
    String description

    @JsonManagedReference
    static hasMany = [districts: District]
    static mapping = {
        id generator: "assigned"
    }

    def beforeInsert() {
        if (!this.id) {
            this.id = UUID.randomUUID()
        }
    }
    static constraints = {
        code nullable: false, unique: true
        description nullable: false
    }
}
