package mz.org.fgh.sifmoz.backend.prescription

import mz.org.fgh.sifmoz.backend.drug.Drug

class PrescribedDrug {

    double amPerTime
    int timesPerDay
    boolean modified
    Prescription prescription
    Drug drug;



    static mapping = {
        version false
    }

    static constraints = {

    }

    @Override
    String toString() {

    }
}
