package mz.org.fgh.sifmoz.backend.packaging

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.doctor.Doctor
import mz.org.fgh.sifmoz.backend.episode.Episode
import mz.org.fgh.sifmoz.backend.patientVisitDetails.PatientVisitDetails


@Resource(uri='/api/pack')
class Pack {

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
    String uuid = UUID.randomUUID().toString()
   // static belongsTo = [patientVisitDetails: PatientVisitDetails]

    static constraints = {
        dateLeft(nullable: false, blank: false)
        dateReceived(nullable: true, blank: true)
        packDate(nullable: true)
        pickupDate(nullable: true)
        weeksSupply(nullable: false)
        dateReturned(nullable: true)
        reasonForPackageReturn(nullable: true,maxSize: 500)
    }
}
