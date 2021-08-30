package mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa

import grails.rest.Resource

class District {
    String id
    String code
    String description

    static belongsTo = [province: Province]

    static mapping = {
        id generator: "uuid"
    }
    static constraints = {
        code nullable: false, unique: ['province']
        description nullable: false
    }
}
