package mz.org.fgh.sifmoz.backend.migration.stage

import grails.rest.RestfulController
import grails.validation.ValidationException
import mz.org.fgh.sifmoz.backend.multithread.ExecutorThreadProvider
import mz.org.fgh.sifmoz.migration.base.engine.MigrationEngineImpl
import mz.org.fgh.sifmoz.migration.entity.patient.PatientMigrationRecord
import mz.org.fgh.sifmoz.migration.params.PatientMigrationSearchParams

import java.util.concurrent.ExecutorService

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.Transactional

class MigrationController extends RestfulController{

    MigrationStageService migrationStageService
    MigrationEngineImpl migrationEngine
    private static ExecutorService executor;

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    MigrationController() {
        super(MigrationStage.class)
        executor = ExecutorThreadProvider.getInstance().getExecutorService();
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

    private void initPatientMigrationEngine() {
        PatientMigrationSearchParams params = new PatientMigrationSearchParams()
        MigrationEngineImpl<PatientMigrationRecord> patientMigrationEngine = new MigrationEngineImpl<>(params)
        executor.execute(patientMigrationEngine)
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