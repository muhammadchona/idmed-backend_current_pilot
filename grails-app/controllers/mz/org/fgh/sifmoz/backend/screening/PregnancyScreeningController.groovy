package mz.org.fgh.sifmoz.backend.screening

import grails.rest.RestfulController
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

class PregnancyScreeningController extends RestfulController{

    PregnancyScreeningService pregnancyScreeningService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    PregnancyScreeningController() {
        super(PregnancyScreening)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond pregnancyScreeningService.list(params), model:[pregnancyScreeningCount: pregnancyScreeningService.count()]
    }

    def show(Long id) {
        respond pregnancyScreeningService.get(id)
    }

    @Transactional
    def save(PregnancyScreening pregnancyScreening) {
        if (pregnancyScreening == null) {
            render status: NOT_FOUND
            return
        }
        if (pregnancyScreening.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond pregnancyScreening.errors
            return
        }

        try {
            pregnancyScreeningService.save(pregnancyScreening)
        } catch (ValidationException e) {
            respond pregnancyScreening.errors
            return
        }

        respond pregnancyScreening, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(PregnancyScreening pregnancyScreening) {
        if (pregnancyScreening == null) {
            render status: NOT_FOUND
            return
        }
        if (pregnancyScreening.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond pregnancyScreening.errors
            return
        }

        try {
            pregnancyScreeningService.save(pregnancyScreening)
        } catch (ValidationException e) {
            respond pregnancyScreening.errors
            return
        }

        respond pregnancyScreening, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || pregnancyScreeningService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
