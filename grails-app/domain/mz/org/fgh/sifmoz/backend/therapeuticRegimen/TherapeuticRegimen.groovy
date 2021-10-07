package mz.org.fgh.sifmoz.backend.therapeuticRegimen

class TherapeuticRegimen {
    String id
    String regimenScheme
    boolean active
    String code
    boolean pedhiatric
    String description

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        code nullable: false, unique: true
        regimenScheme nullable: false
        description nullable: true
    }
}
