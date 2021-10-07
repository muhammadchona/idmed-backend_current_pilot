package mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa

import grails.rest.Resource

// @Resource(uri='/api/province')
class Province {

    String code
    String description

    static constraints = {
        code nullable: false, unique: true
        description nullable: false
    }
}
