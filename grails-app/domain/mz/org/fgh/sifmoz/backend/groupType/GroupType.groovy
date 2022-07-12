package mz.org.fgh.sifmoz.backend.groupType

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.base.BaseEntity

class GroupType extends BaseEntity {
    String id
    String code
    String description

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        code nullable: false, unique: true
        description nullable: false
    }


    @Override
    public String toString() {
        return "GroupType{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
