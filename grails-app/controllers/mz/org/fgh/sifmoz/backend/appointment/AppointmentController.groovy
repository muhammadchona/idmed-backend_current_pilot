package mz.org.fgh.sifmoz.backend.appointment

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class AppointmentController {

    AppointmentService appointmentService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond appointmentService.list(params), model:[appointmentCount: appointmentService.count()]
    }

    def show(Long id) {
        respond appointmentService.get(id)
    }

    @Transactional
    def save(Appointment appointment) {
        if (appointment == null) {
            render status: NOT_FOUND
            return
        }
        if (appointment.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond appointment.errors
            return
        }

        try {
            appointmentService.save(appointment)
        } catch (ValidationException e) {
            respond appointment.errors
            return
        }

        respond appointment, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Appointment appointment) {
        if (appointment == null) {
            render status: NOT_FOUND
            return
        }
        if (appointment.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond appointment.errors
            return
        }

        try {
            appointmentService.save(appointment)
        } catch (ValidationException e) {
            respond appointment.errors
            return
        }

        respond appointment, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || appointmentService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
