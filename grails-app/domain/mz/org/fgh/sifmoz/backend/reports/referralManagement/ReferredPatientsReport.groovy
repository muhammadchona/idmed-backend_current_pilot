package mz.org.fgh.sifmoz.backend.reports.referralManagement

class ReferredPatientsReport {

    String id //ReportId
    String reportId
    String pharmacyId
    String clinicalServiceId
    String provinceId
    String districtId
    String periodType
    String period
    Date startDate
    Date endDate
    String nid
    String name
    int age
    Date lastPrescriptionDate
    String therapeuticalRegimen
    String dispenseType
    Date nextPickUpDate
    Date referrenceDate
    String referralPharmacy
    String notes
    Date dateBackUs
    String tarvType
    Date pickUpDate
    String contact
    Date dateMissedPickUp
    Date dateIdentifiedAbandonment
    Date returnedPickUp
    Date lastPickUpDate

    static constraints = {
        id generator: "assigned"
        pharmacyId nullable: true
        provinceId nullable: true
        districtId nullable: true
        periodType nullable: false , inList: ['SPECIFIC','MONTH','QUARTER','SEMESTER','ANNUAL']
        period nullable: true
        startDate nullable: true
        endDate nullable: true
        therapeuticalRegimen nullable: true
        lastPrescriptionDate nullable: true
        dispenseType nullable: true
        nextPickUpDate nullable: true
        referrenceDate nullable: true
        referralPharmacy nullable: true
        notes nullable: true
        dateBackUs nullable: true
        tarvType nullable: true
        pickUpDate nullable: true
        contact nullable: true
        dateMissedPickUp nullable: true
        dateIdentifiedAbandonment nullable: true
        returnedPickUp nullable: true
        lastPickUpDate nullable: true
    }
}
