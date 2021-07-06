package mz.org.fgh.sifmoz.backend.stockoperation

import grails.rest.Resource

@Resource(uri='/api/stockOperationType')
class StockOperationType {

    String description
    String code

    static constraints = {
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
