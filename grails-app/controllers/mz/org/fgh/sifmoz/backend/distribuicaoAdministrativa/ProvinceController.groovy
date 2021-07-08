package mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class ProvinceController {

    ProvinceService provinceService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond provinceService.list(params), model:[provinceCount: provinceService.count()]
    }

    def show(Long id) {
        respond provinceService.get(id)
    }

    @Transactional
    def save(Province province) {
        if (province == null) {
            render status: NOT_FOUND
            return
        }
        if (province.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond province.errors
            return
        }

        try {
            provinceService.save(province)
        } catch (ValidationException e) {
            respond province.errors
            return
        }

        respond province, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Province province) {
        if (province == null) {
            render status: NOT_FOUND
            return
        }
        if (province.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond province.errors
            return
        }

        try {
            provinceService.save(province)
        } catch (ValidationException e) {
            respond province.errors
            return
        }

        respond province, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || provinceService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
