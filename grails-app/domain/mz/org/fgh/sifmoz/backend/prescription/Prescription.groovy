package mz.org.fgh.sifmoz.backend.prescription

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.doctor.Doctor
import mz.org.fgh.sifmoz.backend.episode.Episode
import mz.org.fgh.sifmoz.backend.prescriptionDetail.PrescriptionDetail
import mz.org.fgh.sifmoz.backend.stockentrance.StockEntrance

@Resource(uri = '/api/prescription')
class Prescription {

    int id
    int duration
    Date prescriptionDate
    Date expiryDate
    boolean current
    String notes
    String prescriptionSeq
    boolean modified
    String uuid = UUID.randomUUID().toString()
   // Doctor doctor
    //Episode episode
    static belongsTo = [doctor : Doctor,episode:Episode]




    static mapping = {
        version false
    }

    static constraints = {
        prescriptionDate(nullable: false, blank: false,  validator: { prescriptionDate, urc ->
            return ((prescriptionDate <= new Date()))})
        expiryDate(nullable: true, blank: true)
        notes(nullable: true, maxSize: 500)
        prescriptionSeq(nullable: false, blank: false,unique: true)
        duration(nullable: false, blank: false)
    }
}
