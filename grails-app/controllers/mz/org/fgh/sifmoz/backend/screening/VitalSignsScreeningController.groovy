package mz.org.fgh.sifmoz.backend.screening

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class VitalSignsScreeningController {

    VitalSignsScreeningService vitalSignsScreeningService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond vitalSignsScreeningService.list(params), model:[vitalSignsScreeningCount: vitalSignsScreeningService.count()]
    }

    def show(Long id) {
        respond vitalSignsScreeningService.get(id)
    }

    @Transactional
    def save(VitalSignsScreening vitalSignsScreening) {
        if (vitalSignsScreening == null) {
            render status: NOT_FOUND
            return
        }
        if (vitalSignsScreening.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond vitalSignsScreening.errors
            return
        }

        try {
            vitalSignsScreeningService.save(vitalSignsScreening)
        } catch (ValidationException e) {
            respond vitalSignsScreening.errors
            return
        }

        respond vitalSignsScreening, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(VitalSignsScreening vitalSignsScreening) {
        if (vitalSignsScreening == null) {
            render status: NOT_FOUND
            return
        }
        if (vitalSignsScreening.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond vitalSignsScreening.errors
            return
        }

        try {
            vitalSignsScreeningService.save(vitalSignsScreening)
        } catch (ValidationException e) {
            respond vitalSignsScreening.errors
            return
        }

        respond vitalSignsScreening, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || vitalSignsScreeningService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
