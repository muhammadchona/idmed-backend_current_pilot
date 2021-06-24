package mz.org.fgh.sifmoz.backend.therapeuticRegimen

class RegimenDrug {

    double amPerTime
    int timesPerDay
    boolean modified
    String notes



    static mapping = {
        version false
    }

    static constraints = {

    }

    @Override
    String toString() {

    }
}
