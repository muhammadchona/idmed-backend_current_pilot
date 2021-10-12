package mz.org.fgh.sifmoz.backend.group

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.groupMember.GroupMember
import mz.org.fgh.sifmoz.backend.patient.Patient

class GroupInfo {
    String id
    String code
    String name
    Date startDate
    Date endDate
    Clinic clinic

    static hasMany = [members: Patient]
    static mapping = {
        id generator: "uuid"
    }

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
