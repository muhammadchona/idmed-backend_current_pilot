package mz.org.fgh.sifmoz.backend.patient

import grails.validation.ValidationException
import mz.org.fgh.sifmoz.backend.patient.vitalsigns.PatientVitalSigns

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class PatientVitalSignsController {

    PatientVitalSignsService patientVitalSignsService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond patientVitalSignsService.list(params), model:[patientVitalSignsCount: patientVitalSignsService.count()]
    }

    def show(Long id) {
        respond patientVitalSignsService.get(id)
    }

    @Transactional
    def save(PatientVitalSigns patientVitalSigns) {
        if (patientVitalSigns == null) {
            render status: NOT_FOUND
            return
        }
        if (patientVitalSigns.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond patientVitalSigns.errors
            return
        }

        try {
            patientVitalSignsService.save(patientVitalSigns)
        } catch (ValidationException e) {
            respond patientVitalSigns.errors
            return
        }

        respond patientVitalSigns, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(PatientVitalSigns patientVitalSigns) {
        if (patientVitalSigns == null) {
            render status: NOT_FOUND
            return
        }
        if (patientVitalSigns.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond patientVitalSigns.errors
            return
        }

        try {
            patientVitalSignsService.save(patientVitalSigns)
        } catch (ValidationException e) {
            respond patientVitalSigns.errors
            return
        }

        respond patientVitalSigns, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || patientVitalSignsService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
