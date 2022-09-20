package mz.org.fgh.sifmoz.stock

import mz.org.fgh.sifmoz.dashboard.AbstractValidateble

class DrugStockFileEvent extends AbstractValidateble{
    String id = UUID.randomUUID()
    Date eventDate
    String moviment
    String orderNumber
    long incomes
    long outcomes
    long posetiveAdjustment
    long negativeAdjustment
    long loses
    long balance
    String notes

    void calculateBalance(long previousBalance) {
        this.balance = incomes - outcomes + posetiveAdjustment - negativeAdjustment - loses + previousBalance
    }
}
