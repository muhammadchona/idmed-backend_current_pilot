package mz.org.fgh.sifmoz.backend.stock

import grails.rest.Resource

@Resource(uri='/api/stock')
class Stock {

    Date expireDate;
    boolean modified;
    String shelfNumber
    int unitsReceived
    int stockMoviment
    String manufacture
    boolean hasUnitsRemaining

    static mapping = {
        version false
    }

    static constraints = {
        expireDate(nullable: false, blank: false)
        shelfNumber(nullable: true, maxSize: 10)
        unitsReceived(min: 1)
        stockMoviment(min: 0, max: unitsReceived)
        manufacture(nullable: false, maxSize: 20)
    }

    @Override
    public String toString() {
        return "Stock{" +
                "expireDate=" + expireDate +
                ", modified=" + modified +
                ", shelfNumber='" + shelfNumber + '\'' +
                ", unitsReceived=" + unitsReceived +
                ", stockMoviment=" + stockMoviment +
                ", manufacture='" + manufacture + '\'' +
                ", hasUnitsRemaining=" + hasUnitsRemaining +
                '}';
    }
}
