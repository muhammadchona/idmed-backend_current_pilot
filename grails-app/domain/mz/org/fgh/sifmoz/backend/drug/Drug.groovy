package mz.org.fgh.sifmoz.backend.drug

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinicSector.ClinicSector
import mz.org.fgh.sifmoz.backend.doctor.Doctor
import mz.org.fgh.sifmoz.backend.episode.Episode
import mz.org.fgh.sifmoz.backend.form.Form
import mz.org.fgh.sifmoz.backend.therapeuticRegimen.TherapeuticRegimen

class Drug {
    String id
    int packSize
 //   boolean sideTreatment
    String name
    double defaultTreatment // numero de toma
    int defaultTimes// numero de vezes a tomar
    String defaultPeriodTreatment //  periofo a tomar --commbo (dia , semana , mes, ano)
    String fnmCode
    String uuidOpenmrs = UUID.randomUUID().toString()
    static belongsTo = [form: Form]
  //  static hasMany = [therapeuticRegimen: TherapeuticRegimen]
    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        name nullable: false, blank: false
        packSize(min: 0)
     //   defaultTreatment(min: 1.00)
        defaultTimes(min:1)
    }
}
