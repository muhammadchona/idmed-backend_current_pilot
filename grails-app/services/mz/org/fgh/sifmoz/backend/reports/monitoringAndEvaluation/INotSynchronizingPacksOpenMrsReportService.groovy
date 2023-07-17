package mz.org.fgh.sifmoz.backend.reports.monitoringAndEvaluation

import grails.gorm.services.Service
import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams
import mz.org.fgh.sifmoz.backend.reports.common.ReportProcessMonitor


interface INotSynchronizingPacksOpenMrsReportService {

    NotSynchronizingPacksOpenMrsReport get(Serializable id)

    List<NotSynchronizingPacksOpenMrsReport> list(Map args)

    Long count()

    NotSynchronizingPacksOpenMrsReport delete(Serializable id)

    NotSynchronizingPacksOpenMrsReport save(NotSynchronizingPacksOpenMrsReport notSynchronizingPacksOpenMrsReport)

    void processReportRecords(ReportSearchParams searchParams, ReportProcessMonitor processMonitor)

    List<ArvDailyRegisterReportTemp> getReportDataByReportId(String reportId)


}
