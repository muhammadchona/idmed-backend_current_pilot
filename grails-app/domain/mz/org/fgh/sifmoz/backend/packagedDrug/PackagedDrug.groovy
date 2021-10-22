package mz.org.fgh.sifmoz.backend.packagedDrug

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.packaging.Pack

class PackagedDrug {
    String id
    Drug drug
    int quantitySupplied
    Clinic clinic

    static belongsTo = [pack: Pack]

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        quantitySupplied(min: 1)
    }
}
