package mz.org.fgh.sifmoz.backend.patientAttribute


import mz.org.fgh.sifmoz.backend.attributeType.PatientAttributeType
import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.patient.Patient

class PatientAttribute extends BaseEntity {
    String id
    PatientAttributeType attributeType
    String value

    static belongsTo = [patient: Patient]

    static mapping = {
        id generator: "uuid"
    }
    static constraints = {
        attributeType nullable: false
        value nullable: false, blank: false
    }
}
