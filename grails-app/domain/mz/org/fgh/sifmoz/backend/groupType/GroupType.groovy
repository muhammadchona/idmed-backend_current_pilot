package mz.org.fgh.sifmoz.backend.groupType

import grails.rest.Resource

// @Resource(uri='/api/groupType')
class GroupType {

    String code
    String description

    static constraints = {
        code nullable: false, unique: true
        description nullable: false
    }
}
