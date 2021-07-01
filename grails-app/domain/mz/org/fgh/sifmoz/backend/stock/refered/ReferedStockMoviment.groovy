package mz.org.fgh.sifmoz.backend.stock.refered

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.stock.adjustment.StockAdjustment

@Resource(uri='/api/referedStockMoviment')
class ReferedStockMoviment {

    Date date
    String orderNumber
    String origin
    int quantity
    char updateStatus
    List<StockAdjustment> adjustments

    static mapping = {
        version false
    }

    static constraints = {
        date(nullable: false, blank: false, validator: { date, urc ->
            return ((date <= new Date()))
        })
        orderNumber(nullable: false, blank: false, maxSize: 20)
        origin(nullable: false, blank: false, maxSize: 20)
        quantity(min: 1)
    }

    @Override
    public String toString() {
        return "ReferedStockMoviment{" +
                "date=" + date +
                ", orderNumber='" + orderNumber + '\'' +
                ", origin='" + origin + '\'' +
                ", quantity=" + quantity +
                ", updateStatus=" + updateStatus +
                '}';
    }
}
