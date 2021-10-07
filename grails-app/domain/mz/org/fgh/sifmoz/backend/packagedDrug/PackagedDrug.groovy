package mz.org.fgh.sifmoz.backend.packagedDrug

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.packaging.Pack

class PackagedDrug {
    String id
    Pack pack
    Drug drug
    int quantitySupplied
    Clinic clinic

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        quantitySupplied(min: 1)
    }
}
