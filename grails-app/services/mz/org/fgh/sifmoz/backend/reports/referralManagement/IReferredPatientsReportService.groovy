package mz.org.fgh.sifmoz.backend.reports.referralManagement

import grails.gorm.services.Service
import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams
import mz.org.fgh.sifmoz.backend.reports.pharmacyManagement.ReferredPatientsReport


interface IReferredPatientsReportService {

    ReferredPatientsReport get(Serializable id)

    List<ReferredPatientsReport> list(Map args)

    Long count()

    ReferredPatientsReport delete(Serializable id)

    ReferredPatientsReport save(ReferredPatientsReport referredPatientsReport)

    int processReferredAndBackReferredReportRecords(ReportSearchParams searchParams)

    void processReportReferredDispenseRecords(ReportSearchParams searchParams)

    void processReportAbsentReferredDispenseRecords(ReportSearchParams searchParams)

}
