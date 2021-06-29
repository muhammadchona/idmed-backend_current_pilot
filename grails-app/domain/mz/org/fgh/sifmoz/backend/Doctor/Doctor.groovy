package mz.org.fgh.sifmoz.backend.Doctor

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


    static mapping = {
        version false
    }

    static constraints = {

        telephone(nullable: true, matches: /\d+/, maxSize: 12, minSize: 9)
        cellphone(nullable: true, matches: /\d+/, maxSize: 12, minSize: 9)
        gender(inList: ['Masculino', 'Femenino'])
        address(nullable: true, maxSize: 500)
        name(nullable: false, unique: ['apelido', 'sexo', 'dataNascimento'], blank: false)
        surname(nullable: false, maxSize: 50, blank: false)
        birthDate(nullable: false, blank: false, validator: { birthDate, urc ->
            return ((birthDate <= new Date()))
        })
    }

    String toString() {
        return name + " " + surname
    }
}
