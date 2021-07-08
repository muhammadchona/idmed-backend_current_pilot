package mz.org.fgh.sifmoz.backend.group

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.groupMember.GroupMember

@Resource(uri='/api/group')
class Group {

    String code
    String name
    Date startDate
    Date endDate

    static hasMany = [members: GroupMember]

    static constraints = {
        code nullable: false, unique: true
        name nullable: false
        startDate(nullable: true, blank: true, validator: { startDate, urc ->
            return startDate != null ? startDate <= new Date() : null
        })
        endDate(nullable: true, blank: true, validator: { endDate, urc ->
            return endDate != null ? startDate < endDate : null
        })
    }
}
