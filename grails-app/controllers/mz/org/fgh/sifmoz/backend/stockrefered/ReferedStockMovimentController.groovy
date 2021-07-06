package mz.org.fgh.sifmoz.backend.stockrefered

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class ReferedStockMovimentController {

    ReferedStockMovimentService referedStockMovimentService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond referedStockMovimentService.list(params), model:[referedStockMovimentCount: referedStockMovimentService.count()]
    }

    def show(Long id) {
        respond referedStockMovimentService.get(id)
    }

    @Transactional
    def save(ReferedStockMoviment referedStockMoviment) {
        if (referedStockMoviment == null) {
            render status: NOT_FOUND
            return
        }
        if (referedStockMoviment.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond referedStockMoviment.errors
            return
        }

        try {
            referedStockMovimentService.save(referedStockMoviment)
        } catch (ValidationException e) {
            respond referedStockMoviment.errors
            return
        }

        respond referedStockMoviment, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ReferedStockMoviment referedStockMoviment) {
        if (referedStockMoviment == null) {
            render status: NOT_FOUND
            return
        }
        if (referedStockMoviment.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond referedStockMoviment.errors
            return
        }

        try {
            referedStockMovimentService.save(referedStockMoviment)
        } catch (ValidationException e) {
            respond referedStockMoviment.errors
            return
        }

        respond referedStockMoviment, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || referedStockMovimentService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
