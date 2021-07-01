package mz.org.fgh.sifmoz.backend.patient.vitalsigns

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.appointment.Visit

@Resource(uri='/api/adherenceScreening')
class AdherenceScreening {

    boolean hasPatientCameCorrectDate
    int daysWithoutMedicine
    boolean patientForgotMedicine
    int lateDays
    String lateMotives
    Visit visit

    static mapping = {
        version false
    }

    static constraints = {
        daysWithoutMedicine(min: 0, validator: {daysWithoutMedicine, obj -> return (obj.hasPatientCameCorrectDate == false && daysWithoutMedicine == 0)})
        lateDays(min: 0)
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
