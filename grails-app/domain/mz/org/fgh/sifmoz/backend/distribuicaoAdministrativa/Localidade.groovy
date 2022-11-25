package mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa

import mz.org.fgh.sifmoz.backend.base.BaseEntity

class Localidade extends BaseEntity {
    String id
    String code
    String description

    static belongsTo = [postoAdministrativo: PostoAdministrativo]

    static mapping = {
        id generator: "assigned"
    }
    static constraints = {
        code nullable: false, unique: true
        description nullable: false
    }
}
