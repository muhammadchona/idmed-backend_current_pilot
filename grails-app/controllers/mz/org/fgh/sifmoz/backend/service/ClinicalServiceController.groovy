package mz.org.fgh.sifmoz.backend.service

import grails.converters.JSON
import grails.rest.RestfulController
import grails.validation.ValidationException
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.patientVisit.PatientVisit
import mz.org.fgh.sifmoz.backend.serviceattribute.ClinicalServiceAttribute
import mz.org.fgh.sifmoz.backend.serviceattribute.ClinicalServiceAttributeService
import mz.org.fgh.sifmoz.backend.therapeuticRegimen.TherapeuticRegimen
import mz.org.fgh.sifmoz.backend.therapeuticRegimen.TherapeuticRegimenService
import mz.org.fgh.sifmoz.backend.utilities.JSONSerializer

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.Transactional

class ClinicalServiceController extends RestfulController{

    ClinicalServiceService clinicalServiceService
    ClinicalServiceAttributeService clinicalServiceAttributeService
    TherapeuticRegimenService therapeuticRegimenService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    ClinicalServiceController () {
        super(ClinicalService)
    }
    def index(Integer max) {

            render JSONSerializer.setObjectListJsonResponse(clinicalServiceService.list(params)) as JSON
    }

    def show(String id) {
        render JSONSerializer.setJsonObjectResponse(clinicalServiceService.get(id)) as JSON
    }

    @Transactional
    def save() {
        ClinicalService clinicalService = new ClinicalService()
        def objectJSON = request.JSON
        clinicalService = objectJSON as ClinicalService

        clinicalService.beforeInsert()
        clinicalService.validate()

        if(objectJSON.id){
            clinicalService.id = UUID.fromString(objectJSON.id)
            clinicalService.attributes.eachWithIndex { item, index ->
                item.id = UUID.fromString(objectJSON.attributes[index].id)
            }
          //  clinicalService.clinicSectors.eachWithIndex { item, index ->
          //     item.id = objectJSON.clinicSectors[index].id
          //  }
        }
        if (clinicalService.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond clinicalService.errors
            return
        }

        try {
            clinicalService.therapeuticRegimens = new ArrayList<>()
            if(objectJSON.therapeuticRegimens != null) {
                for (int i=0;i < objectJSON.therapeuticRegimens.length();i++) {
                    TherapeuticRegimen therapeuticRegimen = TherapeuticRegimen.get(objectJSON.therapeuticRegimens[i].id)
                    clinicalService.therapeuticRegimens.add(therapeuticRegimen)
                }
                clinicalService.therapeuticRegimens.each {item ->
                    item.clinicalService = clinicalService
                    therapeuticRegimenService.save(item)

                }
            }
            clinicalServiceService.save(clinicalService)
        } catch (ValidationException e) {
            respond clinicalService.errors
            return
        }

        respond clinicalService, [status: CREATED, view:"show"]
    }

    @Transactional
    def update() {
        def objectJSON = request.JSON
        ClinicalService clinicalService = ClinicalService.get(objectJSON.id)
        boolean clinicalServiceInitialState = clinicalService.active
        if(objectJSON.active == true && clinicalServiceInitialState == true) {
            clinicalService.attributes.each { item ->
                clinicalServiceAttributeService.delete(item.id)
            }
        }

        clinicalService.properties = objectJSON

        if(objectJSON.id){
          //  clinicalService = ClinicalService.get(objectJSON.id)
            clinicalService.therapeuticRegimens = new ArrayList<>()
            if(objectJSON.active == true && clinicalServiceInitialState == true) {
                clinicalService.attributes.eachWithIndex { item, index ->
                    item.id = UUID.fromString(objectJSON.attributes[index].id)
                    //   item.clinicalServiceAttributeType.id = UUID.fromString(objectJSON.attributes[index].clinicalServiceAttributeType.id)
                }
            }
         //   clinicalService.clinicSectors.eachWithIndex { item, index ->
         //       item.id = objectJSON.clinicSectors[index].id
          //  }
            if(objectJSON.therapeuticRegimens != null) {
                for (int i = 0; i < objectJSON.therapeuticRegimens.length(); i++) {
                    TherapeuticRegimen therapeuticRegimen = TherapeuticRegimen.get(objectJSON.therapeuticRegimens[i].id)
                    clinicalService.therapeuticRegimens.add(therapeuticRegimen)
                }
            }
            if (clinicalService == null) {
                render status: NOT_FOUND
                return
            }

        }

        if (clinicalService.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond clinicalService.errors
            return
        }

        try {
            List<TherapeuticRegimen> regimensByClinicalServiceDb = TherapeuticRegimen.findAllByClinicalService(clinicalService)
            regimensByClinicalServiceDb.each {item ->
                if (!clinicalService.therapeuticRegimens.contains(item)) {
                    item.clinicalService = null
                    therapeuticRegimenService.save(item)
                }
            }
            clinicalService.therapeuticRegimens.each {item ->
                if (!regimensByClinicalServiceDb.contains(item)) {
                    TherapeuticRegimen therapeuticRegimenDB= item.get(item.id)
                    therapeuticRegimenDB.clinicalService = clinicalService
                    therapeuticRegimenService.save(therapeuticRegimenDB)
                }
            }
            clinicalServiceService.save(clinicalService)
        } catch (ValidationException e) {
            respond clinicalService.errors
            return
        }

        respond clinicalService, [status: OK, view:"show"]

    }

    @Transactional
    def delete(Long id) {
        if (id == null || clinicalServiceService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
