package mz.org.fgh.sifmoz.backend.group


import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.groupMember.GroupMember
import mz.org.fgh.sifmoz.backend.groupType.GroupType
import mz.org.fgh.sifmoz.backend.service.ClinicalService

class GroupInfo extends BaseEntity {
    String id
    String code
    String name
    Date startDate
    Date endDate
    Clinic clinic
    GroupType groupType
    ClinicalService service

    static hasMany = [members: GroupMember, packHeaders: GroupPackHeader]
    static mapping = {
        id generator: "assigned"
        table 'group_info'
    }

    static constraints = {
        code nullable: false, unique: true
        name nullable: false
        startDate(nullable: false, blank: false, validator: { startDate, urc ->
            return startDate != null ? startDate <= new Date() : null
        })
        endDate(nullable: true, blank: true)
        packHeaders nullable: true
    }


    @Override
    String toString() {
        return "GroupInfo{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", clinic=" + clinic +
                ", groupType=" + groupType +
                ", service=" + service +
                '}'
    }
}
