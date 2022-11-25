package mz.org.fgh.sifmoz.backend.screening

import com.fasterxml.jackson.annotation.JsonBackReference
import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.patientVisit.PatientVisit

class VitalSignsScreening extends BaseEntity {
    String id
    int distort
    String imc
    double weight
    int systole
    double height

    @JsonBackReference
    PatientVisit visit

    static belongsTo = [PatientVisit]
    static mapping = {
        id generator: "assigned"
    }

    static constraints = {
        distort (nullable: false)
    }

    @Override
    String toString() {
        return "VitalSignsScreening{" +
                "distort=" + distort +
                ", imc='" + imc + '\'' +
                ", weight=" + weight +
                ", systole=" + systole +
                ", height=" + height +
                '}'
    }
}
