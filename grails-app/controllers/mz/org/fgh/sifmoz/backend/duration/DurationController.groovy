package mz.org.fgh.sifmoz.backend.duration

import grails.rest.RestfulController
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

class DurationController extends RestfulController{

    DurationService durationService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    public DurationController () {
        super(Duration)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond durationService.list(params), model:[durationCount: durationService.count()]
    }

    def show(Long id) {
        respond durationService.get(id)
    }

    @Transactional
    def save(Duration duration) {
        if (duration == null) {
            render status: NOT_FOUND
            return
        }
        if (duration.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond duration.errors
            return
        }

        try {
            durationService.save(duration)
        } catch (ValidationException e) {
            respond duration.errors
            return
        }

        respond duration, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Duration duration) {
        if (duration == null) {
            render status: NOT_FOUND
            return
        }
        if (duration.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond duration.errors
            return
        }

        try {
            durationService.save(duration)
        } catch (ValidationException e) {
            respond duration.errors
            return
        }

        respond duration, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || durationService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
