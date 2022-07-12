package mz.org.fgh.sifmoz.backend.reports.common

import mz.org.fgh.sifmoz.backend.base.BaseEntity

class ReportProcessMonitor extends BaseEntity {

    String id
    String reportId
    String msg
    double progress

    ReportProcessMonitor() {
    }

    ReportProcessMonitor(String reportId, String msg, double progress) {
        this.reportId = reportId
        this.msg = msg
        this.progress = progress
    }

    static mapping = {
        id generator: "uuid"
    }
    static constraints = {
    }
}
