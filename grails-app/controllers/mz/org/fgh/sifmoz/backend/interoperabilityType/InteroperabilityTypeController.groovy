package mz.org.fgh.sifmoz.backend.interoperabilityType

import grails.rest.RestfulController
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import grails.gorm.transactions.Transactional

class InteroperabilityTypeController extends RestfulController{

    InteroperabilityTypeService interoperabilityTypeService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    InteroperabilityTypeController() {
        super(InteroperabilityType)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond interoperabilityTypeService.list(params), model:[interoperabilityTypeCount: interoperabilityTypeService.count()]
    }

    def show(Long id) {
        respond interoperabilityTypeService.get(id)
    }

    @Transactional
    def save(InteroperabilityType interoperabilityType) {
        if (interoperabilityType == null) {
            render status: NOT_FOUND
            return
        }
        if (interoperabilityType.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond interoperabilityType.errors
            return
        }

        try {
            interoperabilityTypeService.save(interoperabilityType)
        } catch (ValidationException e) {
            respond interoperabilityType.errors
            return
        }

        respond interoperabilityType, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(InteroperabilityType interoperabilityType) {
        if (interoperabilityType == null) {
            render status: NOT_FOUND
            return
        }
        if (interoperabilityType.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond interoperabilityType.errors
            return
        }

        try {
            interoperabilityTypeService.save(interoperabilityType)
        } catch (ValidationException e) {
            respond interoperabilityType.errors
            return
        }

        respond interoperabilityType, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || interoperabilityTypeService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
