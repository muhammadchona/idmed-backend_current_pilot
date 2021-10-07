package mz.org.fgh.sifmoz.backend.therapeuticLine

import grails.rest.Resource

// @Resource(uri = '/api/therapeuticalLine')
class TherapeuticLine {

    String code
    String description
    String uuid = UUID.randomUUID().toString()

    static constraints = {
    }
}
