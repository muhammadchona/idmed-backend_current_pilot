package mz.org.fgh.sifmoz.backend.patientVisitDetails

import mz.org.fgh.sifmoz.backend.episode.Episode
import mz.org.fgh.sifmoz.backend.packaging.Pack
import mz.org.fgh.sifmoz.backend.patientVisit.PatientVisit
import mz.org.fgh.sifmoz.backend.prescription.Prescription

class PatientVisitDetails {

    String id
    Episode episode

    static hasMany = [prescriptions: Prescription, packs: Pack]
    static belongsTo = [patientVisit: PatientVisit]

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
    }
}
