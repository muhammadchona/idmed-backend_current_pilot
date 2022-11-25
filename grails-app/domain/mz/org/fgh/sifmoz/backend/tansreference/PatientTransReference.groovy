package mz.org.fgh.sifmoz.backend.tansreference

import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.patient.Patient
import mz.org.fgh.sifmoz.backend.patientIdentifier.PatientServiceIdentifier

class PatientTransReference extends BaseEntity{

    String id
    Clinic origin
    String destination
    PatientServiceIdentifier identifier
    String syncStatus
    PatientTransReferenceType operationType
    Date operationDate
    Date creationDate
    Long matchId

    static belongsTo = [patient: Patient]
    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        matchId nullable: true
    }

    def beforeInsert() {
        if (matchId == null) {
            matchId = findAll( [sort: ['matchId': 'desc']]).get(0).matchId++
        }
    }
}
