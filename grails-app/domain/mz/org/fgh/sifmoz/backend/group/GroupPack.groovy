package mz.org.fgh.sifmoz.backend.group

import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.packaging.Pack

class GroupPack extends BaseEntity {
    String id
    Pack pack
    static hasOne = [Pack]
    static belongsTo = [header: GroupPackHeader]

    static mapping = {
        id generator: "assigned"
    }
    static constraints = {
    }

    @Override
     String toString() {
        return "GroupPack{" +
                "id='" + id + '\'' +
                ", pack=" + pack +
                '}'
    }
}
