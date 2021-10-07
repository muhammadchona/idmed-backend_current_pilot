package mz.org.fgh.sifmoz.backend.episode

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinicSector.ClinicSector
import mz.org.fgh.sifmoz.backend.episodeType.EpisodeType
import mz.org.fgh.sifmoz.backend.patient.Patient
import mz.org.fgh.sifmoz.backend.patientIdentifier.PatientProgramIdentifier
import mz.org.fgh.sifmoz.backend.patientProgram.PatientProgram
import mz.org.fgh.sifmoz.backend.prescription.Prescription
import mz.org.fgh.sifmoz.backend.program.Program
import mz.org.fgh.sifmoz.backend.startStopReason.StartStopReason

// @Resource(uri = '/api/episode')
class Episode {

    Date startDate
    Date stopDate
    StartStopReason startReason
    StartStopReason stopReason
    String startNotes
    String stopNotes
    EpisodeType episodeType
    ClinicSector clinicSector

    static belongsTo = [patientProgram: PatientProgram]

    static hasMany = [prescriptions: Prescription]

    static constraints = {

        startReason nullable: false
        startNotes nullable: false

        stopReason nullable: true
        stopNotes nullable: true

        startDate(nullable: false, blank: false, validator: { startDate, urc ->
            return startDate <= new Date()
        })
        stopDate(nullable: true, blank: true, validator: { stopDate, urc ->
            return stopDate != null ? startDate < stopDate : null
        })

    }
}
