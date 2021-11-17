package mz.org.fgh.sifmoz.backend.prescriptionDetail

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import mz.org.fgh.sifmoz.backend.dispenseType.DispenseType
import mz.org.fgh.sifmoz.backend.prescription.Prescription
import mz.org.fgh.sifmoz.backend.therapeuticLine.TherapeuticLine
import mz.org.fgh.sifmoz.backend.therapeuticRegimen.TherapeuticRegimen

class PrescriptionDetail {
    String id
    String reasonForUpdate
    @JsonManagedReference
    TherapeuticLine therapeuticLine
    @JsonManagedReference
    TherapeuticRegimen therapeuticRegimen
    @JsonManagedReference
    DispenseType dispenseType
    @JsonBackReference
    Prescription prescription
    static belongsTo = [Prescription]

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        reasonForUpdate nullable: true
    }
}
