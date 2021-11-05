package mz.org.fgh.sifmoz.backend.packagedDrug

import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.packaging.Pack

class PackagedDrug {
    String id
    Drug drug
    int quantitySupplied
    Date nextPickUpDate
    boolean toContinue

    static belongsTo = [pack: Pack]

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        quantitySupplied(min: 1)
        nextPickUpDate nullable: true
    }
}
