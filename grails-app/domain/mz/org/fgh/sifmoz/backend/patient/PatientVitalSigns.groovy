package mz.org.fgh.sifmoz.backend.patient.vitalsigns

import grails.rest.Resource

@Resource(uri='/api/patientVitalSigns')
class PatientVitalSigns {

    int distort;
    String imc;
    double weight;
    int systole;
    double height;

    static mapping = {
        version false
    }

    static constraints = {
        distort (nullable: false)
    }

    @Override
    public String toString() {
        return "PatientVitalSigns{" +
                "distort=" + distort +
                ", imc='" + imc + '\'' +
                ", weight=" + weight +
                ", systole=" + systole +
                ", height=" + height +
                '}';
    }
}
