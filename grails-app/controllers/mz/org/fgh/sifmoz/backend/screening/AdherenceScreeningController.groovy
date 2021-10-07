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


class AdherenceScreeningController extends RestfulController{

    AdherenceScreeningService adherenceScreeningService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    AdherenceScreeningController(Class resource) {
        super(resource)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond adherenceScreeningService.list(params), model:[adherenceScreeningCount: adherenceScreeningService.count()]
    }

    def show(Long id) {
        respond adherenceScreeningService.get(id)
    }

    @Transactional
    def save(AdherenceScreening adherenceScreening) {
        if (adherenceScreening == null) {
            render status: NOT_FOUND
            return
        }
        if (adherenceScreening.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond adherenceScreening.errors
            return
        }

        try {
            adherenceScreeningService.save(adherenceScreening)
        } catch (ValidationException e) {
            respond adherenceScreening.errors
            return
        }

        respond adherenceScreening, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(AdherenceScreening adherenceScreening) {
        if (adherenceScreening == null) {
            render status: NOT_FOUND
            return
        }
        if (adherenceScreening.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond adherenceScreening.errors
            return
        }

        try {
            adherenceScreeningService.save(adherenceScreening)
        } catch (ValidationException e) {
            respond adherenceScreening.errors
            return
        }

        respond adherenceScreening, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || adherenceScreeningService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
