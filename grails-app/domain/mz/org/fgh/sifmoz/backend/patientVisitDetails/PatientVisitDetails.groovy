package mz.org.fgh.sifmoz.backend.patientVisitDetails

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.episode.Episode
import mz.org.fgh.sifmoz.backend.packaging.Pack
import mz.org.fgh.sifmoz.backend.patientVisit.PatientVisit
import mz.org.fgh.sifmoz.backend.prescription.Prescription

class PatientVisitDetails {

    String id
    @JsonManagedReference
    Episode episode
    @JsonIgnore
    Clinic clinic

    @JsonManagedReference
    static hasMany = [prescriptions: Prescription, packs: Pack]

    @JsonBackReference
    PatientVisit patientVisit

    static belongsTo = [PatientVisit]

    static mapping = {
        id generator: "uuid"
    }
    static constraints = {
    }
}
