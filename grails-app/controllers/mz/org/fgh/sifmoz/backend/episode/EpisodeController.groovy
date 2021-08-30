package mz.org.fgh.sifmoz.backend.episode

import grails.rest.RestfulController
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

class EpisodeController extends RestfulController{

    EpisodeService episodeService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    EpisodeController() {
        super(Episode)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond episodeService.list(params), model:[episodeCount: episodeService.count()]
    }

    def show(Long id) {
        respond episodeService.get(id)
    }

    @Transactional
    def save(Episode episode) {
        if (episode == null) {
            render status: NOT_FOUND
            return
        }
        if (episode.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond episode.errors
            return
        }

        try {
            episodeService.save(episode)
        } catch (ValidationException e) {
            respond episode.errors
            return
        }

        respond episode, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Episode episode) {
        if (episode == null) {
            render status: NOT_FOUND
            return
        }
        if (episode.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond episode.errors
            return
        }

        try {
            episodeService.save(episode)
        } catch (ValidationException e) {
            respond episode.errors
            return
        }

        respond episode, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || episodeService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
