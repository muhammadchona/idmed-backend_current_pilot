package mz.org.fgh.sifmoz.backend.service

import grails.converters.JSON
import grails.rest.RestfulController
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.Transactional

class ClinicalServiceController extends RestfulController{

    ClinicalServiceService clinicalServiceService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    ClinicalServiceController () {
        super(ClinicalService)
    }
    def index(Integer max) {
        JSON.use('deep'){
            params.max = Math.min(max ?: 10, 100)
            render clinicalServiceService.list(params) as JSON
        }
    }

    def show(String id) {
        respond clinicalServiceService.get(id)
    }

    @Transactional
    def save(ClinicalService clinicalService) {
        if (clinicalService == null) {
            render status: NOT_FOUND
            return
        }
        if (clinicalService.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond clinicalService.errors
            return
        }

        try {
            clinicalServiceService.save(clinicalService)
        } catch (ValidationException e) {
            respond clinicalService.errors
            return
        }

        respond clinicalService, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ClinicalService clinicalService) {
        if (clinicalService == null) {
            render status: NOT_FOUND
            return
        }
        if (clinicalService.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond clinicalService.errors
            return
        }

        try {
            clinicalServiceService.save(clinicalService)
        } catch (ValidationException e) {
            respond clinicalService.errors
            return
        }

        respond clinicalService, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || clinicalServiceService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
