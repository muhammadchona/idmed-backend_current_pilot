package mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa

import grails.rest.Resource

class Province {
    String id
    String code
    String description

    static hasMany = [districts: District]

    static mapping = {
        id generator: "uuid"
    }
    static constraints = {
        code nullable: false, unique: true
        description nullable: false
    }
}
