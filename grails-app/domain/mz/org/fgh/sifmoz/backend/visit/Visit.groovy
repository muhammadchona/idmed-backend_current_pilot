package mz.org.fgh.sifmoz.backend.visit

import grails.rest.Resource

@Resource(uri='/api/visit')
class Visit {

    Date visitDate

    static constraints = {
    }
}
