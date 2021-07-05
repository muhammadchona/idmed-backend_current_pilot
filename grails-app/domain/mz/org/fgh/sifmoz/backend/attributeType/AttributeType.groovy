package mz.org.fgh.sifmoz.backend.attributeType

import grails.rest.Resource

@Resource(uri='/api/attributeType')
class AttributeType {

    String code
    String description
    String dataType

    static constraints = {
    }
}
