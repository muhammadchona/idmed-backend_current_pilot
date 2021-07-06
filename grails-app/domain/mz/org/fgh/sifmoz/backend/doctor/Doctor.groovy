package mz.org.fgh.sifmoz.backend.doctor

import grails.rest.Resource

@Resource(uri='/api/doctor')
class Doctor {

    String name
    String surname
    String gender
    Date birthDate
    String telephone
    String cellphone
    String address
    int category
    boolean active

    static constraints = {
    }
}
