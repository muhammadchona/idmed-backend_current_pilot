package mz.org.fgh.sifmoz.backend.prescription

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class PrescriptionDetailsController {

    PrescriptionDetailsService prescriptionDetailsService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond prescriptionDetailsService.list(params), model:[prescriptionDetailsCount: prescriptionDetailsService.count()]
    }

    def show(Long id) {
        respond prescriptionDetailsService.get(id)
    }

    @Transactional
    def save(PrescriptionDetails prescriptionDetails) {
        if (prescriptionDetails == null) {
            render status: NOT_FOUND
            return
        }
        if (prescriptionDetails.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond prescriptionDetails.errors
            return
        }

        try {
            prescriptionDetailsService.save(prescriptionDetails)
        } catch (ValidationException e) {
            respond prescriptionDetails.errors
            return
        }

        respond prescriptionDetails, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(PrescriptionDetails prescriptionDetails) {
        if (prescriptionDetails == null) {
            render status: NOT_FOUND
            return
        }
        if (prescriptionDetails.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond prescriptionDetails.errors
            return
        }

        try {
            prescriptionDetailsService.save(prescriptionDetails)
        } catch (ValidationException e) {
            respond prescriptionDetails.errors
            return
        }

        respond prescriptionDetails, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || prescriptionDetailsService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
