package mz.org.fgh.sifmoz.backend.screening

import com.fasterxml.jackson.annotation.JsonBackReference
import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.patientVisit.PatientVisit

class PregnancyScreening extends BaseEntity {
    String id
    boolean pregnant
    boolean menstruationLastTwoMonths
    Date lastMenstruation
    @JsonBackReference
    PatientVisit visit

    static belongsTo = [PatientVisit]

    static mapping = {
        id generator: "assigned"
    }

    static constraints = {
        lastMenstruation(nullable: true, blank: true)
    }

    @Override
    String toString() {
        return "PregnancyScreening{" +
                "pregnant=" + pregnant +
                ", menstruationLastTwoMonths=" + menstruationLastTwoMonths +
                ", childDeliveryPrevision=" + lastMenstruation +
                '}'
    }
}
