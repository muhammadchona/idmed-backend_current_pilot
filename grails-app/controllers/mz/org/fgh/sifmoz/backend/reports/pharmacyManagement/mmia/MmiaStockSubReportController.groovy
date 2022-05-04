package mz.org.fgh.sifmoz.backend.reports.pharmacyManagement.mmia

import grails.validation.ValidationException
import mz.org.fgh.sifmoz.backend.multithread.MultiThreadRestReportController

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class MmiaStockSubReportController extends MultiThreadRestReportController<MmiaStockSubReport>{

    MmiaStockSubReportService mmiaStockSubReportService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    MmiaStockSubReportController(Class<MmiaStockSubReport> resource) {
        super(resource)
    }

    @Override
    long getRecordsQtyToProcess() {
        return 0
    }

    @Override
    void getProcessedRecordsQty(String reportId) {

    }

    @Override
    void printReport(String reportId, String fileType) {

    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond mmiaStockSubReportService.list(params), model:[mmiaStockSubReportCount: mmiaStockSubReportService.count()]
    }

    def show(Long id) {
        respond mmiaStockSubReportService.get(id)
    }

    @Transactional
    def save(MmiaStockSubReport mmiaStockSubReport) {
        if (mmiaStockSubReport == null) {
            render status: NOT_FOUND
            return
        }
        if (mmiaStockSubReport.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond mmiaStockSubReport.errors
            return
        }

        try {
            mmiaStockSubReportService.save(mmiaStockSubReport)
        } catch (ValidationException e) {
            respond mmiaStockSubReport.errors
            return
        }

        respond mmiaStockSubReport, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(MmiaStockSubReport mmiaStockSubReport) {
        if (mmiaStockSubReport == null) {
            render status: NOT_FOUND
            return
        }
        if (mmiaStockSubReport.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond mmiaStockSubReport.errors
            return
        }

        try {
            mmiaStockSubReportService.save(mmiaStockSubReport)
        } catch (ValidationException e) {
            respond mmiaStockSubReport.errors
            return
        }

        respond mmiaStockSubReport, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || mmiaStockSubReportService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }

    @Override
    void run() {

    }
}
