package mz.org.fgh.sifmoz.backend.identifierType

import grails.rest.Resource

@Resource(uri='/api/identifierType')
class IdentifierType {

    String code
    String description

    static constraints = {
    }
}
