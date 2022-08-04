package mz.org.fgh.sifmoz.backend.healthInformationSystem

class SystemConfigs {

    String id
    String key
    String value
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
        key nullable: false, unique: true
        value nullable: false
    }
}
