package mz.org.fgh.sifmoz.backend.prescriptionDetail

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.dispenseType.DispenseType
import mz.org.fgh.sifmoz.backend.prescription.Prescription
import mz.org.fgh.sifmoz.backend.therapeuticLine.TherapeuticLine
import mz.org.fgh.sifmoz.backend.therapeuticRegimen.TherapeuticRegimen

@Resource(uri = '/api/prescriptionDetails')
class PrescriptionDetail {

    String reasonForUpdate
    Prescription prescription
    TherapeuticLine therapeuticLine
    TherapeuticRegimen therapeuticRegimen
    DispenseType dispenseType

    static constraints = {
    }
}
