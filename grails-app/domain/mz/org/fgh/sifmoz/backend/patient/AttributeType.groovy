package mz.org.fgh.sifmoz.backend.patient

import grails.rest.Resource


@Resource(uri='/api/AttributeType')
class AttributeType {

    String code
    String description
    String dataType


    static mapping = {
        version false
    }

    static constraints = {
        code nullable: false, unique: true
        description nullable: false
        dataType nullable: false
    }

    @Override
    String toString() {
        description dataType
    }
}
