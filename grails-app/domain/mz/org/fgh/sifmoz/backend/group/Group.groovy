package mz.org.fgh.sifmoz.backend.group

import grails.rest.Resource


@Resource(uri='/api/group')
class Group {


    String code
    String name


    static mapping = {
        version false
    }

    static constraints = {
        code nullable: false, unique: true
        name nullable: false
    }

    @Override
    String toString() {
        name
    }
}
