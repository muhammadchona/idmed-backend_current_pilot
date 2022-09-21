package mz.org.fgh.sifmoz.backend.reports.stock

import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.convertDateUtils.ConvertDateUtils
import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams
import mz.org.fgh.sifmoz.backend.reports.common.ReportProcessMonitor
import mz.org.fgh.sifmoz.backend.reports.pharmacyManagement.mmia.MmiaStockSubReportItem
import mz.org.fgh.sifmoz.backend.service.ClinicalService
import mz.org.fgh.sifmoz.backend.stockentrance.StockEntrance
import mz.org.fgh.sifmoz.backend.utilities.Utilities
import mz.org.fgh.sifmoz.dashboard.DashboardServiceButton
import mz.org.fgh.sifmoz.stock.DrugStockFileEvent
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired

@Transactional
class DrugStockFileService {

    @Autowired
    SessionFactory sessionFactory

    def serviceMethod() {

    }

    def List<DrugStockFileEvent> getDrugSumaryEvents(String clinicId, String drugId) {
        List<DrugStockFileEvent> drugStockFileEventArrayList = new ArrayList<>()
        Session session = sessionFactory.getCurrentSession()

        String queryString ="select *  " +
                            "from drug_stock_summary_vw  " +
                            "where drug_id = :drug " +
                            "   and clinic_id = :clinic " +
                            "order by event_date asc"


        def query = session.createSQLQuery(queryString)
        query.setParameter("drug", drugId)
        query.setParameter("clinic", clinicId)
        List<Object[]> result = query.list()

        if (Utilities.listHasElements(result as ArrayList<?>)) {
            initStockEvent(result, drugStockFileEventArrayList)
        }

        return drugStockFileEventArrayList
    }

    def List<DrugStockFileEvent> getDrugBatchSumaryEvents(String clinicId, String stockId) {
        List<DrugStockFileEvent> drugStockFileEventArrayList = new ArrayList<>()

        Session session = sessionFactory.getCurrentSession()

        String queryString ="select *  " +
                "from drug_stock_batch_summary_vw  " +
                "where stock = :stock " +
                "   and clinic_id = :clinic " +
                "order by event_date asc"


        def query = session.createSQLQuery(queryString)
        query.setParameter("stock", stockId)
        query.setParameter("clinic", clinicId)
        List<Object[]> result = query.list()

        if (Utilities.listHasElements(result as ArrayList<?>)) {
            initStockEvent(result, drugStockFileEventArrayList)
        }

        return drugStockFileEventArrayList
    }

    private void initStockEvent(List result, ArrayList<DrugStockFileEvent> drugStockFileEventArrayList) {
        for (int i = 0; i < result.size(); i++) {
            DrugStockFileEvent drugStockFileEvent = new DrugStockFileEvent()

            drugStockFileEvent.moviment = String.valueOf(result[i][3])
            drugStockFileEvent.eventDate = result[i][2] as Date
            drugStockFileEvent.orderNumber = String.valueOf(result[i][4])
            drugStockFileEvent.incomes = Long.valueOf(String.valueOf(result[i][5]))
            drugStockFileEvent.outcomes = Long.valueOf(String.valueOf(result[i][6]))
            drugStockFileEvent.posetiveAdjustment = Long.valueOf(String.valueOf(result[i][7]))
            drugStockFileEvent.negativeAdjustment = Long.valueOf(String.valueOf(result[i][8]))
            drugStockFileEvent.loses = Long.valueOf(String.valueOf(result[i][9]))
            drugStockFileEvent.code = String.valueOf(result[i][11])
            drugStockFileEvent.stockId = String.valueOf(result[i][12])
            drugStockFileEvent.notes = (Utilities.stringHasValue(String.valueOf(result[i][13])) && String.valueOf(result[i][13]) != "null") ? String.valueOf(result[i][13]) : ""

            if (Utilities.listHasElements(drugStockFileEventArrayList as ArrayList<?>)) {
                drugStockFileEvent.calculateBalance(drugStockFileEventArrayList.get(drugStockFileEventArrayList.size() - 1).getBalance())
            } else {
                drugStockFileEvent.balance = drugStockFileEvent.incomes
            }
            drugStockFileEventArrayList.add(drugStockFileEvent)
        }
    }
}
