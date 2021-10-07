package mz.org.fgh.sifmoz.backend.regimenDrug

class RegimenDrug {
    String id
    double amPerTime
    int timesPerDay
    boolean modified
    String notes

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
    }
}
