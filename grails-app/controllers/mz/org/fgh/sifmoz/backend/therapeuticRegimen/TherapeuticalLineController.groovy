package mz.org.fgh.sifmoz.backend.therapeuticRegimen

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class TherapeuticalLineController {

    TherapeuticalLineService therapeuticalLineService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond therapeuticalLineService.list(params), model:[therapeuticalLineCount: therapeuticalLineService.count()]
    }

    def show(Long id) {
        respond therapeuticalLineService.get(id)
    }

    @Transactional
    def save(TherapeuticalLine therapeuticalLine) {
        if (therapeuticalLine == null) {
            render status: NOT_FOUND
            return
        }
        if (therapeuticalLine.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond therapeuticalLine.errors
            return
        }

        try {
            therapeuticalLineService.save(therapeuticalLine)
        } catch (ValidationException e) {
            respond therapeuticalLine.errors
            return
        }

        respond therapeuticalLine, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(TherapeuticalLine therapeuticalLine) {
        if (therapeuticalLine == null) {
            render status: NOT_FOUND
            return
        }
        if (therapeuticalLine.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond therapeuticalLine.errors
            return
        }

        try {
            therapeuticalLineService.save(therapeuticalLine)
        } catch (ValidationException e) {
            respond therapeuticalLine.errors
            return
        }

        respond therapeuticalLine, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || therapeuticalLineService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
