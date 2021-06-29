package mz.org.fgh.sifmoz.backend.program

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class ProgramAttributeTypeController {

    ProgramAttributeTypeService programAttributeTypeService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond programAttributeTypeService.list(params), model:[programAttributeTypeCount: programAttributeTypeService.count()]
    }

    def show(Long id) {
        respond programAttributeTypeService.get(id)
    }

    @Transactional
    def save(ProgramAttributeType programAttributeType) {
        if (programAttributeType == null) {
            render status: NOT_FOUND
            return
        }
        if (programAttributeType.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond programAttributeType.errors
            return
        }

        try {
            programAttributeTypeService.save(programAttributeType)
        } catch (ValidationException e) {
            respond programAttributeType.errors
            return
        }

        respond programAttributeType, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ProgramAttributeType programAttributeType) {
        if (programAttributeType == null) {
            render status: NOT_FOUND
            return
        }
        if (programAttributeType.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond programAttributeType.errors
            return
        }

        try {
            programAttributeTypeService.save(programAttributeType)
        } catch (ValidationException e) {
            respond programAttributeType.errors
            return
        }

        respond programAttributeType, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || programAttributeTypeService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
