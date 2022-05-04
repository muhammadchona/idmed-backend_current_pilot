package mz.org.fgh.sifmoz.backend.reports.pharmacyManagement.mmia

import grails.gorm.services.Service

@Service(MmiaStockSubReport)
interface MmiaStockSubReportService {

    MmiaStockSubReport get(Serializable id)

    List<MmiaStockSubReport> list(Map args)

    Long count()

    MmiaStockSubReport delete(Serializable id)

    MmiaStockSubReport save(MmiaStockSubReport mmiaStockSubReport)
}
