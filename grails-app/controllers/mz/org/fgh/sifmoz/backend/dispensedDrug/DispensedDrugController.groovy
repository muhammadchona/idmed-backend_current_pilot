package mz.org.fgh.sifmoz.backend.dispensedDrug

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class DispensedDrugController {

    DispensedDrugService dispensedDrugService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond dispensedDrugService.list(params), model:[dispensedDrugCount: dispensedDrugService.count()]
    }

    def show(Long id) {
        respond dispensedDrugService.get(id)
    }

    @Transactional
    def save(DispensedDrug dispensedDrug) {
        if (dispensedDrug == null) {
            render status: NOT_FOUND
            return
        }
        if (dispensedDrug.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond dispensedDrug.errors
            return
        }

        try {
            dispensedDrugService.save(dispensedDrug)
        } catch (ValidationException e) {
            respond dispensedDrug.errors
            return
        }

        respond dispensedDrug, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(DispensedDrug dispensedDrug) {
        if (dispensedDrug == null) {
            render status: NOT_FOUND
            return
        }
        if (dispensedDrug.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond dispensedDrug.errors
            return
        }

        try {
            dispensedDrugService.save(dispensedDrug)
        } catch (ValidationException e) {
            respond dispensedDrug.errors
            return
        }

        respond dispensedDrug, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || dispensedDrugService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
