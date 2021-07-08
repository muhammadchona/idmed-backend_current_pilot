package mz.org.fgh.sifmoz.backend.episode

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinicSector.ClinicSector
import mz.org.fgh.sifmoz.backend.episodeType.EpisodeType
import mz.org.fgh.sifmoz.backend.patient.Patient
import mz.org.fgh.sifmoz.backend.patientIdentifier.PatientProgramIdentifier
import mz.org.fgh.sifmoz.backend.program.Program
import mz.org.fgh.sifmoz.backend.startStopReason.StartStopReason

@Resource(uri = '/api/episode')
class Episode {

    Date startDate
    Date stopDate
    StartStopReason startStopReason
    String notes
    EpisodeType episodeType
    ClinicSector clinicSector

    static belongsTo = [patientProgramIdentifier: PatientProgramIdentifier]

    static constraints = {
        startDate(nullable: false, blank: false, validator: { startDate, urc ->
            return startDate <= new Date()
        })
        stopDate(nullable: true, blank: true, validator: { stopDate, urc ->
            return stopDate != null ? startDate < stopDate : null
        })

    }
}
