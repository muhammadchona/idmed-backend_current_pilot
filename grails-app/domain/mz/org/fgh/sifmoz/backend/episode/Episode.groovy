package mz.org.fgh.sifmoz.backend.episode

import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.clinicSector.ClinicSector
import mz.org.fgh.sifmoz.backend.episodeType.EpisodeType
import mz.org.fgh.sifmoz.backend.patientIdentifier.PatientServiceIdentifier
import mz.org.fgh.sifmoz.backend.patientVisitDetails.PatientVisitDetails
import mz.org.fgh.sifmoz.backend.prescription.Prescription
import mz.org.fgh.sifmoz.backend.service.ClinicalService
import mz.org.fgh.sifmoz.backend.startStopReason.StartStopReason

class Episode {
    String id
    Date episodeDate
    Date creationDate
    StartStopReason startStopReason
    String notes
    EpisodeType episodeType
    ClinicSector clinicSector
    Clinic clinic

    static belongsTo = [patientServiceIdentifier: PatientServiceIdentifier]

    static hasMany = [patientVisitDetails: PatientVisitDetails]

    static mapping = {
        id generator: "uuid"
    }
    static constraints = {
        episodeDate(nullable: false, blank: false, validator: { episodeDate, urc ->
            return episodeDate <= new Date()
        })
    }
}
