package mz.org.fgh.sifmoz.backend.prescriptionDrug

import grails.rest.RestfulController
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.Transactional

class PrescribedDrugController extends RestfulController{

    IPrescribedDrugService prescribedDrugService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    PrescribedDrugController() {
        super(PrescribedDrug)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 100, 100)
        respond prescribedDrugService.list(params), model:[prescribedDrugCount: prescribedDrugService.count()]
    }

    def show(String id) {
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

    def getAllByPrescriptionId(String prescriptionId) {
        respond prescribedDrugService.getAllByPrescriptionId(prescriptionId)
    }
}
