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
class DispenseTypeController {

    DispenseTypeService dispenseTypeService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond dispenseTypeService.list(params), model:[dispenseTypeCount: dispenseTypeService.count()]
    }

    def show(Long id) {
        respond dispenseTypeService.get(id)
    }

    @Transactional
    def save(DispenseType dispenseType) {
        if (dispenseType == null) {
            render status: NOT_FOUND
            return
        }
        if (dispenseType.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond dispenseType.errors
            return
        }

        try {
            dispenseTypeService.save(dispenseType)
        } catch (ValidationException e) {
            respond dispenseType.errors
            return
        }

        respond dispenseType, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(DispenseType dispenseType) {
        if (dispenseType == null) {
            render status: NOT_FOUND
            return
        }
        if (dispenseType.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond dispenseType.errors
            return
        }

        try {
            dispenseTypeService.save(dispenseType)
        } catch (ValidationException e) {
            respond dispenseType.errors
            return
        }

        respond dispenseType, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || dispenseTypeService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
