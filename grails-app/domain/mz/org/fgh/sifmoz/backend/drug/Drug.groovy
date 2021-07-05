package mz.org.fgh.sifmoz.backend.drug

import grails.rest.Resource

@Resource(uri='/api/drug')
class Drug {

    boolean clinicalStage
    int packSize
    boolean sideTreatment
    String name

    static constraints = {
    }
}
