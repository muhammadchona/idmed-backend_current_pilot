package mz.org.fgh.sifmoz.backend.facilityType

import grails.rest.Resource

@Resource(uri='/api/facilityType')
class FacilityType {

    String code
    String description

    static constraints = {
        code nullable: false, unique: true
        description nullable: false
    }
}
