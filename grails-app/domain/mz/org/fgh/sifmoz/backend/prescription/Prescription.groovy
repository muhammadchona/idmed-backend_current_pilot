package mz.org.fgh.sifmoz.backend.prescription

import mz.org.fgh.sifmoz.backend.Doctor.Doctor
import mz.org.fgh.sifmoz.backend.episode.Episode

class Prescription {


    boolean clinicalStage
    int duration
    Date prescriptionDate
    Date expiryDate
    boolean current
    String notes
    String prescriptionSeq
    String uuid = UUID.randomUUID().toString()
    Doctor doctor
    Episode episode



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
