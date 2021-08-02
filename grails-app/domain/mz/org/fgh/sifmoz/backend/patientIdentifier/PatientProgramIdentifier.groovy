package mz.org.fgh.sifmoz.backend.patientIdentifier

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.episode.Episode
import mz.org.fgh.sifmoz.backend.identifierType.IdentifierType
import mz.org.fgh.sifmoz.backend.patient.Patient
import mz.org.fgh.sifmoz.backend.patientProgram.PatientProgram
import mz.org.fgh.sifmoz.backend.program.Program

@Resource(uri='/api/patientProgramIdentifier')
class PatientProgramIdentifier {

    Date startDate
    Date endDate
    String value
    IdentifierType identifierType

    static belongsTo = [patientProgram: PatientProgram]

    static constraints = {
        startDate(nullable: true, blank: true, validator: { startDate, urc ->
            return startDate != null ? startDate <= new Date() : null
        })
        endDate(nullable: true, blank: true, validator: { endDate, urc ->
            return endDate != null ? startDate < endDate : null
        })
        value nullable: false, unique: ['program','identifierType']
    }
}
