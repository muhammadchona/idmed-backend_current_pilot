package mz.org.fgh.sifmoz.backend.patientProgram

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class PatientProgramController {

    PatientProgramService patientProgramService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond patientProgramService.list(params), model:[patientProgramCount: patientProgramService.count()]
    }

    def show(Long id) {
        respond patientProgramService.get(id)
    }

    @Transactional
    def save(PatientProgram patientProgram) {
        if (patientProgram == null) {
            render status: NOT_FOUND
            return
        }
        if (patientProgram.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond patientProgram.errors
            return
        }

        try {
            patientProgramService.save(patientProgram)
        } catch (ValidationException e) {
            respond patientProgram.errors
            return
        }

        respond patientProgram, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(PatientProgram patientProgram) {
        if (patientProgram == null) {
            render status: NOT_FOUND
            return
        }
        if (patientProgram.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond patientProgram.errors
            return
        }

        try {
            patientProgramService.save(patientProgram)
        } catch (ValidationException e) {
            respond patientProgram.errors
            return
        }

        respond patientProgram, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || patientProgramService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
