package mz.org.fgh.sifmoz.backend.groupMember

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.group.Group
import mz.org.fgh.sifmoz.backend.patient.Patient

@Resource(uri='/api/groupMember')
class GroupMember {

    Date startDate
    Date endDate
    Group group

    static belongsTo = [patient: Patient]

    static constraints = {
        patient nullable: false, unique: ['group','endDate']
        startDate(nullable: true, blank: true, validator: { startDate, urc ->
            return startDate != null ? startDate <= new Date() : null
        })
        endDate(nullable: true, blank: true, validator: { endDate, urc ->
            return endDate != null ? startDate < endDate : null
        })
    }
}
