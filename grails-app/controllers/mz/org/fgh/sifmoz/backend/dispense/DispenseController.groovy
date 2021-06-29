package mz.org.fgh.sifmoz.backend.dispense

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class DispenseController {

    DispenseService dispenseService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond dispenseService.list(params), model:[dispenseCount: dispenseService.count()]
    }

    def show(Long id) {
        respond dispenseService.get(id)
    }

    @Transactional
    def save(Dispense dispense) {
        if (dispense == null) {
            render status: NOT_FOUND
            return
        }
        if (dispense.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond dispense.errors
            return
        }

        try {
            dispenseService.save(dispense)
        } catch (ValidationException e) {
            respond dispense.errors
            return
        }

        respond dispense, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Dispense dispense) {
        if (dispense == null) {
            render status: NOT_FOUND
            return
        }
        if (dispense.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond dispense.errors
            return
        }

        try {
            dispenseService.save(dispense)
        } catch (ValidationException e) {
            respond dispense.errors
            return
        }

        respond dispense, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || dispenseService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
