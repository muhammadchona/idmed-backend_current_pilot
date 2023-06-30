package mz.org.fgh.sifmoz.backend.healthInformationSystem

import grails.converters.JSON
import grails.rest.RestfulController
import grails.validation.ValidationException
import mz.org.fgh.sifmoz.backend.identifierType.IdentifierType
import mz.org.fgh.sifmoz.backend.utilities.JSONSerializer

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.Transactional

class HealthInformationSystemController extends RestfulController{

    HealthInformationSystemService healthInformationSystemService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    HealthInformationSystemController() {
        super(HealthInformationSystem)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
            render JSONSerializer.setObjectListJsonResponse(healthInformationSystemService.list(params)) as JSON
    }

    def show(String id) {
            render JSONSerializer.setJsonObjectResponse(healthInformationSystemService.get(id)) as JSON
    }

    @Transactional
    def save() {
        HealthInformationSystem healthInformationSystem = new HealthInformationSystem()
        def objectJSON = request.JSON
        healthInformationSystem = objectJSON as HealthInformationSystem

        healthInformationSystem.beforeInsert()
        healthInformationSystem.validate()

        if(objectJSON.id){
            healthInformationSystem.id = UUID.fromString(objectJSON.id)
            healthInformationSystem.interoperabilityAttributes.eachWithIndex { item, index ->
                item.id = UUID.fromString(objectJSON.interoperabilityAttributes[index].id)
            }
        }
        if (healthInformationSystem.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond healthInformationSystem.errors
            return
        }

        try {
            healthInformationSystemService.save(healthInformationSystem)
        } catch (ValidationException e) {
            respond healthInformationSystem.errors
            return
        }

        respond healthInformationSystem, [status: CREATED, view:"show"]
    }

    @Transactional
    def update() {
        HealthInformationSystem healthInformationSystem
        def objectJSON = request.JSON

        println(objectJSON)

        if(objectJSON.id){
            healthInformationSystem = HealthInformationSystem.get(objectJSON.id)
            if (healthInformationSystem == null) {
                render status: NOT_FOUND
                return
            }
            healthInformationSystem.properties = objectJSON
        }

        if (healthInformationSystem.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond healthInformationSystem.errors
            return
        }

        try {
            healthInformationSystemService.save(healthInformationSystem)
        } catch (ValidationException e) {
            respond healthInformationSystem.errors
            return
        }

        respond healthInformationSystem, [status: OK, view:"show"]
    }

    @Transactional
    def delete(String id) {
        if (id == null || healthInformationSystemService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
