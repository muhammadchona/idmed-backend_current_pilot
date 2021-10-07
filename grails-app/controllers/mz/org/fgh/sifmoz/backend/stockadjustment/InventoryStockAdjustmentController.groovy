package mz.org.fgh.sifmoz.backend.stockadjustment

import grails.rest.RestfulController
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional


class InventoryStockAdjustmentController extends RestfulController{

    IInventoryStockAdjustmentService inventoryStockAdjustmentService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    InventoryStockAdjustmentController(Class resource) {
        super(resource)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond inventoryStockAdjustmentService.list(params), model:[inventoryStockAdjustmentCount: inventoryStockAdjustmentService.count()]
    }

    def show(Long id) {
        respond inventoryStockAdjustmentService.get(id)
    }

    @Transactional
    def save(InventoryStockAdjustment inventoryStockAdjustment) {
        if (inventoryStockAdjustment == null) {
            render status: NOT_FOUND
            return
        }
        if (inventoryStockAdjustment.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond inventoryStockAdjustment.errors
            return
        }

        try {
            inventoryStockAdjustmentService.save(inventoryStockAdjustment)
        } catch (ValidationException e) {
            respond inventoryStockAdjustment.errors
            return
        }

        respond inventoryStockAdjustment, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(InventoryStockAdjustment inventoryStockAdjustment) {
        if (inventoryStockAdjustment == null) {
            render status: NOT_FOUND
            return
        }
        if (inventoryStockAdjustment.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond inventoryStockAdjustment.errors
            return
        }

        try {
            inventoryStockAdjustmentService.save(inventoryStockAdjustment)
        } catch (ValidationException e) {
            respond inventoryStockAdjustment.errors
            return
        }

        respond inventoryStockAdjustment, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || inventoryStockAdjustmentService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
