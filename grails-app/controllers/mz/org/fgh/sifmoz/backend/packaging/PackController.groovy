package mz.org.fgh.sifmoz.backend.packaging

import grails.rest.RestfulController
import grails.validation.ValidationException
import mz.org.fgh.sifmoz.backend.packagedDrug.PackagedDrug
import mz.org.fgh.sifmoz.backend.utilities.Utilities

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.Transactional

class PackController extends RestfulController{

    IPackService packService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    PackController() {
        super(Pack)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond packService.list(params), model:[packCount: packService.count()]
    }

    def show(Long id) {
        respond packService.get(id)
    }

    @Transactional
    def save(Pack pack) {
        if (pack == null) {
            render status: NOT_FOUND
            return
        }
        if (pack.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond pack.errors
            return
        }

        try {
            packService.save(pack)
        } catch (ValidationException e) {
            respond pack.errors
            return
        }

        respond pack, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Pack pack) {
        if (pack == null) {
            render status: NOT_FOUND
            return
        }
        if (pack.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond pack.errors
            return
        }

        try {
            if (Utilities.stringHasValue(pack.id)) {
                PackagedDrug.deleteAll(pack.packagedDrugs)
            }
            packService.save(pack)
        } catch (ValidationException e) {
            respond pack.errors
            return
        }

        respond pack, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || packService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }

    def getAllByClinicId(String clinicId, int offset, int max) {
        respond packService.getAllByClinicId(clinicId, offset, max)
    }
}
