package mz.org.fgh.sifmoz.backend.patient.vitalsigns

import grails.rest.Resource

@Resource(uri='/api/ramScreening')
class RAMScreening {

    String adverseReaction
    boolean adverseReactionMedicine
    boolean referedToUSRam

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
