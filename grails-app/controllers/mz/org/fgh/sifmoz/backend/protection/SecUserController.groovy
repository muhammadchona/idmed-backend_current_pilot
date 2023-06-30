package mz.org.fgh.sifmoz.backend.protection

import grails.converters.JSON
import grails.rest.RestfulController
import grails.validation.ValidationException
import mz.org.fgh.sifmoz.backend.clinicSector.ClinicSector
import mz.org.fgh.sifmoz.backend.clinicSector.ClinicSectorUsers
import mz.org.fgh.sifmoz.backend.service.ClinicalService
import mz.org.fgh.sifmoz.backend.utilities.JSONSerializer

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class SecUserController extends RestfulController {

    ISecUserService secUserService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    SecUserController() {
        super(SecUser)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        render JSONSerializer.setObjectListJsonResponse(secUserService.list(params)) as JSON
    }

    def show(Long id) {
        render JSONSerializer.setJsonObjectResponse(secUserService.get(id)) as JSON
    }

    @Transactional
    def save(SecUser secUser) {
        if (secUser == null) {
            render status: NOT_FOUND
            return
        }
        if (secUser.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond secUser.errors
            return
        }

        try {
            secUserService.save(secUser)
                    if( secUser.roles != null && secUser.accountLocked == false && secUser.roles.length > 0) {
                        SecUserRole.removeAll(secUser)
                        for(String role : secUser.roles) {
                            Role secRole= Role.findByAuthority(role)
                            SecUserRole.create(secUser, secRole)
                        }
                    }
            if( secUser.clinicSectors != null && secUser.accountLocked == false) {
                ClinicSectorUsers.removeAll(secUser)
                for(ClinicSector clinicSector : secUser.clinicSectors) {
                    ClinicSector secSector= ClinicSector.get(clinicSector.id)
                    ClinicSectorUsers.create(secUser, secSector)
                }
            }
        } catch (ValidationException e) {
            respond secUser.errors
            return
        }

        render JSONSerializer.setJsonObjectResponse(SecUser.get(secUser.id)) as JSON
    }

    @Transactional
    def update(SecUser secUser) {
        if (secUser == null) {
            render status: NOT_FOUND
            return
        }
        if (secUser.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond secUser.errors
            return
        }

        try {
            secUserService.save(secUser)
            if( secUser.roles != null && secUser.accountLocked == false && secUser.roles.length > 0) {
                SecUserRole.removeAll(secUser)
                for(String role : secUser.roles) {
                    Role secRole= Role.findByAuthority(role)
                    SecUserRole.create(secUser, secRole)
                }
            }
            if( secUser.clinicSectors != null && secUser.accountLocked == false) {
                ClinicSectorUsers.removeAll(secUser)
                for (ClinicSector clinicSector : secUser.clinicSectors) {
                    ClinicSector secSector = ClinicSector.get(clinicSector.id)
                    ClinicSectorUsers.create(secUser, secSector)
                }
            }
        } catch (ValidationException e) {
            respond secUser.errors
            return
        }

        render JSONSerializer.setJsonObjectResponse(SecUser.get(secUser.id)) as JSON
    }

    @Transactional
    def delete(Long id) {
        if (id == null || secUserService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }

    @Transactional
    void saveSecUserAndRoles(SecUser secUser,List<Role> roles) {

      secUserService.saveSecUserAndRoles(secUser ,roles)

        respond secUser, [status: CREATED, view:"show"]
    }
}
