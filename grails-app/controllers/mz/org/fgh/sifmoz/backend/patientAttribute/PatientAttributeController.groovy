package mz.org.fgh.sifmoz.backend.patientAttribute

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class PatientAttributeController {

    PatientAttributeService patientAttributeService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond patientAttributeService.list(params), model:[patientAttributeCount: patientAttributeService.count()]
    }

    def show(Long id) {
        respond patientAttributeService.get(id)
    }

    @Transactional
    def save(PatientAttribute patientAttribute) {
        if (patientAttribute == null) {
            render status: NOT_FOUND
            return
        }
        if (patientAttribute.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond patientAttribute.errors
            return
        }

        try {
            patientAttributeService.save(patientAttribute)
        } catch (ValidationException e) {
            respond patientAttribute.errors
            return
        }

        respond patientAttribute, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(PatientAttribute patientAttribute) {
        if (patientAttribute == null) {
            render status: NOT_FOUND
            return
        }
        if (patientAttribute.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond patientAttribute.errors
            return
        }

        try {
            patientAttributeService.save(patientAttribute)
        } catch (ValidationException e) {
            respond patientAttribute.errors
            return
        }

        respond patientAttribute, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || patientAttributeService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
