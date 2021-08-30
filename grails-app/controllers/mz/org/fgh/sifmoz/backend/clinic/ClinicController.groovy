package mz.org.fgh.sifmoz.backend.clinic

import grails.rest.RestfulController
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

class ClinicController extends RestfulController{

    ClinicService clinicService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    ClinicController() {
        super(Clinic)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond clinicService.list(params), model:[clinicCount: clinicService.count()]
    }

    def show(Long id) {
        respond clinicService.get(id)
    }

    @Transactional
    def save(Clinic clinic) {
        if (clinic == null) {
            render status: NOT_FOUND
            return
        }
        if (clinic.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond clinic.errors
            return
        }

        try {
            clinicService.save(clinic)
        } catch (ValidationException e) {
            respond clinic.errors
            return
        }

        respond clinic, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Clinic clinic) {
        if (clinic == null) {
            render status: NOT_FOUND
            return
        }
        if (clinic.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond clinic.errors
            return
        }

        try {
            clinicService.save(clinic)
        } catch (ValidationException e) {
            respond clinic.errors
            return
        }

        respond clinic, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || clinicService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
