package mz.org.fgh.sifmoz.backend.stock.operationtype

import grails.rest.Resource

@Resource(uri='/api/stockOperationType')
class StockOperationType {

    String description
    String code

    static mapping = {
        version false
    }

    static constraints = {
        description(nullable: false, maxSize: 50, blank: false)
        code(nullable: false, maxSize: 50, blank: false)
    }

    @Override
    public String toString() {
        return "StockOperationType{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
