package mz.org.fgh.sifmoz.backend.prescription

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.doctor.Doctor
import mz.org.fgh.sifmoz.backend.episode.Episode
import mz.org.fgh.sifmoz.backend.patientVisitDetails.PatientVisitDetails
import mz.org.fgh.sifmoz.backend.prescriptionDetail.PrescriptionDetail
import mz.org.fgh.sifmoz.backend.prescriptionDrug.PrescribedDrug
import mz.org.fgh.sifmoz.backend.stockentrance.StockEntrance

class Prescription {

    String id
    int duration
    Date prescriptionDate
    Date expiryDate
    boolean current
    String notes
    String prescriptionSeq
    String patientType
    String patientStatus
    boolean modified
    Clinic clinic
    Doctor doctor
    PrescriptionDetail prescriptionDetail
    static hasOne = [patientVisitDetails: PatientVisitDetails]
    //PatientVisitDetails patientVisitDetails

    static hasMany = [prescribedDrugs: PrescribedDrug]

    static mapping = {
        id generator: "uuid"
    }
    static constraints = {
        prescriptionDate(nullable: false, blank: false,  validator: { prescriptionDate, urc ->
            return ((prescriptionDate <= new Date()))})
        expiryDate(nullable: true, blank: true)
        notes(nullable: true, maxSize: 500)
        prescriptionSeq(nullable: true)
        duration(nullable: false, blank: false)
        patientVisitDetails nullable: true
    }
}
