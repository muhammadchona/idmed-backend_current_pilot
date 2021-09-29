package mz.org.fgh.sifmoz.backend.service.attribute.type

import grails.rest.RestfulController
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

class ClinicalServiceAttributeTypeController extends RestfulController{

    ClinicalServiceAttributeTypeService clinicalServiceAttributeTypeService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    ClinicalServiceAttributeTypeController () {
        super(ClinicalServiceAttributeType)
    }
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond clinicalServiceAttributeTypeService.list(params), model:[clinicalServiceAttributeTypeCount: clinicalServiceAttributeTypeService.count()]
    }

    def show(Long id) {
        respond clinicalServiceAttributeTypeService.get(id)
    }

    @Transactional
    def save(ClinicalServiceAttributeType clinicalServiceAttributeType) {
        if (clinicalServiceAttributeType == null) {
            render status: NOT_FOUND
            return
        }
        if (clinicalServiceAttributeType.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond clinicalServiceAttributeType.errors
            return
        }

        try {
            clinicalServiceAttributeTypeService.save(clinicalServiceAttributeType)
        } catch (ValidationException e) {
            respond clinicalServiceAttributeType.errors
            return
        }

        respond clinicalServiceAttributeType, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ClinicalServiceAttributeType clinicalServiceAttributeType) {
        if (clinicalServiceAttributeType == null) {
            render status: NOT_FOUND
            return
        }
        if (clinicalServiceAttributeType.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond clinicalServiceAttributeType.errors
            return
        }

        try {
            clinicalServiceAttributeTypeService.save(clinicalServiceAttributeType)
        } catch (ValidationException e) {
            respond clinicalServiceAttributeType.errors
            return
        }

        respond clinicalServiceAttributeType, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || clinicalServiceAttributeTypeService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
