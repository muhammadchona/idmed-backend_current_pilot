package mz.org.fgh.sifmoz.backend.screening

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.patientVisit.PatientVisit

class PregnancyScreening {
    String id
    boolean pregnant
    boolean menstruationLastTwoMonths
    Date lastMenstruation
    static belongsTo = [visit: PatientVisit]

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        lastMenstruation(nullable: true, blank: true)
    }

    @Override
    public String toString() {
        return "PregnancyScreening{" +
                "pregnant=" + pregnant +
                ", menstruationLastTwoMonths=" + menstruationLastTwoMonths +
                ", childDeliveryPrevision=" + lastMenstruation +
                '}';
    }
}
