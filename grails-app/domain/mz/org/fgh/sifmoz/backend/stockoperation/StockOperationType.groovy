package mz.org.fgh.sifmoz.backend.stockoperation

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
        code(nullable: false, maxSize: 50, blank: false, unique: true)
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
