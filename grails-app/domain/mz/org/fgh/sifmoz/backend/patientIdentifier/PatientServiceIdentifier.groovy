package mz.org.fgh.sifmoz.backend.patientIdentifier

import com.fasterxml.jackson.annotation.JsonManagedReference
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.episode.Episode
import mz.org.fgh.sifmoz.backend.identifierType.IdentifierType
import mz.org.fgh.sifmoz.backend.patient.Patient
import mz.org.fgh.sifmoz.backend.service.ClinicalService

class PatientServiceIdentifier {
    String id
    Date startDate
    Date endDate
    Date reopenDate
    String value
    String state
    boolean prefered
    IdentifierType identifierType
    ClinicalService service
    Clinic clinic

    @JsonManagedReference
    static belongsTo = [patient: Patient]

    @JsonManagedReference
    static hasMany = [episodes: Episode]

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        startDate(nullable: true, blank: true, validator: { startDate, urc ->
            return startDate != null ? startDate <= new Date() : null
        })
        endDate nullable: true
        reopenDate nullable: true
    }
}
