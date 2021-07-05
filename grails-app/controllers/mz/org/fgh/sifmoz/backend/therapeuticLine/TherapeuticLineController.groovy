package mz.org.fgh.sifmoz.backend.therapeuticLine

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class TherapeuticLineController {

    TherapeuticLineService therapeuticLineService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond therapeuticLineService.list(params), model:[therapeuticLineCount: therapeuticLineService.count()]
    }

    def show(Long id) {
        respond therapeuticLineService.get(id)
    }

    @Transactional
    def save(TherapeuticLine therapeuticLine) {
        if (therapeuticLine == null) {
            render status: NOT_FOUND
            return
        }
        if (therapeuticLine.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond therapeuticLine.errors
            return
        }

        try {
            therapeuticLineService.save(therapeuticLine)
        } catch (ValidationException e) {
            respond therapeuticLine.errors
            return
        }

        respond therapeuticLine, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(TherapeuticLine therapeuticLine) {
        if (therapeuticLine == null) {
            render status: NOT_FOUND
            return
        }
        if (therapeuticLine.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond therapeuticLine.errors
            return
        }

        try {
            therapeuticLineService.save(therapeuticLine)
        } catch (ValidationException e) {
            respond therapeuticLine.errors
            return
        }

        respond therapeuticLine, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || therapeuticLineService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
