package mz.org.fgh.sifmoz.backend.stockadjustment

import grails.rest.RestfulController
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional


class StockReferenceAdjustmentController extends RestfulController{

    IStockReferenceAdjustmentService stockReferenceAdjustmentService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    StockReferenceAdjustmentController(Class resource) {
        super(resource)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond stockReferenceAdjustmentService.list(params), model:[stockReferenceAdjustmentCount: stockReferenceAdjustmentService.count()]
    }

    def show(Long id) {
        respond stockReferenceAdjustmentService.get(id)
    }

    @Transactional
    def save(StockReferenceAdjustment stockReferenceAdjustment) {
        if (stockReferenceAdjustment == null) {
            render status: NOT_FOUND
            return
        }
        if (stockReferenceAdjustment.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond stockReferenceAdjustment.errors
            return
        }

        try {
            stockReferenceAdjustmentService.save(stockReferenceAdjustment)
        } catch (ValidationException e) {
            respond stockReferenceAdjustment.errors
            return
        }

        respond stockReferenceAdjustment, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(StockReferenceAdjustment stockReferenceAdjustment) {
        if (stockReferenceAdjustment == null) {
            render status: NOT_FOUND
            return
        }
        if (stockReferenceAdjustment.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond stockReferenceAdjustment.errors
            return
        }

        try {
            stockReferenceAdjustmentService.save(stockReferenceAdjustment)
        } catch (ValidationException e) {
            respond stockReferenceAdjustment.errors
            return
        }

        respond stockReferenceAdjustment, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || stockReferenceAdjustmentService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
