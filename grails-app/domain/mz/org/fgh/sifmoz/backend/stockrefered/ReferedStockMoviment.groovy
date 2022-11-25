package mz.org.fgh.sifmoz.backend.stockrefered


import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.stockadjustment.StockReferenceAdjustment

class ReferedStockMoviment extends BaseEntity {
    String id
    Date date
    String orderNumber
    String origin
    int quantity
    char updateStatus
    Clinic clinic
    static hasMany = [adjustments : StockReferenceAdjustment]

    static mapping = {
        id generator: "assigned"
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
    String toString() {
        return "ReferedStockMoviment{" +
                "date=" + date +
                ", orderNumber='" + orderNumber + '\'' +
                ", origin='" + origin + '\'' +
                ", quantity=" + quantity +
                ", updateStatus=" + updateStatus +
                '}'
    }
}
