package mz.org.fgh.sifmoz.backend.clinic

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinicSector.ClinicSector
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.District
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.Province
import mz.org.fgh.sifmoz.backend.nationalClinic.NationalClinic

class Clinic {
    String id
    String code
    String notes
    String telephone
    String clinicName
    Province province
    District district
    boolean mainClinic
    String uuid = UUID.randomUUID().toString()

    static belongsTo = [nationalClinic: NationalClinic]
    static hasMany = [sectors: ClinicSector]

    static mapping = {
        id generator: "uuid"
    }
    static constraints = {
        code nullable: true
        notes nullable: true, blank: true
        district nullable: true
        telephone nullable: true, matches: /\d+/, maxSize: 12, minSize: 9
        clinicName nullable: false, unique: ['province']
    }
}
