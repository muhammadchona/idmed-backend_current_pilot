package mz.org.fgh.sifmoz.backend.stock.entrance

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.stock.Stock

@Resource(uri='/api/stockEntrance')
class StockEntrance {

    String orderNumber
    Date dateReceived

    List<Stock> stockList

    static mapping = {
        version false
    }

    static constraints = {
        orderNumber(nullable: false, blank: false)
        dateReceived(nullable: false, blank: false, validator: { dateReceived, urc ->
            return ((dateReceived <= new Date()))
        })
    }

    @Override
    public String toString() {
        return "StockEntrance{" +
                "orderNumber='" + orderNumber + '\'' +
                ", dateReceived=" + dateReceived +
                '}';
    }
}
