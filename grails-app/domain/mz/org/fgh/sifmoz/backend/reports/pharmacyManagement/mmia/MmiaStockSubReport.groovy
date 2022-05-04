package mz.org.fgh.sifmoz.backend.reports.pharmacyManagement.mmia

class MmiaStockSubReport {

    String id
    String reportId
    String fnmCode
    String drugName
    String unit
    int balance
    int initialEntrance
    int outcomes
    int lossesAdjustments
    int inventory
    Date expireDate

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
    }
}
