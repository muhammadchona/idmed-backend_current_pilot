package mz.org.fgh.sifmoz.backend.packagedDrug

import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.stock.Stock

class PackagedDrugStock extends BaseEntity {
    String id
    Drug drug
    int quantitySupplied
    Date creationDate
    Stock stock
    static belongsTo = [packagedDrug: PackagedDrug]

    static mapping = {
        id generator: "assigned"
    }

    static constraints = {
    }
}
