package mz.org.fgh.sifmoz.backend.nationalClinic

import grails.rest.Resource

@Resource(uri='/api/nationalClinics')
class NationalClinic {

    String code
    String province
    String district
    String subdistrict
    String facilityName
    String facilityType

    static constraints = {
    }
}
