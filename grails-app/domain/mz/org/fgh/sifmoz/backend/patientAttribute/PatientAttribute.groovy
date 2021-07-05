package mz.org.fgh.sifmoz.backend.patientAttribute

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.attributeType.AttributeType

@Resource(uri='/api/patientAttribute')
class PatientAttribute {

    AttributeType attributeType
    String value

    static constraints = {
    }
}
