package mz.org.fgh.sifmoz.backend.patientIdentifier

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class PatientProgramIdentifierController {

    PatientProgramIdentifierService patientProgramIdentifierService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond patientProgramIdentifierService.list(params), model:[patientProgramIdentifierCount: patientProgramIdentifierService.count()]
    }

    def show(Long id) {
        respond patientProgramIdentifierService.get(id)
    }

    @Transactional
    def save(PatientProgramIdentifier patientProgramIdentifier) {
        if (patientProgramIdentifier == null) {
            render status: NOT_FOUND
            return
        }
        if (patientProgramIdentifier.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond patientProgramIdentifier.errors
            return
        }

        try {
            patientProgramIdentifierService.save(patientProgramIdentifier)
        } catch (ValidationException e) {
            respond patientProgramIdentifier.errors
            return
        }

        respond patientProgramIdentifier, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(PatientProgramIdentifier patientProgramIdentifier) {
        if (patientProgramIdentifier == null) {
            render status: NOT_FOUND
            return
        }
        if (patientProgramIdentifier.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond patientProgramIdentifier.errors
            return
        }

        try {
            patientProgramIdentifierService.save(patientProgramIdentifier)
        } catch (ValidationException e) {
            respond patientProgramIdentifier.errors
            return
        }

        respond patientProgramIdentifier, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || patientProgramIdentifierService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
