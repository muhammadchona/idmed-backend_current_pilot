package mz.org.fgh.sifmoz.backend.stockdestruction

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class DestroyedStockController {

    DestroyedStockService destroyedStockService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond destroyedStockService.list(params), model:[destroyedStockCount: destroyedStockService.count()]
    }

    def show(Long id) {
        respond destroyedStockService.get(id)
    }

    @Transactional
    def save(DestroyedStock destroyedStock) {
        if (destroyedStock == null) {
            render status: NOT_FOUND
            return
        }
        if (destroyedStock.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond destroyedStock.errors
            return
        }

        try {
            destroyedStockService.save(destroyedStock)
        } catch (ValidationException e) {
            respond destroyedStock.errors
            return
        }

        respond destroyedStock, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(DestroyedStock destroyedStock) {
        if (destroyedStock == null) {
            render status: NOT_FOUND
            return
        }
        if (destroyedStock.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond destroyedStock.errors
            return
        }

        try {
            destroyedStockService.save(destroyedStock)
        } catch (ValidationException e) {
            respond destroyedStock.errors
            return
        }

        respond destroyedStock, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || destroyedStockService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
