package mz.org.fgh.sifmoz.backend.patientAttribute

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.attributeType.AttributeType
import mz.org.fgh.sifmoz.backend.patient.Patient

class PatientAttribute {
    String id
    AttributeType attributeType
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
