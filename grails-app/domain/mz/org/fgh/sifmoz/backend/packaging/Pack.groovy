package mz.org.fgh.sifmoz.backend.packaging

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.doctor.Doctor
import mz.org.fgh.sifmoz.backend.episode.Episode
import mz.org.fgh.sifmoz.backend.packagedDrug.PackagedDrug
import mz.org.fgh.sifmoz.backend.patientVisitDetails.PatientVisitDetails

class Pack {
    String id
    Date dateLeft
    Date dateReceived
    boolean modified
    Date packDate
    Date pickupDate
    int weeksSupply
    Date dateReturned
    int stockReturned
    int packageReturned
    String reasonForPackageReturn
    Clinic clinic
    String dispenseMode

    static belongsTo = [patientVisitDetails: PatientVisitDetails]

    static hasMany = [packagedDrugs: PackagedDrug]
    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        dateLeft(nullable: true)
        dateReceived(nullable: true, blank: true)
        packDate(nullable: true)
        pickupDate(nullable: false)
        weeksSupply(nullable: false)
        dateReturned(nullable: true)
        reasonForPackageReturn(nullable: true,maxSize: 500)
    }
}
