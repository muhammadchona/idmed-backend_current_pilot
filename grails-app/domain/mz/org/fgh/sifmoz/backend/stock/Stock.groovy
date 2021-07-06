package mz.org.fgh.sifmoz.backend.stock

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.stockcenter.StockCenter

@Resource(uri='/api/stock')
class Stock {

    Date expireDate;
    boolean modified;
    String shelfNumber
    int unitsReceived
    int stockMoviment
    String manufacture
    boolean hasUnitsRemaining
    Drug drug
    StockCenter center

    static constraints = {
    }
}
