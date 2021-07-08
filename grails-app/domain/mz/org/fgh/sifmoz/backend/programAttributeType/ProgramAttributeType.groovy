package mz.org.fgh.sifmoz.backend.programAttributeType

import grails.rest.Resource

@Resource(uri='/api/programAttributeType')
class ProgramAttributeType {

    String code
    String description

    static constraints = {
        code nullable: false, unique: true
        description nullable: false
    }
}
