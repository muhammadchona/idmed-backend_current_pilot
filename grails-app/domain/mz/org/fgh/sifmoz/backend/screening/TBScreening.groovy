package mz.org.fgh.sifmoz.backend.screening

import com.fasterxml.jackson.annotation.JsonBackReference
import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.patientVisit.PatientVisit

class TBScreening extends BaseEntity {
    String id
    boolean parentTBTreatment
    boolean cough
    boolean fever
    boolean losingWeight
    boolean treatmentTB
    boolean treatmentTPI
  //  boolean referedToUSTB
    Date startTreatmentDate
    boolean fatigueOrTirednessLastTwoWeeks
    boolean sweating

    @JsonBackReference
    PatientVisit visit

    static belongsTo = [PatientVisit]

    static mapping = {
        id generator: "assigned"
    }

    static constraints = {
        startTreatmentDate(nullable: true, blank: true)
        visit(nullable: true, blank: true)
    }

    @Override
    String toString() {
        return "TBScreening{" +
                "parentTBTreatment=" + parentTBTreatment +
                ", cough=" + cough +
                ", fever=" + fever +
                ", losingWeight=" + losingWeight +
                ", treatmentTB=" + treatmentTB +
                ", treatmentTPI=" + treatmentTPI +
                ", startTreatmentDate=" + startTreatmentDate +
                ", fatigueOrTirednessLastTwoWeeks=" + fatigueOrTirednessLastTwoWeeks +
                ", sweating=" + sweating +
                '}'
    }
}
