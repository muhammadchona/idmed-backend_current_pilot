package mz.org.fgh.sifmoz.backend.dispenseType

import grails.rest.Resource

// @Resource(uri='/api/dispenseType')
class DispenseType {

    String code
    String description

    static constraints = {
        code nullable: false, unique: true
        description nullable: false, blank: false

    }
}
