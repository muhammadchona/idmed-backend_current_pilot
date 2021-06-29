package mz.org.fgh.sifmoz.backend.appointment

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class VisitController {

    VisitService visitService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond visitService.list(params), model:[visitCount: visitService.count()]
    }

    def show(Long id) {
        respond visitService.get(id)
    }

    @Transactional
    def save(Visit visit) {
        if (visit == null) {
            render status: NOT_FOUND
            return
        }
        if (visit.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond visit.errors
            return
        }

        try {
            visitService.save(visit)
        } catch (ValidationException e) {
            respond visit.errors
            return
        }

        respond visit, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Visit visit) {
        if (visit == null) {
            render status: NOT_FOUND
            return
        }
        if (visit.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond visit.errors
            return
        }

        try {
            visitService.save(visit)
        } catch (ValidationException e) {
            respond visit.errors
            return
        }

        respond visit, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || visitService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
