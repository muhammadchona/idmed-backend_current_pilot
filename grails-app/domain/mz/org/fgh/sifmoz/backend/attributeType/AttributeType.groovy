package mz.org.fgh.sifmoz.backend.attributeType

import grails.rest.Resource

@Resource(uri='/api/attributeType')
class AttributeType {

    String code
    String name
    String description
    String datatype

    static constraints = {
        code nullable: false, unique: true
        name nullable: false, unique: true
        description nullable: true, blank: true
        datatype nullable: true, blank: true
    }
}
