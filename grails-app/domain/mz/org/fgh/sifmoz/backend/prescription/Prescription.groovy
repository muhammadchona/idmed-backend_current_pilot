package mz.org.fgh.sifmoz.backend.prescription

import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.doctor.Doctor
import mz.org.fgh.sifmoz.backend.duration.Duration
import mz.org.fgh.sifmoz.backend.patientVisitDetails.PatientVisitDetails
import mz.org.fgh.sifmoz.backend.prescriptionDetail.PrescriptionDetail
import mz.org.fgh.sifmoz.backend.prescriptionDrug.PrescribedDrug
import mz.org.fgh.sifmoz.backend.utilities.Utilities

class Prescription {

    String id
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
    Duration duration
    PatientVisitDetails patientVisitDetails

    static belongsTo = [PatientVisitDetails]

    static hasMany = [prescribedDrugs: PrescribedDrug, prescriptionDetails: PrescriptionDetail]

    static mapping = {
        id generator: "uuid"
        patientVisitDetails lazy: true
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

    public void generateNextSeq() {
        long lastSeq = 0;
        try{
            if (Utilities.stringHasValue(this.prescriptionSeq)){
                lastSeq = Long.parseLong(this.prescriptionSeq);
            }else {
                lastSeq = 0;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        setPrescriptionSeq(String.valueOf(Utilities.garantirXCaracterOnNumber(lastSeq+1, 4)));
    }
}
