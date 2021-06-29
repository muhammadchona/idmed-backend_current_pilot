package mz.org.fgh.sifmoz.backend.prescription

import mz.org.fgh.sifmoz.backend.dispense.Dispense
import mz.org.fgh.sifmoz.backend.dispense.DispenseType
import mz.org.fgh.sifmoz.backend.therapeuticRegimen.TherapeuticRegimen
import mz.org.fgh.sifmoz.backend.therapeuticRegimen.TherapeuticalLine

class PrescriptionDetails {


    String reasonForUpdate
    Prescription prescription
    TherapeuticalLine therapeuticalLine
    TherapeuticRegimen therapeuticRegimen
    DispenseType dispenseType



    static mapping = {
        version false
    }

    static constraints = {

    }

    @Override
    String toString() {

    }

}
