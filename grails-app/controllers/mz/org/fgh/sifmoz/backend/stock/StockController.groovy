package mz.org.fgh.sifmoz.backend.stock

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class StockController {

    StockService stockService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond stockService.list(params), model:[stockCount: stockService.count()]
    }

    def show(Long id) {
        respond stockService.get(id)
    }

    @Transactional
    def save(Stock stock) {
        if (stock == null) {
            render status: NOT_FOUND
            return
        }
        if (stock.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond stock.errors
            return
        }

        try {
            stockService.save(stock)
        } catch (ValidationException e) {
            respond stock.errors
            return
        }

        respond stock, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Stock stock) {
        if (stock == null) {
            render status: NOT_FOUND
            return
        }
        if (stock.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond stock.errors
            return
        }

        try {
            stockService.save(stock)
        } catch (ValidationException e) {
            respond stock.errors
            return
        }

        respond stock, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || stockService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
