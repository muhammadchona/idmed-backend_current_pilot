package mz.org.fgh.sifmoz.backend.doctor

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.prescription.Prescription
import mz.org.fgh.sifmoz.backend.stock.Stock

class Doctor {
    String id
    String firstnames
    String lastname
    String gender
    Date dateofbirth
    String telephone
    String email
    Clinic clinic
    // int category
    boolean active
    static hasMany = [prescriptions: Prescription]

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        firstnames nullable: false
        lastname nullable: false
        gender nullable: false
        dateofbirth(nullable: true, blank: true, validator: { dateofbirth, urc ->
            return dateofbirth != null ? dateofbirth <= new Date() : null
        })
        telephone(nullable: true, matches: /\d+/, maxSize: 12, minSize: 9)
        email nullable: true

    }
}
