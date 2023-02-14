package mz.org.fgh.sifmoz.backend.reports.monitoringAndEvaluation

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.convertDateUtils.ConvertDateUtils
import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams
import mz.org.fgh.sifmoz.backend.patientVisitDetails.PatientVisitDetailsService
import mz.org.fgh.sifmoz.backend.reports.common.IReportProcessMonitorService
import mz.org.fgh.sifmoz.backend.reports.common.ReportProcessMonitor
import org.springframework.beans.factory.annotation.Autowired

@Transactional
@Service(ArvDailyRegisterReportTemp)
abstract class ArvDailyRegisterReportService implements IArvDailyRegisterReportService {

    PatientVisitDetailsService patientVisitDetailsService
    @Autowired
    IReportProcessMonitorService reportProcessMonitorService


    public static final String PROCESS_STATUS_PROCESSING_FINISHED = "Processamento terminado"



    private ArvDailyRegisterReportTemp setGenericInfo(ReportSearchParams searchParams) {
        ArvDailyRegisterReportTemp arvTemp = new ArvDailyRegisterReportTemp()
        arvTemp.setStartDate(searchParams.startDate)
        arvTemp.setEndDate(searchParams.endDate)
        arvTemp.setReportId(searchParams.id)
        arvTemp.setPeriodType(searchParams.periodType)
        arvTemp.setReportId(searchParams.id)
        arvTemp.setYear(searchParams.year)
        return arvTemp
    }


    @Override
    void processReportRecords(ReportSearchParams searchParams, ReportProcessMonitor processMonitor) {
        def result = patientVisitDetailsService.getARVDailyReport(searchParams.getClinicId(),
                searchParams.getStartDate(),
                searchParams.getEndDate(), searchParams.getClinicalService())
        double percentageUnit = 0
        if (result.size() == 0) {
            setProcessMonitor(processMonitor)
            reportProcessMonitorService.save(processMonitor)
        } else {
            percentageUnit = 100 / result.size()
        }
        int orderNumber = 1
        for (item in result) {
            ArvDailyRegisterReportTemp arvDailyRegisterReportTemp = setGenericInfo(searchParams)
            processMonitor.setProgress(processMonitor.getProgress() + percentageUnit)
            reportProcessMonitorService.save(processMonitor)
            generateAndSaveArvDaily(item as List, searchParams, arvDailyRegisterReportTemp, orderNumber++)
        }
        processMonitor.setProgress(processMonitor.getProgress() + percentageUnit)
        if (100 == processMonitor.progress.intValue() || 99 == processMonitor.progress.intValue()) {
            setProcessMonitor(processMonitor)
        }
        reportProcessMonitorService.save(processMonitor)

    }

    @Override
    List<ArvDailyRegisterReportTemp> getReportDataByReportId(String reportId) {
        return ArvDailyRegisterReportTemp.findAllByReportId(reportId)
    }

    @Override
    List<DrugQuantityTemp> getSubReportDataById(String id) {
        ArvDailyRegisterReportTemp arvDailyRegisterReportTemp = ArvDailyRegisterReportTemp.findById(id)
            List<DrugQuantityTemp> list = DrugQuantityTemp.executeQuery("select  s from DrugQuantityTemp  s "  +
                    " where s.arvDailyRegisterReportTemp =:arvDailyRegisterReportTemp ",
                    [arvDailyRegisterReportTemp: arvDailyRegisterReportTemp]);
            return list
    }

    private ReportProcessMonitor setProcessMonitor(ReportProcessMonitor processMonitor) {
        processMonitor.setProgress(100)
        processMonitor.setMsg(PROCESS_STATUS_PROCESSING_FINISHED)
    }

    void generateAndSaveArvDaily(List item, ReportSearchParams reportSearchParams, ArvDailyRegisterReportTemp arvTemp, Integer orderNumber) {
        arvTemp.setOrderNumber(orderNumber.toString())
        arvTemp.setNid item[0] == null ? "":(item[0].toString())
        arvTemp.setPatientName(item[1] +" "+item[2] +" " +item[3] )
        Integer age =  item[5] == null? 0 :  ConvertDateUtils.getAgeByLocalDates(item[5] as Date, reportSearchParams.getEndDate())
        arvTemp.setAgeGroup_0_4(age >= 0 && age < 4 ? "Sim" : "Nao")
        arvTemp.setAgeGroup_5_9(age >= 5 && age <= 9 ? "Sim" : "Nao")
        arvTemp.setAgeGroup_10_14(age >= 10 && age <= 14 ? "Sim" : "Nao")
        arvTemp.setAgeGroup_Greater_than_15(age >= 15 ? "Sim" : "Nao")
        arvTemp.setPatientType( item[4] == null ? "": item[4].toString())
        arvTemp.setDispensationType(item[6] == null? "":item[6].toString())
        arvTemp.setTherapeuticLine(item[7]==null ? "": item[7].toString())
        arvTemp.setPickupDate(item[8]==null ? "": item[8] as Date)
        arvTemp.setNextPickupDate(item[9]==null ? "": item[9] as Date)
        arvTemp.setRegime(item[10]==null ? "": item[10].toString())
        arvTemp.setId(item[11]==null ? "": item[11].toString())
        arvTemp.setStartReason(item[12]==null ? "": item[12].toString())
        arvTemp.setPrep("PREP".equalsIgnoreCase(item[13])  ? "": item[14] as Boolean? "Sim": "")
        arvTemp.setPatientVisitDetailId(item[15]==null ? "": item[15].toString())
        save(arvTemp)
        for (DrugQuantityTemp prod : patientVisitDetailsService.getProducts(arvTemp.getPatientVisitDetailId(), reportSearchParams.getClinicId())) {
            prod.setArvDailyRegisterReportTemp(arvTemp)
            save(prod)
        }
    }


}
