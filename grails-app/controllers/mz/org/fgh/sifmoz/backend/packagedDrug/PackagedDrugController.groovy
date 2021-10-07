package mz.org.fgh.sifmoz.backend.packagedDrug

import grails.rest.RestfulController
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional


class PackagedDrugController extends RestfulController{

    PackagedDrugService packagedDrugService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    PackagedDrugController(Class resource) {
        super(resource)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond packagedDrugService.list(params), model:[packagedDrugCount: packagedDrugService.count()]
    }

    def show(Long id) {
        respond packagedDrugService.get(id)
    }

    @Transactional
    def save(PackagedDrug packagedDrug) {
        if (packagedDrug == null) {
            render status: NOT_FOUND
            return
        }
        if (packagedDrug.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond packagedDrug.errors
            return
        }

        try {
            packagedDrugService.save(packagedDrug)
        } catch (ValidationException e) {
            respond packagedDrug.errors
            return
        }

        respond packagedDrug, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(PackagedDrug packagedDrug) {
        if (packagedDrug == null) {
            render status: NOT_FOUND
            return
        }
        if (packagedDrug.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond packagedDrug.errors
            return
        }

        try {
            packagedDrugService.save(packagedDrug)
        } catch (ValidationException e) {
            respond packagedDrug.errors
            return
        }

        respond packagedDrug, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || packagedDrugService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
