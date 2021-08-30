package mz.org.fgh.sifmoz.backend.groupMember

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.group.Group
import mz.org.fgh.sifmoz.backend.patient.Patient

class GroupMember {
    String id
    Date startDate
    Date endDate
    Group group
    Clinic clinic

    static belongsTo = [patient: Patient]
    static mapping = {
        id generator: "uuid"
    }

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
