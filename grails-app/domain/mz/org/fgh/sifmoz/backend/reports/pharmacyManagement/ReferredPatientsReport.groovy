package mz.org.fgh.sifmoz.backend.reports.pharmacyManagement

class ReferredPatientsReport {

    Long id //ReportId
    String reportId
    String pharmacyId
    String provinceId
    String districtId
    String period
    String month
    String quarter
    String semester
    String annual
    String year
    Date startDate
    Date endDate
    String nid
    String name
    String age
    String lastPrescriptionDate
    String therapeuticalRegimen
    String dispenseType
    Date nexPickUpDate
    Date referrenceDate
    String referralPharmacy

    static constraints = {
        id generator: "uuid"
        pharmacyId nullable: true
        provinceId nullable: true
        districtId nullable: true
        period nullable: true
        month nullable: true
        quarter nullable: true
        semester nullable: true
        annual nullable: true
        startDate nullable: true
        endDate nullable: true
        year nullable: true
    }
}
