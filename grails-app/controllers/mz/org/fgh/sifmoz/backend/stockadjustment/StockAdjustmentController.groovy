package mz.org.fgh.sifmoz.backend.stockadjustment

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class StockAdjustmentController {

    IStockAdjustmentService stockAdjustmentService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond stockAdjustmentService.list(params), model:[stockAdjustmentCount: stockAdjustmentService.count()]
    }

    def show(Long id) {
        respond stockAdjustmentService.get(id)
    }

    @Transactional
    def save(StockAdjustment stockAdjustment) {
        if (stockAdjustment == null) {
            render status: NOT_FOUND
            return
        }
        if (stockAdjustment.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond stockAdjustment.errors
            return
        }

        try {
            stockAdjustmentService.save(stockAdjustment)
        } catch (ValidationException e) {
            respond stockAdjustment.errors
            return
        }

        respond stockAdjustment, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(StockAdjustment stockAdjustment) {
        if (stockAdjustment == null) {
            render status: NOT_FOUND
            return
        }
        if (stockAdjustment.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond stockAdjustment.errors
            return
        }

        try {
            stockAdjustmentService.save(stockAdjustment)
        } catch (ValidationException e) {
            respond stockAdjustment.errors
            return
        }

        respond stockAdjustment, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || stockAdjustmentService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
