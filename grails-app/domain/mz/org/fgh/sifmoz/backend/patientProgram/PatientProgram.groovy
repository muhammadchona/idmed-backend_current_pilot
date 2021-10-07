package mz.org.fgh.sifmoz.backend.patientProgram

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.episode.Episode
import mz.org.fgh.sifmoz.backend.patient.Patient
import mz.org.fgh.sifmoz.backend.patientIdentifier.PatientProgramIdentifier
import mz.org.fgh.sifmoz.backend.program.Program
import mz.org.fgh.sifmoz.backend.startStopReason.StartStopReason

class PatientProgram {

    Date startDate
    Date stopDate
    Date reopenDate
    StartStopReason startReason
    StartStopReason stopReason
    String startNotes
    String stopNotes
    Program program
    boolean prefered


    static belongsTo = [patient: Patient]
    static hasMany = [
            episodes: Episode,
            identifiers: PatientProgramIdentifier
    ]
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
