package mz.org.fgh.sifmoz.backend.therapeuticRegimen

import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.form.Form
import mz.org.fgh.sifmoz.backend.service.ClinicalService

class TherapeuticRegimen {
    String id
    String regimenScheme
    boolean active
    String code
  //  boolean pedhiatric
    String description
    static belongsTo = [clincalService: ClinicalService]
    static hasMany = [drug: Drug]

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        code nullable: false, unique: true
        regimenScheme nullable: false
        description nullable: true
        clincalService nullable: true
    }
}
