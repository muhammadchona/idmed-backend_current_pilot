package mz.org.fgh.sifmoz.backend.patient

import grails.converters.JSON
import grails.rest.RestfulController
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.Transactional

class PatientController extends RestfulController{

    IPatientService patientService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    PatientController() {
        super(Patient)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond patientService.list(params), model:[patientCount: patientService.count()]
    }

    def show(String id) {
        JSON.use('deep'){
            render patientService.get(id) as JSON
        }
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

    def search(Patient patient) {
        JSON.use('deep'){
            render patientService.search(patient) as JSON
        }
    }

    def getByClinicId(String clinicId, int offset, int max) {
        respond patientService.getAllByClinicId(clinicId, offset, max)
    }
}
