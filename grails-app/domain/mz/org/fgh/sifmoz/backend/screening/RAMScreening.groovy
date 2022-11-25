package mz.org.fgh.sifmoz.backend.screening

import com.fasterxml.jackson.annotation.JsonBackReference
import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.patientVisit.PatientVisit

class RAMScreening extends BaseEntity {
    String id
    String adverseReaction
    boolean adverseReactionMedicine
   // boolean referedToUSRam
    @JsonBackReference
    PatientVisit visit

    static belongsTo = [PatientVisit]

    static mapping = {
        id generator: "assigned"
    }

    static constraints = {
        adverseReaction(nullable: true, blank: true)
    }

    @Override
    String toString() {
        return "RAMScreening{" +
                "adverseReaction='" + adverseReaction + '\'' +
                ", adverseReactionMedicine=" + adverseReactionMedicine +
                ", referedToUSRam=" +
                '}'
    }
}
