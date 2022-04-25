package mz.org.fgh.sifmoz.backend.reports.pharmacyManagement

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class ReferredPatientsReportController {

    ReferredPatientsReportService referredPatientsReportService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond referredPatientsReportService.list(params), model:[referredPatientsReportCount: referredPatientsReportService.count()]
    }

    def show(Long id) {
        respond referredPatientsReportService.get(id)
    }

    @Transactional
    def save(ReferredPatientsReport referredPatientsReport) {
        if (referredPatientsReport == null) {
            render status: NOT_FOUND
            return
        }
        if (referredPatientsReport.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond referredPatientsReport.errors
            return
        }

        try {
            referredPatientsReportService.save(referredPatientsReport)
        } catch (ValidationException e) {
            respond referredPatientsReport.errors
            return
        }

        respond referredPatientsReport, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ReferredPatientsReport referredPatientsReport) {
        if (referredPatientsReport == null) {
            render status: NOT_FOUND
            return
        }
        if (referredPatientsReport.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond referredPatientsReport.errors
            return
        }

        try {
            referredPatientsReportService.save(referredPatientsReport)
        } catch (ValidationException e) {
            respond referredPatientsReport.errors
            return
        }

        respond referredPatientsReport, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || referredPatientsReportService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
