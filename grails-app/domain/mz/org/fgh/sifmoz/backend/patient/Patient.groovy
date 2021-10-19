package mz.org.fgh.sifmoz.backend.patient


import mz.org.fgh.sifmoz.backend.appointment.Appointment
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.District
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.Localidade
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.PostoAdministrativo
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.Province
import mz.org.fgh.sifmoz.backend.group.GroupInfo
import mz.org.fgh.sifmoz.backend.patientAttribute.PatientAttribute
import mz.org.fgh.sifmoz.backend.patientIdentifier.PatientServiceIdentifier

class Patient {
    String id
    String firstNames
    String middleNames
    String lastNames
    String gender
    Date dateOfBirth
    String cellphone
    String alternativeCellphone
    String address
    String addressReference
    boolean accountstatus
    Province province
    Localidade bairro
    District district
    PostoAdministrativo postoAdministrativo

    static belongsTo = [clinic: Clinic]
    static hasMany = [
            attributes: PatientAttribute,
            identifiers: PatientServiceIdentifier,
            appointments: Appointment,
            groups: GroupInfo
    ]

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        dateOfBirth(nullable: true, blank: true, validator: { dateofbirth, urc ->
            return dateofbirth != null ? dateofbirth <= new Date() : null
        })
        cellphone(nullable: true, matches: /\d+/, maxSize: 12, minSize: 9)
        alternativeCellphone(nullable: true, matches: /\d+/, maxSize: 12, minSize: 9)
        address nullable: true
        addressReference nullable: true
        province nullable: false
        bairro nullable: true
        postoAdministrativo nullable: true
        attributes nullable: true
        identifiers nullable: true
        appointments nullable: true
        groups nullable: true
        clinic nullable: false
    }
}
