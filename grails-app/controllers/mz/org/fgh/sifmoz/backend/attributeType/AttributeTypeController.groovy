package mz.org.fgh.sifmoz.backend.attributeType

import grails.rest.RestfulController
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional


class AttributeTypeController extends RestfulController{

    AttributeTypeService attributeTypeService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    AttributeTypeController(Class resource) {
        super(resource)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond attributeTypeService.list(params), model:[attributeTypeCount: attributeTypeService.count()]
    }

    def show(Long id) {
        respond attributeTypeService.get(id)
    }

    @Transactional
    def save(AttributeType attributeType) {
        if (attributeType == null) {
            render status: NOT_FOUND
            return
        }
        if (attributeType.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond attributeType.errors
            return
        }

        try {
            attributeTypeService.save(attributeType)
        } catch (ValidationException e) {
            respond attributeType.errors
            return
        }

        respond attributeType, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(AttributeType attributeType) {
        if (attributeType == null) {
            render status: NOT_FOUND
            return
        }
        if (attributeType.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond attributeType.errors
            return
        }

        try {
            attributeTypeService.save(attributeType)
        } catch (ValidationException e) {
            respond attributeType.errors
            return
        }

        respond attributeType, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || attributeTypeService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
