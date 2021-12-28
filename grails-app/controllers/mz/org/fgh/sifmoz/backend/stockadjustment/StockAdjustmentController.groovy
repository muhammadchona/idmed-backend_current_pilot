package mz.org.fgh.sifmoz.backend.stockadjustment

import grails.converters.JSON
import grails.rest.RestfulController
import grails.validation.ValidationException
import mz.org.fgh.sifmoz.backend.utilities.JSONSerializer

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.Transactional

class StockAdjustmentController extends RestfulController{

    StockAdjustmentService stockAdjustmentService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    StockAdjustmentController() {
        super(StockAdjustment)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        render JSONSerializer.setObjectListJsonResponse(stockAdjustmentService.list(params)) as JSON
    }

    def show(Long id) {
        render JSONSerializer.setJsonObjectResponse(stockAdjustmentService.get(id)) as JSON
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
