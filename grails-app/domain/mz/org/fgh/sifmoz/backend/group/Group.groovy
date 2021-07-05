package mz.org.fgh.sifmoz.backend.group

import grails.rest.Resource

@Resource(uri='/api/group')
class Group {

    String code
    String name
    Date createDate

    static constraints = {
    }
}
