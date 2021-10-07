package mz.org.fgh.sifmoz.backend.screening

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.patientVisit.PatientVisit

// @Resource(uri='/api/ramScreening')
class RAMScreening {

    String adverseReaction
    boolean adverseReactionMedicine
    boolean referedToUSRam
    PatientVisit visit

    static mapping = {
        version false
    }

    static constraints = {
    }

    @Override
    public String toString() {
        return "RAMScreening{" +
                "adverseReaction='" + adverseReaction + '\'' +
                ", adverseReactionMedicine=" + adverseReactionMedicine +
                ", referedToUSRam=" + referedToUSRam +
                '}';
    }
}
