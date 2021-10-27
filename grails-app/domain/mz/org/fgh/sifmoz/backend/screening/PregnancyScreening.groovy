package mz.org.fgh.sifmoz.backend.screening

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.patientVisit.PatientVisit

class PregnancyScreening {
    String id
    boolean pregnant;
    boolean menstruationLastTwoMonths
    Date childDeliveryPrevision
    static belongsTo = [visit: PatientVisit]

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
    }

    @Override
    public String toString() {
        return "PregnancyScreening{" +
                "pregnant=" + pregnant +
                ", menstruationLastTwoMonths=" + menstruationLastTwoMonths +
                ", childDeliveryPrevision=" + childDeliveryPrevision +
                '}';
    }
}
