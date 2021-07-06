package mz.org.fgh.sifmoz.backend.screening

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.patientVisit.PatientVisit

@Resource(uri='/api/adherenceScreening')
class AdherenceScreening {

    boolean hasPatientCameCorrectDate
    int daysWithoutMedicine
    boolean patientForgotMedicine
    int lateDays
    String lateMotives
    PatientVisit visit

    static mapping = {
        version false
    }

    static constraints = {
        lateMotives(nullable: true, maxSize: 50)
    }

    @Override
    public String toString() {
        return "AdherenceScreening{" +
                "hasPatientCameCorrectDate=" + hasPatientCameCorrectDate +
                ", daysWithoutMedicine=" + daysWithoutMedicine +
                ", patientForgotMedicine=" + patientForgotMedicine +
                ", lateDays=" + lateDays +
                ", lateMotives='" + lateMotives + '\'' +
                '}';
    }
}
