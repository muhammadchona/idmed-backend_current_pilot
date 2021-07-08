package mz.org.fgh.sifmoz.backend.stockcenter

import grails.rest.Resource

@Resource(uri='/api/stockCenter')
class StockCenter {

    String name
    boolean prefered
    String code

    static constraints = {
        name(nullable: false, blank: false)
        code(nullable: false, blank: false, unique: true)
    }

    @Override
    public String toString() {
        return "StockCenter{" +
                "name='" + name + '\'' +
                ", prefered=" + prefered +
                '}';
    }
}
