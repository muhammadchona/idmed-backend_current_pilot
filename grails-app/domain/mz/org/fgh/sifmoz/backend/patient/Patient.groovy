package mz.org.fgh.sifmoz.backend.patient

import grails.rest.Resource

@Resource(uri='/api/patient')
class Patient {

    String nome
    String apelido
    String sexo
    Date dataNascimento
    String telemovel1
    String telemovel2
    String morada
    String uuid = UUID.randomUUID().toString()

    static mapping = {
        version false
    }

    static constraints = {
        uuid nullable: true
        telemovel1(nullable: true, matches: /\d+/, maxSize: 12, minSize: 9)
        telemovel2(nullable: true, matches: /\d+/, maxSize: 12, minSize: 9)
        sexo(inList: ['Masculino', 'Femenino'])
        morada(nullable: true, maxSize: 500)
        nome(nullable: false, unique: ['apelido', 'sexo', 'dataNascimento'], blank: false)
        apelido(nullable: false, maxSize: 50, blank: false)
        dataNascimento(nullable: false, blank: false, validator: { datanascimento, urc ->
            return ((datanascimento <= new Date()))
        })
    }

    String toString() {
        return nome + " " + apelido
    }
}
