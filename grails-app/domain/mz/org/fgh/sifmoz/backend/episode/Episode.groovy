package mz.org.fgh.sifmoz.backend.episode

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinicSector.ClinicSector
import mz.org.fgh.sifmoz.backend.episodeType.EpisodeType
import mz.org.fgh.sifmoz.backend.patient.Patient
import mz.org.fgh.sifmoz.backend.program.Program

@Resource(uri = '/api/episode')
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

    static constraints = {
    }
}
