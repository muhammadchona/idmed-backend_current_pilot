package mz.org.fgh.sifmoz.backend.Doctor

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class DoctorController {

    DoctorService doctorService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond doctorService.list(params), model:[doctorCount: doctorService.count()]
    }

    def show(Long id) {
        respond doctorService.get(id)
    }

    @Transactional
    def save(Doctor doctor) {
        if (doctor == null) {
            render status: NOT_FOUND
            return
        }
        if (doctor.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond doctor.errors
            return
        }

        try {
            doctorService.save(doctor)
        } catch (ValidationException e) {
            respond doctor.errors
            return
        }

        respond doctor, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Doctor doctor) {
        if (doctor == null) {
            render status: NOT_FOUND
            return
        }
        if (doctor.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond doctor.errors
            return
        }

        try {
            doctorService.save(doctor)
        } catch (ValidationException e) {
            respond doctor.errors
            return
        }

        respond doctor, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || doctorService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
