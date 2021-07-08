package mz.org.fgh.sifmoz.backend.patientAttribute

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.attributeType.AttributeType
import mz.org.fgh.sifmoz.backend.patient.Patient

@Resource(uri='/api/patientAttribute')
class PatientAttribute {

    AttributeType attributeType
    String value

    static belongsTo = [patient: Patient]

    static constraints = {
        attributeType nullable: false
        value nullable: false, blank: false
    }
}
