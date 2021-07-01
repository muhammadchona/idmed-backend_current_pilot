package mz.org.fgh.sifmoz.backend.stock.entrance

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class StockEntranceController {

    StockEntranceService stockEntranceService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond stockEntranceService.list(params), model:[stockEntranceCount: stockEntranceService.count()]
    }

    def show(Long id) {
        respond stockEntranceService.get(id)
    }

    @Transactional
    def save(StockEntrance stockEntrance) {
        if (stockEntrance == null) {
            render status: NOT_FOUND
            return
        }
        if (stockEntrance.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond stockEntrance.errors
            return
        }

        try {
            stockEntranceService.save(stockEntrance)
        } catch (ValidationException e) {
            respond stockEntrance.errors
            return
        }

        respond stockEntrance, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(StockEntrance stockEntrance) {
        if (stockEntrance == null) {
            render status: NOT_FOUND
            return
        }
        if (stockEntrance.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond stockEntrance.errors
            return
        }

        try {
            stockEntranceService.save(stockEntrance)
        } catch (ValidationException e) {
            respond stockEntrance.errors
            return
        }

        respond stockEntrance, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || stockEntranceService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
