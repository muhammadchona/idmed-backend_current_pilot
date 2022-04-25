package mz.org.fgh.sifmoz.backend.reports.pharmacyManagement

import grails.gorm.services.Service

@Service(ReferredPatientsReport)
interface ReferredPatientsReportService {

    ReferredPatientsReport get(Serializable id)

    List<ReferredPatientsReport> list(Map args)

    Long count()

    ReferredPatientsReport delete(Serializable id)

    ReferredPatientsReport save(ReferredPatientsReport referredPatientsReport)

}
