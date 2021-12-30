package mz.org.fgh.sifmoz.backend.packaging

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.dispenseMode.DispenseMode
import mz.org.fgh.sifmoz.backend.packagedDrug.PackagedDrug
import mz.org.fgh.sifmoz.backend.patientVisitDetails.PatientVisitDetails
import mz.org.fgh.sifmoz.backend.stock.Stock

class Pack {
    String id
    Date dateLeft
    Date dateReceived
    boolean modified
    Date packDate
    Date pickupDate
    Date nextPickUpDate
    int weeksSupply
    Date dateReturned
    int stockReturned
    int packageReturned
    String reasonForPackageReturn
    @JsonIgnore
    Clinic clinic
    DispenseMode dispenseMode
    char syncStatus = 'N'

    @JsonBackReference
    PatientVisitDetails patientVisitDetails

    static belongsTo = [PatientVisitDetails]

    @JsonManagedReference
    static hasMany = [packagedDrugs: PackagedDrug]
    static mapping = {
        id generator: "uuid"
        packagedDrugs lazy: true
    }

    static constraints = {
        dateLeft(nullable: true)
        dateReceived(nullable: true, blank: true)
        packDate(nullable: true)
        pickupDate(nullable: false)
        weeksSupply(nullable: false)
        dateReturned(nullable: true)
        syncStatus(nullable: true)
        dispenseMode(nullable: true)
        reasonForPackageReturn(nullable: true,maxSize: 500)
    }
}
