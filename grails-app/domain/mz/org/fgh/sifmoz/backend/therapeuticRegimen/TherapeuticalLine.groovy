package mz.org.fgh.sifmoz.backend.therapeuticRegimen

class TherapeuticalLine {

    String code
    String description
    String uuid = UUID.randomUUID().toString()

    static mapping = {
        version false
    }

    static constraints = {
        code nullable: false, unique: true
        description nullable: false
        uuid nullable: false, unique: true
    }

    @Override
    String toString() {
        description
    }
}
