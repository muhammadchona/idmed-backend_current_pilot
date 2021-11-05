package mz.org.fgh.sifmoz.backend.prescription


import grails.converters.JSON
import grails.rest.RestfulController
import grails.validation.ValidationException
import mz.org.fgh.sifmoz.backend.doctor.DoctorService
import mz.org.fgh.sifmoz.backend.utilities.Utilities

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.Transactional

class PrescriptionController extends RestfulController{

    IPrescriptionService prescriptionService
    DoctorService doctorService
    static scaffold = true

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    PrescriptionController() {
        super(Prescription)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond prescriptionService.list(params), model:[prescriptionCount: prescriptionService.count()]
    }

    def show(String id) {

        Prescription prescription
        prescription = prescriptionService.get(id)
        prescription.setDoctor(doctorService.get(prescription.getDoctor().getId()))
        //render prescription as JSON

        //render Utilities.parseToJSON(prescription)
        JSON.use("deep") {
            respond prescription, [includes: includeFields, excludes: ['class', 'errors', 'version']]
        }
    }

    private getIncludeFields() {
        params.fields?.tokenize(',')
    }

    @Transactional
    def save(Prescription prescription) {
        if (prescription == null) {
            render status: NOT_FOUND
            return
        }
        if (prescription.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond prescription.errors
            return
        }

        try {
            if (!Utilities.stringHasValue(prescription.id)) {
                prescription.generateNextSeq()
            }
            prescriptionService.save(prescription)
        } catch (ValidationException e) {
            respond prescription.errors
            return
        }

        respond prescription, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Prescription prescription) {
        if (prescription == null) {
            render status: NOT_FOUND
            return
        }
        if (prescription.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond prescription.errors
            return
        }

        try {
            prescriptionService.save(prescription)
        } catch (ValidationException e) {
            respond prescription.errors
            return
        }

        respond prescription, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || prescriptionService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }

    def getAllByClinicId(String clinicId, int offset, int max) {
        respond prescriptionService.getAllByClinicId(clinicId, offset, max)
        /*JSON.use('deep'){
            render prescriptionService.getAllByClinicId(clinicId, offset, max) as JSON
        }*/
    }
}
