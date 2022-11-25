package mz.org.fgh.sifmoz.backend.screening

import com.fasterxml.jackson.annotation.JsonBackReference
import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.patientVisit.PatientVisit

class AdherenceScreening extends BaseEntity {
    String id
    boolean hasPatientCameCorrectDate
    int daysWithoutMedicine
    boolean patientForgotMedicine
    int lateDays
    String lateMotives

    @JsonBackReference
    PatientVisit visit

    static belongsTo = [PatientVisit]
    static mapping = {
        id generator: "assigned"
    }

    static constraints = {
        lateMotives(nullable: true, maxSize: 50)
        daysWithoutMedicine(nullable: true,blank: true)
        lateDays(nullable: true, blank: true)
    }

    @Override
    String toString() {
        return "AdherenceScreening{" +
                "hasPatientCameCorrectDate=" + hasPatientCameCorrectDate +
                ", daysWithoutMedicine=" + daysWithoutMedicine +
                ", patientForgotMedicine=" + patientForgotMedicine +
                ", lateDays=" + lateDays +
                ", lateMotives='" + lateMotives + '\'' +
                '}'
    }
}
