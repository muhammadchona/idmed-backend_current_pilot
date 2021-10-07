package mz.org.fgh.sifmoz.backend.stockcenter

import grails.rest.RestfulController
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional


class StockCenterController extends RestfulController{

    StockCenterService stockCenterService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    StockCenterController(Class resource) {
        super(resource)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond stockCenterService.list(params), model:[stockCenterCount: stockCenterService.count()]
    }

    def show(Long id) {
        respond stockCenterService.get(id)
    }

    @Transactional
    def save(StockCenter stockCenter) {
        if (stockCenter == null) {
            render status: NOT_FOUND
            return
        }
        if (stockCenter.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond stockCenter.errors
            return
        }

        try {
            stockCenterService.save(stockCenter)
        } catch (ValidationException e) {
            respond stockCenter.errors
            return
        }

        respond stockCenter, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(StockCenter stockCenter) {
        if (stockCenter == null) {
            render status: NOT_FOUND
            return
        }
        if (stockCenter.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond stockCenter.errors
            return
        }

        try {
            stockCenterService.save(stockCenter)
        } catch (ValidationException e) {
            respond stockCenter.errors
            return
        }

        respond stockCenter, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || stockCenterService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
