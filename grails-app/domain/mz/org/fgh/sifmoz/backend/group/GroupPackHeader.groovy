package mz.org.fgh.sifmoz.backend.group

import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.duration.Duration

class GroupPackHeader extends BaseEntity {

    String id
    Date packDate
    Duration duration
    Date nextPickUpDate
    static belongsTo = [group: GroupInfo]

    static hasMany = [groupPacks: GroupPack]

    static mapping = {
        id generator: "assigned"
    }

    static constraints = {
    }


    @Override
    String toString() {
        return "GroupPackHeader{" +
                "group=" + group +
                ", groupPacks=" + groupPacks +
                ", id='" + id + '\'' +
                ", packDate=" + packDate +
                ", duration=" + duration +
                ", nextPickUpDate=" + nextPickUpDate +
                '}'
    }
}
