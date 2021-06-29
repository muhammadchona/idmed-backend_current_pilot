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
class TherapeuticRegimenController {

    TherapeuticRegimenService therapeuticRegimenService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond therapeuticRegimenService.list(params), model:[therapeuticRegimenCount: therapeuticRegimenService.count()]
    }

    def show(Long id) {
        respond therapeuticRegimenService.get(id)
    }

    @Transactional
    def save(TherapeuticRegimen therapeuticRegimen) {
        if (therapeuticRegimen == null) {
            render status: NOT_FOUND
            return
        }
        if (therapeuticRegimen.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond therapeuticRegimen.errors
            return
        }

        try {
            therapeuticRegimenService.save(therapeuticRegimen)
        } catch (ValidationException e) {
            respond therapeuticRegimen.errors
            return
        }

        respond therapeuticRegimen, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(TherapeuticRegimen therapeuticRegimen) {
        if (therapeuticRegimen == null) {
            render status: NOT_FOUND
            return
        }
        if (therapeuticRegimen.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond therapeuticRegimen.errors
            return
        }

        try {
            therapeuticRegimenService.save(therapeuticRegimen)
        } catch (ValidationException e) {
            respond therapeuticRegimen.errors
            return
        }

        respond therapeuticRegimen, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || therapeuticRegimenService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
