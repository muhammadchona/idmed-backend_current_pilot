package mz.org.fgh.sifmoz.backend.appointment

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.patient.Patient

// @Resource(uri='/api/appointment')
class Appointment {

    Date appointmentDate
    Date visitDate

    static belongsTo = [patient: Patient]

    static constraints = {
        appointmentDate(nullable: true, blank: true, validator: { appointmentDate, urc ->
            return appointmentDate > new Date()
        })
        visitDate(nullable: true, blank: true, validator: { visitDate, urc ->
            return visitDate <= new Date()
        })
    }
}
