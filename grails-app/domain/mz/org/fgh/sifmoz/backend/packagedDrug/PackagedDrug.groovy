package mz.org.fgh.sifmoz.backend.packagedDrug

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.packaging.Pack


@Resource(uri='/api/packagedDrug')
class PackagedDrug {

    Pack pack
    Drug drug
    int quantitySupplied

    static constraints = {
        quantitySupplied(min: 1)
    }
}
