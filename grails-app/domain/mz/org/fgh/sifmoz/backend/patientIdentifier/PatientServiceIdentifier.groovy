package mz.org.fgh.sifmoz.backend.patientIdentifier


import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.episode.Episode
import mz.org.fgh.sifmoz.backend.identifierType.IdentifierType
import mz.org.fgh.sifmoz.backend.patient.Patient
import mz.org.fgh.sifmoz.backend.service.Service

class PatientServiceIdentifier {
    String id
    Date startDate
    Date endDate
    Date reopenDate
    String value
    boolean prefered
    IdentifierType identifierType
    Service service
    Clinic clinic

    static belongsTo = [patient: Patient]
    static hasMany = [episodes: Episode]

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        startDate(nullable: true, blank: true, validator: { startDate, urc ->
            return startDate != null ? startDate <= new Date() : null
        })
        endDate(nullable: true, blank: true, validator: { endDate, urc ->
            return endDate != null ? startDate < endDate : null
        })
        reopenDate(nullable: true, blank: true, validator: { reopenDate, urc ->
            return reopenDate != null ? endDate <= reopenDate : null
        })
        value nullable: false, unique: ['service', 'identifierType']
    }
}
