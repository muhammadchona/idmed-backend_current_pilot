package mz.org.fgh.sifmoz.backend.episode


import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.clinicSector.ClinicSector
import mz.org.fgh.sifmoz.backend.episodeType.EpisodeType
import mz.org.fgh.sifmoz.backend.patientIdentifier.PatientServiceIdentifier
import mz.org.fgh.sifmoz.backend.prescription.Prescription
import mz.org.fgh.sifmoz.backend.startStopReason.StartStopReason

class Episode {
    String id
    Date startDate
    Date stopDate
    StartStopReason startStopReason
    String notes
    EpisodeType episodeType
    ClinicSector clinicSector
    Clinic clinic

    static belongsTo = [patientProgramIdentifier: PatientServiceIdentifier]

    static hasMany = [prescriptions: Prescription]

    static mapping = {
        id generator: "uuid"
    }
    static constraints = {
        startDate(nullable: false, blank: false, validator: { startDate, urc ->
            return startDate <= new Date()
        })
        stopDate(nullable: true, blank: true, validator: { stopDate, urc ->
            return stopDate != null ? startDate < stopDate : null
        })

    }
}
