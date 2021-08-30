package mz.org.fgh.sifmoz.backend.serviceAttributeType

import grails.rest.RestfulController
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.Transactional

class ServiceAttributeTypeController extends RestfulController{

    ServiceAttributeTypeService programAttributeTypeService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    ServiceAttributeTypeController() {
        super(ServiceAttributeType)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond programAttributeTypeService.list(params), model:[programAttributeTypeCount: programAttributeTypeService.count()]
    }

    def show(Long id) {
        respond programAttributeTypeService.get(id)
    }

    @Transactional
    def save(ServiceAttributeType programAttributeType) {
        if (programAttributeType == null) {
            render status: NOT_FOUND
            return
        }
        if (programAttributeType.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond programAttributeType.errors
            return
        }

        try {
            programAttributeTypeService.save(programAttributeType)
        } catch (ValidationException e) {
            respond programAttributeType.errors
            return
        }

        respond programAttributeType, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ServiceAttributeType programAttributeType) {
        if (programAttributeType == null) {
            render status: NOT_FOUND
            return
        }
        if (programAttributeType.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond programAttributeType.errors
            return
        }

        try {
            programAttributeTypeService.save(programAttributeType)
        } catch (ValidationException e) {
            respond programAttributeType.errors
            return
        }

        respond programAttributeType, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || programAttributeTypeService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
