package mz.org.fgh.sifmoz.backend.prescriptionDrug

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.prescription.Prescription

class PrescribedDrug {
    String id
    double amtPerTime
    int timesPerDay
    boolean modified
    Drug drug
    Clinic clinic
    static belongsTo = [prescription: Prescription]

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        amtPerTime(min: 1)
        timesPerDay(min: 1)
    }
}
