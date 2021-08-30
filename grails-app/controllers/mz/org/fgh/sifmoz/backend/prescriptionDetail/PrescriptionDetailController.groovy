package mz.org.fgh.sifmoz.backend.prescriptionDetail

import grails.rest.RestfulController
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

class PrescriptionDetailController extends RestfulController{

    PrescriptionDetailService prescriptionDetailService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    PrescriptionDetailController() {
        super(PrescriptionDetail)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond prescriptionDetailService.list(params), model:[prescriptionDetailCount: prescriptionDetailService.count()]
    }

    def show(Long id) {
        respond prescriptionDetailService.get(id)
    }

    @Transactional
    def save(PrescriptionDetail prescriptionDetail) {
        if (prescriptionDetail == null) {
            render status: NOT_FOUND
            return
        }
        if (prescriptionDetail.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond prescriptionDetail.errors
            return
        }

        try {
            prescriptionDetailService.save(prescriptionDetail)
        } catch (ValidationException e) {
            respond prescriptionDetail.errors
            return
        }

        respond prescriptionDetail, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(PrescriptionDetail prescriptionDetail) {
        if (prescriptionDetail == null) {
            render status: NOT_FOUND
            return
        }
        if (prescriptionDetail.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond prescriptionDetail.errors
            return
        }

        try {
            prescriptionDetailService.save(prescriptionDetail)
        } catch (ValidationException e) {
            respond prescriptionDetail.errors
            return
        }

        respond prescriptionDetail, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || prescriptionDetailService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
