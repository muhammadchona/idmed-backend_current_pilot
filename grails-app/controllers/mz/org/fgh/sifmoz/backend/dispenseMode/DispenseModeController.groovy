package mz.org.fgh.sifmoz.backend.dispenseMode

import grails.rest.RestfulController
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

class DispenseModeController extends RestfulController{

    DispenseModeService dispenseModeService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    DispenseModeController() {
        super(DispenseMode)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond dispenseModeService.list(params), model:[dispenseModeCount: dispenseModeService.count()]
    }

    def show(Long id) {
        respond dispenseModeService.get(id)
    }

    @Transactional
    def save(DispenseMode dispenseMode) {
        if (dispenseMode == null) {
            render status: NOT_FOUND
            return
        }
        if (dispenseMode.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond dispenseMode.errors
            return
        }

        try {
            dispenseModeService.save(dispenseMode)
        } catch (ValidationException e) {
            respond dispenseMode.errors
            return
        }

        respond dispenseMode, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(DispenseMode dispenseMode) {
        if (dispenseMode == null) {
            render status: NOT_FOUND
            return
        }
        if (dispenseMode.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond dispenseMode.errors
            return
        }

        try {
            dispenseModeService.save(dispenseMode)
        } catch (ValidationException e) {
            respond dispenseMode.errors
            return
        }

        respond dispenseMode, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || dispenseModeService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
