package mz.org.fgh.sifmoz.backend.migration.stage

import grails.rest.RestfulController
import grails.validation.ValidationException
import mz.org.fgh.sifmoz.migration.base.engine.MigrationEngine
import mz.org.fgh.sifmoz.migration.base.engine.MigrationEngineImpl

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

class MigrationController extends RestfulController{

    MigrationStageService migrationStageService
    MigrationEngineImpl migrationEngine

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    MigrationController() {
        super(MigrationStage.class)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond migrationStageService.list(params), model:[migrationStageCount: migrationStageService.count()]
    }

    def show(String id) {
        respond migrationStageService.get(id)
    }

    def startMigration() {

    }

    @Transactional
    def save(MigrationStage migrationStage) {
        if (migrationStage == null) {
            render status: NOT_FOUND
            return
        }
        if (migrationStage.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond migrationStage.errors
            return
        }

        try {
            migrationStageService.save(migrationStage)
        } catch (ValidationException e) {
            respond migrationStage.errors
            return
        }

        respond migrationStage, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(MigrationStage migrationStage) {
        if (migrationStage == null) {
            render status: NOT_FOUND
            return
        }
        if (migrationStage.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond migrationStage.errors
            return
        }

        try {
            migrationStageService.save(migrationStage)
        } catch (ValidationException e) {
            respond migrationStage.errors
            return
        }

        respond migrationStage, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || migrationStageService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}