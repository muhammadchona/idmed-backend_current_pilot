package mz.org.fgh.sifmoz.backend.patientVisit

import grails.converters.JSON
import grails.rest.RestfulController
import grails.validation.ValidationException

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.Transactional

class PatientVisitController extends RestfulController{

    IPatientVisitService patientVisitService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    PatientVisitController() {
        super(PatientVisit)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond patientVisitService.list(params), model:[visitCount: patientVisitService.count()]
    }

    def show(Long id) {
        respond patientVisitService.get(id)
    }

    @Transactional
    def save(PatientVisit visit) {
        if (visit == null) {
            render status: NOT_FOUND
            return
        }
        if (visit.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond visit.errors
            return
        }

        try {
            patientVisitService.save(visit)
        } catch (ValidationException e) {
            respond visit.errors
            return
        }

        respond visit, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(PatientVisit visit) {
        if (visit == null) {
            render status: NOT_FOUND
            return
        }
        if (visit.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond visit.errors
            return
        }

        try {
            patientVisitService.save(visit)
        } catch (ValidationException e) {
            respond visit.errors
            return
        }

        respond visit, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || patientVisitService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }

    def getAllByClinicId(String clinicId, int offset, int max) {
        respond patientVisitService.getAllByClinicId(clinicId, offset, max)
    }

    def getByPatientId(String patientId) {
        JSON.use('deep') {
            render patientVisitService.getAllByPatientId(patientId) as JSON
        }
    }
}
