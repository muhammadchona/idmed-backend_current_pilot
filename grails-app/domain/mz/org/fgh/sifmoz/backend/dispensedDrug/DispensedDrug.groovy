package mz.org.fgh.sifmoz.backend.dispensedDrug

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.dispense.Dispense
import mz.org.fgh.sifmoz.backend.drug.Drug


@Resource(uri='/api/dispensedrug')
class DispensedDrug {

    Dispense dispense
    Drug drug

    static constraints = {
    }
}
