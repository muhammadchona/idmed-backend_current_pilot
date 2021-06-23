package mz.org.fgh.sifmoz.backend.patient

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class PatientController {

    PatientService patientService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond patientService.list(params), model:[patientCount: patientService.count()]
    }

    def show(Long id) {
        respond patientService.get(id)
    }

    @Transactional
    def save(Patient patient) {
        if (patient == null) {
            render status: NOT_FOUND
            return
        }
        if (patient.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond patient.errors
            return
        }

        try {
            patientService.save(patient)
        } catch (ValidationException e) {
            respond patient.errors
            return
        }

        respond patient, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Patient patient) {
        if (patient == null) {
            render status: NOT_FOUND
            return
        }
        if (patient.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond patient.errors
            return
        }

        try {
            patientService.save(patient)
        } catch (ValidationException e) {
            respond patient.errors
            return
        }

        respond patient, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || patientService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
