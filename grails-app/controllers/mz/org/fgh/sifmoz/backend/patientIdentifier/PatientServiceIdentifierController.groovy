package mz.org.fgh.sifmoz.backend.patientIdentifier

import grails.converters.JSON
import grails.rest.RestfulController
import grails.validation.ValidationException
import mz.org.fgh.sifmoz.backend.utilities.JSONSerializer

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.Transactional

class PatientServiceIdentifierController extends RestfulController{

    IPatientServiceIdentifierService patientServiceIdentifierService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    PatientServiceIdentifierController () {
        super(PatientServiceIdentifier)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        render JSONSerializer.setObjectListJsonResponse(patientServiceIdentifierService.list(params)) as JSON
    }

    def show(String id) {
        render JSONSerializer.setJsonObjectResponse(patientServiceIdentifierService.get(id)) as JSON
    }

    @Transactional
    def save() {
        PatientServiceIdentifier patientServiceIdentifier = new PatientServiceIdentifier()
        def objectJSON = request.JSON
        patientServiceIdentifier = objectJSON as PatientServiceIdentifier

        patientServiceIdentifier.beforeInsert()
        patientServiceIdentifier.validate()

        if(objectJSON.id){
            patientServiceIdentifier.id = UUID.fromString(objectJSON.id)
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

    def getByClinicId(String clinicId, int offset, int max) {
        respond patientServiceIdentifierService.getAllByClinicId(clinicId, offset, max)
    }

    def getByPatientId(String patientId, int offset, int max) {
        def result = patientServiceIdentifierService.getAllByPatientId(patientId, offset, max)
        render JSONSerializer.setObjectListJsonResponse(result) as JSON
    }

//    def getByServiceId(String serviceId) {
////        for (i in patientServiceIdentifierService.getAllByServiceId(serviceId)) {
////            System.out.println(i as JSON)
////        }
//
//        def result = patientServiceIdentifierService.getAllByPatientId(serviceId)
//
//        System.out.println(result.size())
//
//        render JSONSerializer.setObjectListJsonResponse(result) as JSON
//    }
}
