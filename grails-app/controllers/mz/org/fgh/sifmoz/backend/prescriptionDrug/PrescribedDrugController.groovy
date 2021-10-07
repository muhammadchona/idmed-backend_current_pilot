package mz.org.fgh.sifmoz.backend.prescriptionDrug

import grails.rest.RestfulController
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional


class PrescribedDrugController extends RestfulController{

    PrescribedDrugService prescribedDrugService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    PrescribedDrugController(Class resource) {
        super(resource)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond prescribedDrugService.list(params), model:[prescribedDrugCount: prescribedDrugService.count()]
    }

    def show(Long id) {
        respond prescribedDrugService.get(id)
    }

    @Transactional
    def save(PrescribedDrug prescribedDrug) {
        if (prescribedDrug == null) {
            render status: NOT_FOUND
            return
        }
        if (prescribedDrug.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond prescribedDrug.errors
            return
        }

        try {
            prescribedDrugService.save(prescribedDrug)
        } catch (ValidationException e) {
            respond prescribedDrug.errors
            return
        }

        respond prescribedDrug, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(PrescribedDrug prescribedDrug) {
        if (prescribedDrug == null) {
            render status: NOT_FOUND
            return
        }
        if (prescribedDrug.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond prescribedDrug.errors
            return
        }

        try {
            prescribedDrugService.save(prescribedDrug)
        } catch (ValidationException e) {
            respond prescribedDrug.errors
            return
        }

        respond prescribedDrug, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || prescribedDrugService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
