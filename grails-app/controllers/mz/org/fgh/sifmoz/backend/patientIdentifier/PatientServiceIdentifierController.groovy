package mz.org.fgh.sifmoz.backend.patientIdentifier

import grails.rest.RestfulController
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.Transactional

class PatientServiceIdentifierController extends RestfulController{

    PatientServiceIdentifierService patientServiceIdentifierService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    PatientServiceIdentifierController () {
        super(PatientServiceIdentifier)
    }
  
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond patientServiceIdentifierService.list(params), model:[patientServiceIdentifierCount: patientServiceIdentifierService.count()]
    }

    def show(Long id) {
        respond patientServiceIdentifierService.get(id)
    }

    @Transactional
    def save(PatientServiceIdentifier patientServiceIdentifier) {
        if (patientServiceIdentifier == null) {
            render status: NOT_FOUND
            return
        }
        if (patientServiceIdentifier.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond patientServiceIdentifier.errors
            return
        }

        try {
            patientServiceIdentifierService.save(patientServiceIdentifier)
        } catch (ValidationException e) {
            respond patientServiceIdentifier.errors
            return
        }

        respond patientServiceIdentifier, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(PatientServiceIdentifier patientServiceIdentifier) {
        if (patientServiceIdentifier == null) {
            render status: NOT_FOUND
            return
        }
        if (patientServiceIdentifier.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond patientServiceIdentifier.errors
            return
        }

        try {
            patientServiceIdentifierService.save(patientServiceIdentifier)
        } catch (ValidationException e) {
            respond patientServiceIdentifier.errors
            return
        }

        respond patientServiceIdentifier, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || patientServiceIdentifierService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
