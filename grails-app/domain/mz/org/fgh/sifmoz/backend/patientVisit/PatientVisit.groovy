package mz.org.fgh.sifmoz.backend.patientVisit

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.appointment.Appointment
import mz.org.fgh.sifmoz.backend.groupMember.GroupMember
import mz.org.fgh.sifmoz.backend.patientAttribute.PatientAttribute
import mz.org.fgh.sifmoz.backend.patientIdentifier.PatientProgramIdentifier
import mz.org.fgh.sifmoz.backend.patientVisitDetails.PatientVisitDetails

@Resource(uri='/api/patientVisit')
class PatientVisit {

    Date visitDate




    static constraints = {
        visitDate(nullable: false, blank: false)
    }
}
