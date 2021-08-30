package mz.org.fgh.sifmoz.backend.service

import grails.rest.RestfulController
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.Transactional

class ServiceController extends RestfulController{

    ServiceService service

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    ServiceController() {
        super(Service)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond service.list(params), model:[programCount: service.count()]
    }

    def show(Long id) {
        respond service.get(id)
    }

    @Transactional
    def save(Service program) {
        if (program == null) {
            render status: NOT_FOUND
            return
        }
        if (program.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond program.errors
            return
        }

        try {
            service.save(program)
        } catch (ValidationException e) {
            respond program.errors
            return
        }

        respond program, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Service program) {
        if (program == null) {
            render status: NOT_FOUND
            return
        }
        if (program.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond program.errors
            return
        }

        try {
            service.save(program)
        } catch (ValidationException e) {
            respond program.errors
            return
        }

        respond program, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || service.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
