package mz.org.fgh.sifmoz.backend.patient

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.appointment.Appointment
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.Province
import mz.org.fgh.sifmoz.backend.groupMember.GroupMember
import mz.org.fgh.sifmoz.backend.patientAttribute.PatientAttribute
import mz.org.fgh.sifmoz.backend.patientIdentifier.PatientProgramIdentifier
import mz.org.fgh.sifmoz.backend.patientProgram.PatientProgram

@Resource(uri='/api/patient')
class Patient {

    String firstnames
    String lastname
    String gender
    Date dateofbirth
    String cellphone
    String alternativeCellphone
    String address
    String otherAddress
    boolean accountstatus
    String uuid = UUID.randomUUID().toString()

    static belongsTo = [province: Province]
    static hasMany = [
            attributes: PatientAttribute,
            identifiers: PatientProgram,
            appointments: Appointment,
            groups: GroupMember
    ]

    static constraints = {
        dateofbirth(nullable: true, blank: true, validator: { dateofbirth, urc ->
            return dateofbirth != null ? dateofbirth <= new Date() : null
        })
        cellphone(nullable: true, matches: /\d+/, maxSize: 12, minSize: 9)
        alternativeCellphone(nullable: true, matches: /\d+/, maxSize: 12, minSize: 9)
    }
}
