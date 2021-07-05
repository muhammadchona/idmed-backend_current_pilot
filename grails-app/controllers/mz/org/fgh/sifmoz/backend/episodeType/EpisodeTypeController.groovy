package mz.org.fgh.sifmoz.backend.episodeType

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class EpisodeTypeController {

    EpisodeTypeService episodeTypeService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond episodeTypeService.list(params), model:[episodeTypeCount: episodeTypeService.count()]
    }

    def show(Long id) {
        respond episodeTypeService.get(id)
    }

    @Transactional
    def save(EpisodeType episodeType) {
        if (episodeType == null) {
            render status: NOT_FOUND
            return
        }
        if (episodeType.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond episodeType.errors
            return
        }

        try {
            episodeTypeService.save(episodeType)
        } catch (ValidationException e) {
            respond episodeType.errors
            return
        }

        respond episodeType, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(EpisodeType episodeType) {
        if (episodeType == null) {
            render status: NOT_FOUND
            return
        }
        if (episodeType.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond episodeType.errors
            return
        }

        try {
            episodeTypeService.save(episodeType)
        } catch (ValidationException e) {
            respond episodeType.errors
            return
        }

        respond episodeType, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || episodeTypeService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
