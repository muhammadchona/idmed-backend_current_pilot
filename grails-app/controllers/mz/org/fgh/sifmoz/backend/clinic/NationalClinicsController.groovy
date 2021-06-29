package mz.org.fgh.sifmoz.backend.clinic

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class NationalClinicsController {

    NationalClinicsService nationalClinicsService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond nationalClinicsService.list(params), model:[nationalClinicsCount: nationalClinicsService.count()]
    }

    def show(Long id) {
        respond nationalClinicsService.get(id)
    }

    @Transactional
    def save(NationalClinics nationalClinics) {
        if (nationalClinics == null) {
            render status: NOT_FOUND
            return
        }
        if (nationalClinics.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond nationalClinics.errors
            return
        }

        try {
            nationalClinicsService.save(nationalClinics)
        } catch (ValidationException e) {
            respond nationalClinics.errors
            return
        }

        respond nationalClinics, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(NationalClinics nationalClinics) {
        if (nationalClinics == null) {
            render status: NOT_FOUND
            return
        }
        if (nationalClinics.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond nationalClinics.errors
            return
        }

        try {
            nationalClinicsService.save(nationalClinics)
        } catch (ValidationException e) {
            respond nationalClinics.errors
            return
        }

        respond nationalClinics, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || nationalClinicsService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
