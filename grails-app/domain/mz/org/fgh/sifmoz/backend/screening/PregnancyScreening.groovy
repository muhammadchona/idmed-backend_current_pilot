package mz.org.fgh.sifmoz.backend.screening

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.patientVisit.PatientVisit

@Resource(uri='/api/pregnancyScreening')
class PregnancyScreening {

    boolean pregnant;
    boolean menstruationLastTwoMonths
    Date childDeliveryPrevision
    PatientVisit visit

    static mapping = {
        version false
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
