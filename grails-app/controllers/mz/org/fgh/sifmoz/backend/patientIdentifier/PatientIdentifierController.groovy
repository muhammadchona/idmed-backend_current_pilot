package mz.org.fgh.sifmoz.backend.patientIdentifier

import grails.rest.RestfulController
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional


class PatientIdentifierController extends RestfulController{

    PatientIdentifierService patientIdentifierService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    PatientIdentifierController(Class resource) {
        super(resource)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond patientIdentifierService.list(params), model:[patientIdentifierCount: patientIdentifierService.count()]
    }

    def show(Long id) {
        respond patientIdentifierService.get(id)
    }

    @Transactional
    def save(PatientProgramIdentifier patientIdentifier) {
        if (patientIdentifier == null) {
            render status: NOT_FOUND
            return
        }
        if (patientIdentifier.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond patientIdentifier.errors
            return
        }

        try {
            patientIdentifierService.save(patientIdentifier)
        } catch (ValidationException e) {
            respond patientIdentifier.errors
            return
        }

        respond patientIdentifier, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(PatientProgramIdentifier patientIdentifier) {
        if (patientIdentifier == null) {
            render status: NOT_FOUND
            return
        }
        if (patientIdentifier.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond patientIdentifier.errors
            return
        }

        try {
            patientIdentifierService.save(patientIdentifier)
        } catch (ValidationException e) {
            respond patientIdentifier.errors
            return
        }

        respond patientIdentifier, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || patientIdentifierService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
