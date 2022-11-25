package mz.org.fgh.sifmoz.backend.stocklevel


import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.stock.Stock

class StockLevel extends BaseEntity {
    String id
    int bacth
    int fullContainerRemaining
    int loosePillsRemaining
    Clinic clinic
    Stock stock

    static mapping = {
        id generator: "assigned"
    }

    static constraints = {
    }

    @Override
    String toString() {
        return "StockLevel{" +
                "bacth=" + bacth +
                ", fullContainerRemaining=" + fullContainerRemaining +
                ", loosePillsRemaining=" + loosePillsRemaining +
                '}'
    }
}
