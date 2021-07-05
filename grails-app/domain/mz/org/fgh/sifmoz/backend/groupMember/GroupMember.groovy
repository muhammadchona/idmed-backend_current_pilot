package mz.org.fgh.sifmoz.backend.groupMember

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.group.Group
import mz.org.fgh.sifmoz.backend.patient.Patient

@Resource(uri='/api/groupMember')
class GroupMember {

    Patient patient
    Group group
    Date joinDate

    static constraints = {
    }
}
