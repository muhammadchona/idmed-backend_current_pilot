package mz.org.fgh.sifmoz.backend.healthInformationSystem

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class SystemConfigsController {

    ISystemConfigsService systemConfigsService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond systemConfigsService.list(params), model:[systemConfigsCount: systemConfigsService.count()]
    }

    def show(Long id) {
        respond systemConfigsService.get(id)
    }

    @Transactional
    def save(SystemConfigs systemConfigs) {
        systemConfigs.beforeInsert()
        if (systemConfigs == null) {
            render status: NOT_FOUND
            return
        }
        if (systemConfigs.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond systemConfigs.errors
            return
        }

        try {
            systemConfigsService.save(systemConfigs)
        } catch (ValidationException e) {
            respond systemConfigs.errors
            return
        }

        respond systemConfigs, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(SystemConfigs systemConfigs) {
        if (systemConfigs == null) {
            render status: NOT_FOUND
            return
        }
        if (systemConfigs.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond systemConfigs.errors
            return
        }

        try {
            systemConfigsService.save(systemConfigs)
        } catch (ValidationException e) {
            respond systemConfigs.errors
            return
        }

        respond systemConfigs, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || systemConfigsService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
