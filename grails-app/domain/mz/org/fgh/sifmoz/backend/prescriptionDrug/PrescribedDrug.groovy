package mz.org.fgh.sifmoz.backend.prescriptionDrug

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.prescription.Prescription

class PrescribedDrug {
    String id
    int amtPerTime
    int timesPerDay
    int qtyPrescribed
    String form
    boolean modified
    Drug drug
    static belongsTo = [prescription: Prescription]

    static mapping = {
        id generator: "uuid"
    }
    static constraints = {
        timesPerDay(min: 1)
        amtPerTime min: 1
    }
}
