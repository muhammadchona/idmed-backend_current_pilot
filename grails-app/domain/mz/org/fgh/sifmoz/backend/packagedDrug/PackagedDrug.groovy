package mz.org.fgh.sifmoz.backend.packagedDrug

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.packaging.Pack

class PackagedDrug {
    String id
    @JsonManagedReference
    Drug drug
    int quantitySupplied
    Date nextPickUpDate
    boolean toContinue
    @JsonBackReference
    Pack pack
    static belongsTo = [Pack]

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        quantitySupplied(min: 1)
        nextPickUpDate nullable: true
    }
}
