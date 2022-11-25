package mz.org.fgh.sifmoz.backend.regimenDrug

import mz.org.fgh.sifmoz.backend.base.BaseEntity

class RegimenDrug extends BaseEntity {
    String id
  //  double amPerTime
   // int timesPerDay
   // boolean modified
   // String notes
    // static belongsTo = [drug: Drug,therapeuticRegimen:TherapeuticRegimen]
 //   Drug drug
  //  TherapeuticRegimen therapeuticRegimen

    static mapping = {
        id generator: "assigned"
    }

    static constraints = {
    }
}
