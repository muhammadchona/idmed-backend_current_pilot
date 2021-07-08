package mz.org.fgh.sifmoz.backend.nationalClinic

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.Province
import mz.org.fgh.sifmoz.backend.facilityType.FacilityType

@Resource(uri='/api/nationalClinics')
class NationalClinic {

    String code
    String facilityName
    String telephone
    FacilityType facilityType

    static belongsTo = [province: Province]
    static hasMany = [clinics: Clinic]

    static constraints = {
        code nullable: false, unique: true
        facilityName nullable: false, blank: false, unique: ['province']
        telephone nullable: true, matches: /\d+/, maxSize: 12, minSize: 9
    }
}
