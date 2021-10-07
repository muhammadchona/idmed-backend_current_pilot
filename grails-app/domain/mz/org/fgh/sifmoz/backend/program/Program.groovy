package mz.org.fgh.sifmoz.backend.program

import grails.rest.Resource

// @Resource(uri='/api/program')
class Program {

    String code
    String description

    static constraints = {
        code nullable: false, unique: true
        description nullable: false
    }
}
