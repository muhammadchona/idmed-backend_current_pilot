package mz.org.fgh.sifmoz.backend.patient.vitalsigns

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.appointment.Visit

@Resource(uri='/api/pregnancyScreening')
class PregnancyScreening {

    boolean pregnant;
    boolean menstruationLastTwoMonths
    Date childDeliveryPrevision
    Visit visit

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
