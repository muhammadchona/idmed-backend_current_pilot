package mz.org.fgh.sifmoz.backend.groupType

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class GroupTypeController {

    GroupTypeService groupTypeService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond groupTypeService.list(params), model:[groupTypeCount: groupTypeService.count()]
    }

    def show(Long id) {
        respond groupTypeService.get(id)
    }

    @Transactional
    def save(GroupType groupType) {
        if (groupType == null) {
            render status: NOT_FOUND
            return
        }
        if (groupType.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond groupType.errors
            return
        }

        try {
            groupTypeService.save(groupType)
        } catch (ValidationException e) {
            respond groupType.errors
            return
        }

        respond groupType, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(GroupType groupType) {
        if (groupType == null) {
            render status: NOT_FOUND
            return
        }
        if (groupType.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond groupType.errors
            return
        }

        try {
            groupTypeService.save(groupType)
        } catch (ValidationException e) {
            respond groupType.errors
            return
        }

        respond groupType, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || groupTypeService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
