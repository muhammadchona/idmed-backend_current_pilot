package mz.org.fgh.sifmoz.backend.patient

import grails.rest.Resource

@Resource(uri='/api/patient')
class Patient {

    String name
    String surname
    String gender
    Date birthDate
    String telephone
    String cellphone
    String address
    String uuid = UUID.randomUUID().toString()

    static constraints = {
    }
}
