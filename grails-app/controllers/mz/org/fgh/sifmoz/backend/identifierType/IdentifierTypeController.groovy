package mz.org.fgh.sifmoz.backend.identifierType

import grails.rest.RestfulController
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

class IdentifierTypeController extends RestfulController{

    IdentifierTypeService identifierTypeService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    IdentifierTypeController() {
        super(IdentifierType)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond identifierTypeService.list(params), model:[identifierTypeCount: identifierTypeService.count()]
    }

    def show(Long id) {
        respond identifierTypeService.get(id)
    }

    @Transactional
    def save(IdentifierType identifierType) {
        if (identifierType == null) {
            render status: NOT_FOUND
            return
        }
        if (identifierType.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond identifierType.errors
            return
        }

        try {
            identifierTypeService.save(identifierType)
        } catch (ValidationException e) {
            respond identifierType.errors
            return
        }

        respond identifierType, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(IdentifierType identifierType) {
        if (identifierType == null) {
            render status: NOT_FOUND
            return
        }
        if (identifierType.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond identifierType.errors
            return
        }

        try {
            identifierTypeService.save(identifierType)
        } catch (ValidationException e) {
            respond identifierType.errors
            return
        }

        respond identifierType, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || identifierTypeService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
