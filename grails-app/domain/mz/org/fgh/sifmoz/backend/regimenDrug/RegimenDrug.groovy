package mz.org.fgh.sifmoz.backend.regimenDrug

import grails.rest.Resource

@Resource(uri='/api/regimenDrug')
class RegimenDrug {

    double amPerTime
    int timesPerDay
    boolean modified
    String notes

    static constraints = {
    }
}
