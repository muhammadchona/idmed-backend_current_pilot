package mz.org.fgh.sifmoz.backend.prescriptionDrug

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.prescription.Prescription

// @Resource(uri = '/api/prescribedDrug')
class PrescribedDrug {

    double amtPerTime
    int timesPerDay
    boolean modified
    Prescription prescription
    Drug drug
   // static belongsTo = [prescription: Prescription,drug: Drug]

    static constraints = {
        amtPerTime(min: 1)
        timesPerDay(min: 1)
    }
}
