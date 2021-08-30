package mz.org.fgh.sifmoz.backend.drug

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.doctor.Doctor
import mz.org.fgh.sifmoz.backend.episode.Episode
import mz.org.fgh.sifmoz.backend.form.Form

class Drug {
    String id
    int packSize
    boolean sideTreatment
    String name
    double defaultTament
    int defaultTimes
    String uuidOpenmrs = UUID.randomUUID().toString()
    static belongsTo = [form: Form]

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        name nullable: false, blank: false
        packSize(min: 0)
        defaultTament(min: 0)
        defaultTimes(min:1)
    }
}
