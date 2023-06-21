package mz.org.fgh.sifmoz.backend.episode

import grails.converters.JSON
import grails.rest.RestfulController
import grails.validation.ValidationException
import groovy.json.JsonSlurper
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.clinicSector.ClinicSector
import mz.org.fgh.sifmoz.backend.patientIdentifier.PatientServiceIdentifier
import mz.org.fgh.sifmoz.backend.screening.VitalSignsScreening
import mz.org.fgh.sifmoz.backend.tansreference.IPatientTransReferenceService
import mz.org.fgh.sifmoz.backend.tansreference.PatientTransReference
import mz.org.fgh.sifmoz.backend.tansreference.PatientTransReferenceType
import mz.org.fgh.sifmoz.backend.utilities.JSONSerializer
import org.grails.web.converters.marshaller.json.DomainClassMarshaller
import org.grails.web.converters.marshaller.xml.DeepDomainClassMarshaller
import org.springframework.validation.BindingResult

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.Transactional

class EpisodeController extends RestfulController {

    IEpisodeService episodeService
    IPatientTransReferenceService referenceService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    EpisodeController() {
        super(Episode)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        render JSONSerializer.setObjectListJsonResponse(episodeService.list(params)) as JSON
    }

    def show(String id) {
        render JSONSerializer.setJsonObjectResponse(episodeService.get(id)) as JSON
    }

    @Transactional
    def save() {

        Episode episode = new Episode()
        def objectJSON = request.JSON
        episode = objectJSON as Episode

        episode.beforeInsert()
        episode.validate()

        if (objectJSON.id) {
            episode.id = UUID.fromString(objectJSON.id)
        }

        if (episode.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond episode.errors
            return
        }

        try {
            episodeService.save(episode)
            if (episode.startStopReason.code.equalsIgnoreCase("TRANSFERIDO_PARA") ||
                    episode.startStopReason.code.equalsIgnoreCase("REFERIDO_DC") ||
                    episode.startStopReason.code.equalsIgnoreCase("REFERIDO_PARA") ||
                    episode.startStopReason.code.equalsIgnoreCase("VOLTOU_REFERENCIA"))
                referenceService.save(patientTransReferenceCloseMobileEpisode(episode))
        } catch (ValidationException e) {
            respond episode.errors
            return
        }

        respond episode, [status: CREATED, view: "show"]
    }

    @Transactional
    def update() {
        def objectJSON = request.JSON
        def auxEpisode = (parseTo(objectJSON.toString()) as Map) as Episode
        Episode episode = Episode.get(objectJSON.id)
        bindData(episode, auxEpisode, [exclude: ['id']])
        //updating db object
        episode.properties = auxEpisode.properties as BindingResult
        if (episode == null) {
            render status: NOT_FOUND
            return
        }
        if (episode.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond episode.errors
            return
        }

        try {
            episodeService.save(episode)
        } catch (ValidationException e) {
            respond episode.errors
            return
        }

        render JSONSerializer.setJsonObjectResponse(episode) as JSON
    }

    @Transactional
    def delete(String id) {
        if (id == null || episodeService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }

    def getByClinicId(String clinicId, int offset, int max) {
        respond episodeService.getAllByClinicId(clinicId, offset, max)
    }

    def getByIdentifierId(String identifierId, int offset, int max) {
        // respond episodeService.getAllByIndentifier(identifierId, offset, max)
        render JSONSerializer.setObjectListJsonResponse(episodeService.getAllByIndentifier(identifierId, offset, max)) as JSON
    }


    def getLastWithVisitByIndentifier(String identifierId, String cliniId) {
        // respond episodeService.getAllByIndentifier(identifierId, offset, max)
        //JSON.registerObjectMarshaller(DomainClassMarshaller)
        //render episodeService.getLastWithVisitByIndentifier(PatientServiceIdentifier.findById(identifierId), Clinic.findById(cliniId))
        render JSONSerializer.setJsonObjectResponse(episodeService.getLastWithVisitByIndentifier(PatientServiceIdentifier.findById(identifierId), Clinic.findById(cliniId))) as JSON
    }

    def getLastWithVisitByClinicSectors(String clinicSectorId) {
        // respond episodeService.getAllByIndentifier(identifierId, offset, max)
        //JSON.registerObjectMarshaller(DomainClassMarshaller)
        //render episodeService.getLastWithVisitByIndentifier(PatientServiceIdentifier.findById(identifierId), Clinic.findById(cliniId))
        render JSONSerializer.setObjectListJsonResponse(episodeService.getLastWithVisitByClinicAndClinicSector(ClinicSector.findById(clinicSectorId))) as JSON
    }

    private static def parseTo(String jsonString) {
        return new JsonSlurper().parseText(jsonString)
    }

    private static PatientTransReference patientTransReferenceCloseMobileEpisode(Episode episode) {

        def operationType = null
        def destination = episode.referralClinic
        if (episode.startStopReason.code.equalsIgnoreCase("TRANSFERIDO_PARA"))
            operationType = PatientTransReferenceType.findByCode("TRANSFERENCIA")
        else if (episode.startStopReason.code.equalsIgnoreCase("REFERIDO_DC")) {
            operationType = PatientTransReferenceType.findByCode("REFERENCIA_DC")
            destination = episode.clinicSector
        } else if (episode.startStopReason.code.equalsIgnoreCase("REFERIDO_PARA"))
            operationType = PatientTransReferenceType.findByCode("REFERENCIA_FP")
        else if (episode.startStopReason.code.equalsIgnoreCase("VOLTOU_REFERENCIA"))
            operationType = PatientTransReferenceType.findByCode("VOLTOU_DA_REFERENCIA")


        def transReference = new PatientTransReference()
        transReference.syncStatus = 'P'
        transReference.operationDate = episode.episodeDate
        transReference.creationDate = new Date()
        transReference.operationType = operationType
        transReference.origin = episode.clinic
        transReference.destination = destination
        transReference.patient = episode.patientServiceIdentifier.patient
        transReference.identifier = episode.patientServiceIdentifier

        return transReference
    }
}
