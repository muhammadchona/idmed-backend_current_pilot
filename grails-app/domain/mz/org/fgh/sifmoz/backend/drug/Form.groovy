package mz.org.fgh.sifmoz.backend.drug

import grails.rest.Resource


@Resource(uri='/api/form')
class Form {

    String code
    String description

    static mapping = {
        version false
    }

    static constraints = {
        code nullable: false, unique: true
        description nullable: false
    }

    @Override
    String toString() {
        description
    }
}
