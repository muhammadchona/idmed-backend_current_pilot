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

    static mapping = {
        version false
    }

    static constraints = {
        uuid nullable: true
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