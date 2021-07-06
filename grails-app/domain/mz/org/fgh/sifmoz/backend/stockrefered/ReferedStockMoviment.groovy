package mz.org.fgh.sifmoz.backend.stockrefered

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.stockadjustment.StockAdjustment

@Resource(uri='/api/referedStockMoviment')
class ReferedStockMoviment {

    Date date
    String orderNumber
    String origin
    int quantity
    char updateStatus
    static hasMany = [adjustments : StockAdjustment]

    static constraints = {
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
