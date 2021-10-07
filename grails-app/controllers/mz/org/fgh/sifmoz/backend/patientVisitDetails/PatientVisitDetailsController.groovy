package mz.org.fgh.sifmoz.backend.patientVisitDetails

import grails.rest.RestfulController
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

class PatientVisitDetailsController extends RestfulController{

    PatientVisitDetailsService patientVisitDetailsService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    PatientVisitDetailsController() {
        super(PatientVisitDetails)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond patientVisitDetailsService.list(params), model:[patientVisitDetailsCount: patientVisitDetailsService.count()]
    }

    def show(Long id) {
        respond patientVisitDetailsService.get(id)
    }

    @Transactional
    def save(PatientVisitDetails patientVisitDetails) {
        if (patientVisitDetails == null) {
            render status: NOT_FOUND
            return
        }
        if (patientVisitDetails.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond patientVisitDetails.errors
            return
        }

        try {
            patientVisitDetailsService.save(patientVisitDetails)
        } catch (ValidationException e) {
            respond patientVisitDetails.errors
            return
        }

        respond patientVisitDetails, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(PatientVisitDetails patientVisitDetails) {
        if (patientVisitDetails == null) {
            render status: NOT_FOUND
            return
        }
        if (patientVisitDetails.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond patientVisitDetails.errors
            return
        }

        try {
            patientVisitDetailsService.save(patientVisitDetails)
        } catch (ValidationException e) {
            respond patientVisitDetails.errors
            return
        }

        respond patientVisitDetails, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || patientVisitDetailsService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
