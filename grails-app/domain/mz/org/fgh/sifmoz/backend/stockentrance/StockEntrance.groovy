package mz.org.fgh.sifmoz.backend.stockentrance

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.stock.Stock

// @Resource(uri='/api/stockEntrance')
class StockEntrance {

    String orderNumber
    Date dateReceived
    static hasMany = [stocks : Stock]

    static constraints = {
        orderNumber(nullable: false, blank: false, unique: true)
        dateReceived(nullable: false, blank: false, validator: { dateReceived, urc ->
            return ((dateReceived <= new Date()))
        })
    }
}
