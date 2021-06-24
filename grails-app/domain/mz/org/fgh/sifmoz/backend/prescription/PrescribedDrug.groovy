package mz.org.fgh.sifmoz.backend.prescription

class PrescribedDrug {

    double amPerTime
    int timesPerDay
    boolean modified



    static mapping = {
        version false
    }

    static constraints = {

    }

    @Override
    String toString() {

    }
}
