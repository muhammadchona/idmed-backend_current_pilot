package mz.org.fgh.sifmoz.backend.dispenseType


import mz.org.fgh.sifmoz.backend.base.BaseEntity

class DispenseType extends BaseEntity {
    public static final String DM = "DM"
    public static final String DT = "DT"
    public static final String DS = "DS"
    public static final String DA = "DA"
    String id
    String code
    String description

    static mapping = {
        id generator: "assigned"
    }

    def beforeInsert() {
        if (!id) {
            id = UUID.randomUUID()
        }
    }

    static constraints = {
        code nullable: false, unique: true
        description nullable: false, blank: false

    }

    boolean isDM () {
        return this.code.equals(DM)
    }

    boolean isDT () {
        return this.code.equals(DT)
    }

    boolean isDS () {
        return this.code.equals(DS)
    }

    boolean isDA () {
        return this.code.equals(DA)
    }
}
