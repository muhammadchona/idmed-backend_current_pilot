package mz.org.fgh.sifmoz.backend.prescription

class Prescription {


    boolean clinicalStage
    int duration
    Date prescriptionDate
    Date expiryDate
    boolean current
    String notes
    String prescriptionSeq
    String uuid = UUID.randomUUID().toString()



    static mapping = {
        version false
    }

    static constraints = {
        prescriptionSeq nullable: false, unique: true
        prescriptionDate nullable: false
        duration nullable: false
        uuid nullable: false
    }

    @Override
    String toString() {

    }


}
