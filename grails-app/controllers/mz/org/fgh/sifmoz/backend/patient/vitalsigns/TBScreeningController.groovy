package mz.org.fgh.sifmoz.backend.patient.vitalsigns

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class TBScreeningController {

    TBScreeningService TBScreeningService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond TBScreeningService.list(params), model:[TBScreeningCount: TBScreeningService.count()]
    }

    def show(Long id) {
        respond TBScreeningService.get(id)
    }

    @Transactional
    def save(TBScreening TBScreening) {
        if (TBScreening == null) {
            render status: NOT_FOUND
            return
        }
        if (TBScreening.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond TBScreening.errors
            return
        }

        try {
            TBScreeningService.save(TBScreening)
        } catch (ValidationException e) {
            respond TBScreening.errors
            return
        }

        respond TBScreening, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(TBScreening TBScreening) {
        if (TBScreening == null) {
            render status: NOT_FOUND
            return
        }
        if (TBScreening.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond TBScreening.errors
            return
        }

        try {
            TBScreeningService.save(TBScreening)
        } catch (ValidationException e) {
            respond TBScreening.errors
            return
        }

        respond TBScreening, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || TBScreeningService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
