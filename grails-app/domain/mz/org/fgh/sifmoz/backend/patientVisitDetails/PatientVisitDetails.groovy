package mz.org.fgh.sifmoz.backend.patientVisitDetails


import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.episode.Episode
import mz.org.fgh.sifmoz.backend.packaging.Pack
import mz.org.fgh.sifmoz.backend.patientVisit.PatientVisit
import mz.org.fgh.sifmoz.backend.prescription.Prescription

class PatientVisitDetails extends BaseEntity {

    String id
    Episode episode
    Clinic clinic
   Prescription prescription
    Pack pack
    PatientVisit patientVisit

    static belongsTo = [patientVisit:PatientVisit]

    static mapping = {
        id generator: "assigned"
    }
    static constraints = {
        pack nullable: false
    }
}
