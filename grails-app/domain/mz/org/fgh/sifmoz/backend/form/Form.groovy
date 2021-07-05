package mz.org.fgh.sifmoz.backend.form

import grails.rest.Resource

@Resource(uri='/api/form')
class Form {

    String code
    String description

    static constraints = {
    }
}
