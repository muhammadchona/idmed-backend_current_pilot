package mz.org.fgh.sifmoz.backend.stockoperation

import grails.rest.RestfulController
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional


class StockOperationTypeController extends RestfulController{

    StockOperationTypeService stockOperationTypeService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    StockOperationTypeController(Class resource) {
        super(resource)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond stockOperationTypeService.list(params), model:[stockOperationTypeCount: stockOperationTypeService.count()]
    }

    def show(Long id) {
        respond stockOperationTypeService.get(id)
    }

    @Transactional
    def save(StockOperationType stockOperationType) {
        if (stockOperationType == null) {
            render status: NOT_FOUND
            return
        }
        if (stockOperationType.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond stockOperationType.errors
            return
        }

        try {
            stockOperationTypeService.save(stockOperationType)
        } catch (ValidationException e) {
            respond stockOperationType.errors
            return
        }

        respond stockOperationType, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(StockOperationType stockOperationType) {
        if (stockOperationType == null) {
            render status: NOT_FOUND
            return
        }
        if (stockOperationType.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond stockOperationType.errors
            return
        }

        try {
            stockOperationTypeService.save(stockOperationType)
        } catch (ValidationException e) {
            respond stockOperationType.errors
            return
        }

        respond stockOperationType, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || stockOperationTypeService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
