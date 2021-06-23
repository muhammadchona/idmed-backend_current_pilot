package mz.org.fgh.sifmoz.backend.identifierType

import grails.rest.Resource

@Resource(uri='/api/identifierType')
class IdentifierType {

    String codigo
    String designacao

    static mapping = {
        version false
    }

    static constraints = {
        codigo nullable: false, unique: true
        designacao nullable: false
    }

    @Override
    String toString() {
        designacao
    }
}
