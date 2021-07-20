package mz.org.fgh.sifmoz.backend.regimenDrug

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.doctor.Doctor
import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.episode.Episode
import mz.org.fgh.sifmoz.backend.therapeuticRegimen.TherapeuticRegimen

@Resource(uri='/api/regimenDrug')
class RegimenDrug {

    double amPerTime
    int timesPerDay
    boolean modified
    String notes

    static belongsTo = [drug: Drug, therapeuticRegimen: TherapeuticRegimen]

    static constraints = {
        amPerTime(min: 0)
        timesPerDay(min:1)
    }
}
