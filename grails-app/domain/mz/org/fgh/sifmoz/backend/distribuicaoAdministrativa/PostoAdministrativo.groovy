package mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa

import mz.org.fgh.sifmoz.backend.base.BaseEntity

class PostoAdministrativo extends BaseEntity {
    String id
    String code
    String description
    static belongsTo = [district: District]
    static hasMany = [localidades: Localidade]

    static mapping = {
        id generator: "uuid"
    }
    static constraints = {
        code nullable: false, unique: true
        description nullable: false
    }
}
