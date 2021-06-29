package mz.org.fgh.sifmoz.backend.group

import grails.rest.Resource


@Resource(uri='/api/groupType')
class GroupType {

    String code
    String description


    static mapping = {
        version false
    }

    static constraints = {
        code nullable: false, unique: true
        description nullable: false
    }

    @Override
    String toString() {
        description
    }
}
