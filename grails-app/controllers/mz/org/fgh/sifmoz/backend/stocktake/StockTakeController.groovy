package mz.org.fgh.sifmoz.backend.stocktake

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class StockTakeController {

    StockTakeService stockTakeService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond stockTakeService.list(params), model:[stockTakeCount: stockTakeService.count()]
    }

    def show(Long id) {
        respond stockTakeService.get(id)
    }

    @Transactional
    def save(StockTake stockTake) {
        if (stockTake == null) {
            render status: NOT_FOUND
            return
        }
        if (stockTake.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond stockTake.errors
            return
        }

        try {
            stockTakeService.save(stockTake)
        } catch (ValidationException e) {
            respond stockTake.errors
            return
        }

        respond stockTake, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(StockTake stockTake) {
        if (stockTake == null) {
            render status: NOT_FOUND
            return
        }
        if (stockTake.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond stockTake.errors
            return
        }

        try {
            stockTakeService.save(stockTake)
        } catch (ValidationException e) {
            respond stockTake.errors
            return
        }

        respond stockTake, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || stockTakeService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
