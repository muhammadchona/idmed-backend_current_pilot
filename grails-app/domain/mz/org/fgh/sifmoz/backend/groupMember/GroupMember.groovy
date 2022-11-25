package mz.org.fgh.sifmoz.backend.groupMember

import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.group.GroupInfo
import mz.org.fgh.sifmoz.backend.patient.Patient

class GroupMember extends BaseEntity {
    String id
    Date startDate
    Date endDate
    GroupInfo group
    Clinic clinic

    static belongsTo = [patient: Patient]
    static mapping = {
        id generator: "assigned"
    }

    static constraints = {
        patient nullable: false, unique: ['group','endDate']
        startDate(nullable: false, blank: false, validator: { startDate, urc ->
            return startDate != null ? startDate <= new Date() : null
        })
        endDate nullable: true
    }
}
