package mz.org.fgh.sifmoz.backend.therapeuticRegimen

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.service.ClinicalService

class TherapeuticRegimen extends BaseEntity {
    String id
    String regimenScheme
    boolean active
    String code
  //  boolean pedhiatric
    String description
    String openmrsUuid
    @JsonBackReference
    ClinicalService clincalService
    static belongsTo = [ClinicalService]
    @JsonIgnore
    static hasMany = [drugs: Drug]

    static mapping = {
        id generator: "assigned"
    }

    def beforeInsert() {
        if (!id) {
            id = UUID.randomUUID()
        }
    }

    static constraints = {
        code nullable: false, unique: true
        regimenScheme nullable: false
        description nullable: true
        clincalService nullable: true
        openmrsUuid nullable: true
    }
}
