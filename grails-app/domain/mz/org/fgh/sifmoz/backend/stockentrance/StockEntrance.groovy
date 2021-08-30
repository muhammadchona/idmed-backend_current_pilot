package mz.org.fgh.sifmoz.backend.stockentrance

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.stock.Stock

class StockEntrance {
    String id
    String orderNumber
    Date dateReceived
    Clinic clinic
    static hasMany = [stocks : Stock]

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        orderNumber(nullable: false, blank: false, unique: true)
        dateReceived(nullable: false, blank: false, validator: { dateReceived, urc ->
            return ((dateReceived <= new Date()))
        })
    }
}
