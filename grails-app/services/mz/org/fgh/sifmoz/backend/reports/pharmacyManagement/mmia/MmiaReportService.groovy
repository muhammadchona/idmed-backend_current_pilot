package mz.org.fgh.sifmoz.backend.reports.pharmacyManagement.mmia

import grails.gorm.services.Service

@Service(MmiaReport)
interface MmiaReportService {

    MmiaReport get(Serializable id)

    List<MmiaReport> list(Map args)

    Long count()

    MmiaReport delete(Serializable id)

    MmiaReport save(MmiaReport mmiaReport)
}
