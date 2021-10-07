package mz.org.fgh.sifmoz.backend.drug

import grails.rest.RestfulController
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

class DrugController extends RestfulController{

    DrugService drugService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    DrugController() {
        super(Drug)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond drugService.list(params), model:[drugCount: drugService.count()]
    }

    def show(Long id) {
        respond drugService.get(id)
    }

    @Transactional
    def save(Drug drug) {
        if (drug == null) {
            render status: NOT_FOUND
            return
        }
        if (drug.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond drug.errors
            return
        }

        try {
            drugService.save(drug)
        } catch (ValidationException e) {
            respond drug.errors
            return
        }

        respond drug, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Drug drug) {
        if (drug == null) {
            render status: NOT_FOUND
            return
        }
        if (drug.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond drug.errors
            return
        }

        try {
            drugService.save(drug)
        } catch (ValidationException e) {
            respond drug.errors
            return
        }

        respond drug, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || drugService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
