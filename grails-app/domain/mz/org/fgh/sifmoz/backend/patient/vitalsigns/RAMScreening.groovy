package mz.org.fgh.sifmoz.backend.patient.vitalsigns

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.appointment.Visit

@Resource(uri='/api/ramScreening')
class RAMScreening {

    String adverseReaction
    boolean adverseReactionMedicine
    boolean referedToUSRam
    Visit visit

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
