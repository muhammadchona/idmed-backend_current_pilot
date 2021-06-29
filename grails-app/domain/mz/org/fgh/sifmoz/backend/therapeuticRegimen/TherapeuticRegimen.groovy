package mz.org.fgh.sifmoz.backend.therapeuticRegimen



class TherapeuticRegimen {



     String regimenScheme
     boolean active
     String code
    boolean pedhiatric
    boolean adult
    boolean description


    static mapping = {
        version false
    }

    static constraints = {
        code nullable: false, unique: true
        description nullable: false
    }

    @Override
    String toString() {
        description
    }

}
