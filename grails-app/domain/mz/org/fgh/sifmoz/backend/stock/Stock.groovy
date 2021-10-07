package mz.org.fgh.sifmoz.backend.stock

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.stockcenter.StockCenter
import mz.org.fgh.sifmoz.backend.stockentrance.StockEntrance

// @Resource(uri='/api/stock')
class Stock {

    Date expireDate;
    boolean modified;
    String shelfNumber
    int unitsReceived
    int stockMoviment
    String manufacture
    String batchNumber
    boolean hasUnitsRemaining
    Drug drug
    StockCenter center
    static hasOne = [entrance: StockEntrance]
    //static belongsTo = [entrance : StockEntrance]

    static mapping = {
        version false
    }

    static constraints = {
        expireDate(nullable: false, blank: false)
        batchNumber(nullable: false, blank: false, unique: true)
        shelfNumber(nullable: true, maxSize: 10)
        unitsReceived(min: 1)
        stockMoviment(min: 0)
        manufacture(nullable: false, maxSize: 20)
    }
}
