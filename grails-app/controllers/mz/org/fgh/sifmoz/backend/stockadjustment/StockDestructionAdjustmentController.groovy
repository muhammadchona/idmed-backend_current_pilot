package mz.org.fgh.sifmoz.backend.stockadjustment

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class StockDestructionAdjustmentController {

    StockDestructionAdjustmentService stockDestructionAdjustmentService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond stockDestructionAdjustmentService.list(params), model:[stockDestructionAdjustmentCount: stockDestructionAdjustmentService.count()]
    }

    def show(Long id) {
        respond stockDestructionAdjustmentService.get(id)
    }

    @Transactional
    def save(StockDestructionAdjustment stockDestructionAdjustment) {
        if (stockDestructionAdjustment == null) {
            render status: NOT_FOUND
            return
        }
        if (stockDestructionAdjustment.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond stockDestructionAdjustment.errors
            return
        }

        try {
            stockDestructionAdjustmentService.save(stockDestructionAdjustment)
        } catch (ValidationException e) {
            respond stockDestructionAdjustment.errors
            return
        }

        respond stockDestructionAdjustment, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(StockDestructionAdjustment stockDestructionAdjustment) {
        if (stockDestructionAdjustment == null) {
            render status: NOT_FOUND
            return
        }
        if (stockDestructionAdjustment.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond stockDestructionAdjustment.errors
            return
        }

        try {
            stockDestructionAdjustmentService.save(stockDestructionAdjustment)
        } catch (ValidationException e) {
            respond stockDestructionAdjustment.errors
            return
        }

        respond stockDestructionAdjustment, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || stockDestructionAdjustmentService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
