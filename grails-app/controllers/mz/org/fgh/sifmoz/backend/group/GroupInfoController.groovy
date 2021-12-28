package mz.org.fgh.sifmoz.backend.group

import grails.converters.JSON
import grails.validation.ValidationException
import mz.org.fgh.sifmoz.backend.utilities.JSONSerializer

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class GroupInfoController {

    GroupInfoService groupInfoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        render JSONSerializer.setObjectListJsonResponse(groupInfoService.list(params)) as JSON
    }

    def show(Long id) {
        render JSONSerializer.setJsonObjectResponse(groupInfoService.get(id)) as JSON
    }

    @Transactional
    def save(GroupInfo groupInfo) {
        if (groupInfo == null) {
            render status: NOT_FOUND
            return
        }
        if (groupInfo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond groupInfo.errors
            return
        }

        try {
            groupInfoService.save(groupInfo)
        } catch (ValidationException e) {
            respond groupInfo.errors
            return
        }

        respond groupInfo, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(GroupInfo groupInfo) {
        if (groupInfo == null) {
            render status: NOT_FOUND
            return
        }
        if (groupInfo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond groupInfo.errors
            return
        }

        try {
            groupInfoService.save(groupInfo)
        } catch (ValidationException e) {
            respond groupInfo.errors
            return
        }

        respond groupInfo, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || groupInfoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
