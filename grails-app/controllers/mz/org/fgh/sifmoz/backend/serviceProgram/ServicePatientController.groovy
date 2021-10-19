package mz.org.fgh.sifmoz.backend.serviceProgram

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class ServicePatientController {

    ServicePatientService servicePatientService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond servicePatientService.list(params), model:[servicePatientCount: servicePatientService.count()]
    }

    def show(Long id) {
        respond servicePatientService.get(id)
    }

    @Transactional
    def save(ServicePatient servicePatient) {
        if (servicePatient == null) {
            render status: NOT_FOUND
            return
        }
        if (servicePatient.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond servicePatient.errors
            return
        }

        try {
            servicePatientService.save(servicePatient)
        } catch (ValidationException e) {
            respond servicePatient.errors
            return
        }

        respond servicePatient, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ServicePatient servicePatient) {
        if (servicePatient == null) {
            render status: NOT_FOUND
            return
        }
        if (servicePatient.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond servicePatient.errors
            return
        }

        try {
            servicePatientService.save(servicePatient)
        } catch (ValidationException e) {
            respond servicePatient.errors
            return
        }

        respond servicePatient, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || servicePatientService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
