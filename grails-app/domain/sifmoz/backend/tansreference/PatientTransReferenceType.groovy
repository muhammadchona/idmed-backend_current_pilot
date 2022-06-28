package sifmoz.backend.tansreference

class PatientTransReferenceType {

    String id
    String description
    String code

    static mapping = {
        id generator: "uuid"
    }
    static constraints = {
        description(nullable: false, maxSize: 50, blank: false)
        code(nullable: false, maxSize: 50, blank: false, unique: true)
    }

    @Override
    public String toString() {
        return "PatientTransReferenceType{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
