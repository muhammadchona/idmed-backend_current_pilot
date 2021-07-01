package mz.org.fgh.sifmoz.backend.stock.center

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.stock.Stock

@Resource(uri='/api/stockCenter')
class StockCenter {

    String name
    boolean prefered
    Stock stock

    static mapping = {
        version false
    }

    static constraints = {
        name(nullable: false, blank: false)
    }

    @Override
    public String toString() {
        return "StockCenter{" +
                "name='" + name + '\'' +
                ", prefered=" + prefered +
                '}';
    }
}
