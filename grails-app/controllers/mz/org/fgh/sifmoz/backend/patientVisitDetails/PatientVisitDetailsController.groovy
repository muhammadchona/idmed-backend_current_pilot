package mz.org.fgh.sifmoz.backend.patientVisitDetails

import grails.converters.JSON
import grails.rest.RestfulController
import grails.validation.ValidationException
import mz.org.fgh.sifmoz.backend.patientVisit.IPatientVisitService
import mz.org.fgh.sifmoz.backend.patientVisit.PatientVisit
import mz.org.fgh.sifmoz.backend.prescription.IPrescriptionService
import mz.org.fgh.sifmoz.backend.prescription.Prescription
import mz.org.fgh.sifmoz.backend.utilities.JSONSerializer

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.Transactional

class PatientVisitDetailsController extends RestfulController{

    IPatientVisitDetailsService patientVisitDetailsService
    IPatientVisitService patientVisitService
    IPrescriptionService prescriptionService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    PatientVisitDetailsController() {
        super(PatientVisitDetails)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        render JSONSerializer.setObjectListJsonResponse(patientVisitDetailsService.list(params)) as JSON
    }

    def show(String id) {
        render JSONSerializer.setJsonObjectResponse(patientVisitDetailsService.get(id)) as JSON
    }

    @Transactional
    def save(PatientVisitDetails patientVisitDetails) {
        if (patientVisitDetails == null) {
            render status: NOT_FOUND
            return
        }
        if (patientVisitDetails.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond patientVisitDetails.errors
            return
        }

        try {
            determinePrescriptionPatientType(patientVisitDetails)
            patientVisitDetailsService.save(patientVisitDetails)
        } catch (ValidationException e) {
            respond patientVisitDetails.errors
            return
        }

        respond patientVisitDetails, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(PatientVisitDetails patientVisitDetails) {
        if (patientVisitDetails == null) {
            render status: NOT_FOUND
            return
        }
        if (patientVisitDetails.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond patientVisitDetails.errors
            return
        }

        try {
            patientVisitDetailsService.save(patientVisitDetails)
        } catch (ValidationException e) {
            respond patientVisitDetails.errors
            return
        }

        respond patientVisitDetails, [status: OK, view:"show"]
    }

    @Transactional
    def delete(String id) {
        if (id == null || patientVisitDetailsService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }

    def getAllByClinicId(String clinicId, int offset, int max) {
        render JSONSerializer.setObjectListJsonResponse(patientVisitDetailsService.getAllByClinicId(clinicId, offset, max)) as JSON
    }

    def getAllByEpisodeId(String episodeId, int offset, int max) {
        render JSONSerializer.setObjectListJsonResponse(patientVisitDetailsService.getAllByEpisodeId(episodeId, offset, max)) as JSON
    }

    def getAllofPrecription(String prescriptionId) {
        render JSONSerializer.setObjectListJsonResponse(PatientVisitDetails.findAllByPrescription(Prescription.findById(prescriptionId))) as JSON
    }

    def getLastByEpisodeId(String episodeId) {
        PatientVisitDetails pvd = patientVisitDetailsService.getLastByEpisodeId(episodeId)
        if (pvd != null) {
            render JSONSerializer.setJsonObjectResponse(pvd) as JSON
        } else render status: NO_CONTENT
    }

    void determinePrescriptionPatientType(PatientVisitDetails patientVisitDetails) {
        PatientVisit patientVisit = patientVisitService.getLastVisitOfPatient(patientVisitDetails.getPatientVisit().getPatient().id)

        if (patientVisitDetails.episode.startStopReason.isManutencao() || patientVisitDetails.episode.startStopReason.isTransferido()) {
            patientVisitDetails.prescription.setPatientType(patientVisitDetails.episode.startStopReason.code)
        }  else if (patientVisitDetails.prescription.patientType == "Alterar") {
            patientVisitDetails.prescription.setPatientType(Prescription.PATIENT_TYPE_ALTERACAO)
        } else if (patientVisitDetails.episode.startStopReason.isNew() && patientVisit == null) {
            patientVisitDetails.prescription.setPatientType(Prescription.PATIENT_TYPE_NOVO)
        } else if (patientVisitDetails.episode.startStopReason.isNew() && patientVisit != null) {
            patientVisitDetails.prescription.setPatientType(Prescription.PATIENT_TYPE_MANUTENCAO)
        } else {
            patientVisitDetails.prescription.setPatientType(Prescription.PATIENT_TYPE_MANUTENCAO)
        }
        prescriptionService.save(patientVisitDetails.prescription)
    }

    def mySave(PatientVisitDetails patientVisit) {
        this.save(patientVisit)
        render patientVisit
    }
}
