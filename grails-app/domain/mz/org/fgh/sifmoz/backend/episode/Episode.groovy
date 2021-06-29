package mz.org.fgh.sifmoz.backend.episode

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinic.ClinicSector
import mz.org.fgh.sifmoz.backend.patient.Patient
import mz.org.fgh.sifmoz.backend.program.Program


@Resource(uri='/api/episode')
class Episode {




     Date startDate
     Date stopDate
    String startReason
    String stopReason
     String startNotes
    String stopNotes
    Program program
    EpisodeType episodeType
    ClinicSector clinicSector
    Patient patient

    static mapping = {
        version false
    }

    static constraints = {
        startReason(nullable: true, maxSize: 250)
        stopReason(nullable: true, maxSize: 250)
        startNotes(nullable: true, maxSize: 250)
        stopNotes(nullable: true, maxSize: 250)
        stopDate(nullable: false, blank: false, validator: { birthDate, urc ->
            return ((birthDate <= new Date()))
        })
    }


}
