package mz.org.fgh.sifmoz.backend.serviceAttributeType

class ServiceAttributeType {
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
}
