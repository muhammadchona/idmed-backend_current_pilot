package mz.org.fgh.sifmoz.backend.screening

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.patientVisit.PatientVisit

@Resource(uri='/api/tbScreening')
class TBScreening {

    boolean parentTBTreatment
    boolean cough
    boolean fever
    boolean losingWeight
    boolean treatmentTB
    boolean treatmentTPI
    boolean referedToUSTB
    Date startTreatmentDate
    boolean fatigueOrTirednessLastTwoWeeks
    boolean sweating
    PatientVisit visit

    static mapping = {
        version false
    }

    static constraints = {
    }

    @Override
    public String toString() {
        return "TBScreening{" +
                "parentTBTreatment=" + parentTBTreatment +
                ", cough=" + cough +
                ", fever=" + fever +
                ", losingWeight=" + losingWeight +
                ", treatmentTB=" + treatmentTB +
                ", treatmentTPI=" + treatmentTPI +
                ", referedToUSTB=" + referedToUSTB +
                ", startTreatmentDate=" + startTreatmentDate +
                ", fatigueOrTirednessLastTwoWeeks=" + fatigueOrTirednessLastTwoWeeks +
                ", sweating=" + sweating +
                '}';
    }
}
