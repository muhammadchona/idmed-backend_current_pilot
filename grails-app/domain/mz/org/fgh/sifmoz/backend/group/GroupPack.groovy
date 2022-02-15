package mz.org.fgh.sifmoz.backend.group

import mz.org.fgh.sifmoz.backend.packaging.Pack

class GroupPack {
    String id
    static hasOne = [pack: Pack]
    static belongsTo = [header: GroupPackHeader]

    static mapping = {
        id generator: "uuid"
    }
    static constraints = {
    }

    @Override
    public String toString() {
        return "GroupPack{" +
                "id='" + id + '\'' +
                ", pack=" + pack +
                '}';
    }
}
