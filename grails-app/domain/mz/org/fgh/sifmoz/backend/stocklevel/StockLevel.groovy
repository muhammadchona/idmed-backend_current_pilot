package mz.org.fgh.sifmoz.backend.stocklevel

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.stock.Stock

// @Resource(uri='/api/stockLevel')
class StockLevel {

    int bacth
    int fullContainerRemaining
    int loosePillsRemaining
    Stock stock

    static mapping = {
        version false
    }

    static constraints = {
    }

    @Override
    public String toString() {
        return "StockLevel{" +
                "bacth=" + bacth +
                ", fullContainerRemaining=" + fullContainerRemaining +
                ", loosePillsRemaining=" + loosePillsRemaining +
                '}';
    }
}
