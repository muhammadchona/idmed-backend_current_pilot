package mz.org.fgh.sifmoz.backend.drug


import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.form.Form
import mz.org.fgh.sifmoz.backend.service.ClinicalService
import mz.org.fgh.sifmoz.backend.stock.Stock
import mz.org.fgh.sifmoz.backend.therapeuticRegimen.TherapeuticRegimen

class Drug extends BaseEntity {
    String id
    int packSize
    String name
    double defaultTreatment // numero de toma
    int defaultTimes// numero de vezes a tomar
    String defaultPeriodTreatment //  periofo a tomar --commbo (dia , semana , mes, ano)
    String fnmCode
    String uuidOpenmrs
    ClinicalService clinicalService
    Form form
    static belongsTo = [Form, TherapeuticRegimen]
    boolean active
    static hasMany = [stockList: Stock, therapeuticRegimenList: TherapeuticRegimen]

    static mapping = {
        id generator: "assigned"
        form lazy: true

    }

    def beforeInsert() {
        if (!id) {
            id = UUID.randomUUID()
        }
    }

    static constraints = {
        name nullable: false, blank: false
        fnmCode nullable: false, unique: true
        packSize(min: 0)
        uuidOpenmrs nullable: true
        clinicalService nullable: true
     //   defaultTreatment(min: 1.00)
        defaultTimes(min:1)
        stockList nullable: true
        therapeuticRegimenList nullable: true
    }

}
