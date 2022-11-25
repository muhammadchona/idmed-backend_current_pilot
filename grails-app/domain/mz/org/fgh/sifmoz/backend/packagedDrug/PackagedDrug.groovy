package mz.org.fgh.sifmoz.backend.packagedDrug


import com.fasterxml.jackson.annotation.JsonManagedReference
import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.packaging.Pack

class PackagedDrug extends BaseEntity {
    String id
    @JsonManagedReference
    Drug drug
    int quantitySupplied
    Date nextPickUpDate
    boolean toContinue
    Date creationDate
    Pack pack
    static belongsTo = [Pack]

    static hasMany = [packagedDrugStocks: PackagedDrugStock]

    static mapping = {
        id generator: "assigned"
    }

    static constraints = {
        quantitySupplied(min: 0)
        nextPickUpDate nullable: true
        creationDate nullable: true
    }
}
