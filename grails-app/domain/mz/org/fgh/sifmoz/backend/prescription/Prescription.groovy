package mz.org.fgh.sifmoz.backend.prescription

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.doctor.Doctor
import mz.org.fgh.sifmoz.backend.episode.Episode

@Resource(uri = '/api/prescription')
class Prescription {

    boolean clinicalStage
    int duration
    Date prescriptionDate
    Date expiryDate
    boolean current
    String notes
    String prescriptionSeq
    String uuid = UUID.randomUUID().toString()
    Doctor doctor
    Episode episode

    static constraints = {
    }
}
