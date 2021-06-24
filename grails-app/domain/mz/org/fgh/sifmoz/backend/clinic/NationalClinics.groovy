package mz.org.fgh.sifmoz.backend.clinic

import grails.rest.Resource

@Resource(uri='/api/nationalClinics')
class NationalClinics {


    String code
    String province
    String district
    String subdistrict
    String facilityName
    String facilityType


    static mapping = {
        version false
    }

    static constraints = {
        code nullable: false, unique: true

    }

    @Override
    String toString() {
    }
}
