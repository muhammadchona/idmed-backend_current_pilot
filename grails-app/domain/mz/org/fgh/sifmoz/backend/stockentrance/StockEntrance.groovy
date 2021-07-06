package mz.org.fgh.sifmoz.backend.stockentrance

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.stock.Stock

@Resource(uri='/api/stockEntrance')
class StockEntrance {

    String orderNumber
    Date dateReceived
    Set<Stock> stocks

    static constraints = {
    }
}
