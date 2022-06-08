package mz.org.fgh.sifmoz.backend.reports.monitoringAndEvaluation

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.convertDateUtils.ConvertDateUtils
import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams
import mz.org.fgh.sifmoz.backend.patient.Patient
import mz.org.fgh.sifmoz.backend.patientVisitDetails.PatientVisitDetails
import mz.org.fgh.sifmoz.backend.patientVisitDetails.PatientVisitDetailsService
import mz.org.fgh.sifmoz.backend.prescriptionDetail.PrescriptionDetail
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


    @Override
    void processReportRecords(ReportSearchParams searchParams, ReportProcessMonitor processMonitor) {
        List<PatientVisitDetails> patientVisitDetailsList = patientVisitDetailsService.getARVDailyReport(searchParams.getClinicId(),
                searchParams.getStartDate(),
                searchParams.getEndDate(), searchParams.getClinicalService())
        double percentageUnit = 0
        if (patientVisitDetailsList.size() == 0) {
            setProcessMonitor(processMonitor)
            reportProcessMonitorService.save(processMonitor)
        } else {
            percentageUnit = 100 / patientVisitDetailsList.size()
        }

        int i = 1
        for (PatientVisitDetails item : patientVisitDetailsList) {
            Patient patient = item.getEpisode().getPatientServiceIdentifier().getPatient()
            PrescriptionDetail prescriptionDetail = PrescriptionDetail.findByPrescription(item.getPrescription())
            ArvDailyRegisterReportTemp reportTemp = new ArvDailyRegisterReportTemp()
            reportTemp.setYear(searchParams.getYear())
            reportTemp.setPharmacyId(searchParams.getClinicId())
            reportTemp.setProvinceId(searchParams.getProvinceId())
            reportTemp.setDistrictId(searchParams.getDistrictId())
            reportTemp.setStartDate(searchParams.getStartDate())
            reportTemp.setEndDate(searchParams.getEndDate())
            reportTemp.setPeriodType(searchParams.getPeriodType())
            reportTemp.setPeriod(searchParams.getPeriod())
            reportTemp.setReportId(searchParams.getId())
            reportTemp.setOrderNumber(String.valueOf(i++))
            reportTemp.setNid(item.getEpisode().getPatientServiceIdentifier().getValue())
            reportTemp.setPatientName((patient.getFirstNames() + " " + patient.getMiddleNames() + " " + patient.getLastNames()))
            reportTemp.setPatientType(item.getPrescription().getPatientType())
            double age = ConvertDateUtils.getAgeBetweenTwoDates(item.getPack().getPickupDate(), searchParams.getEndDate())
            reportTemp.setAgeGroup_0_4(age >= 0 && age < 4 ? "Sim" : "Nao") //TODO
            reportTemp.setAgeGroup_5_9(age >= 5 && age <= 9 ? "Sim" : "Nao")
            reportTemp.setAgeGroup_10_14(age >= 10 && age <= 14 ? "Sim" : "Nao")
            reportTemp.setAgeGroup_Greater_than_15(age >= 15 ? "Sim" : "Nao")
            reportTemp.setArvType(item.getPrescription().getPatientType())
            reportTemp.setDispensationType(prescriptionDetail.getDispenseType().getDescription())
            reportTemp.setTherapeuticLine(prescriptionDetail.getTherapeuticLine().getDescription())
            reportTemp.setPickupDate(item.getPack().getPickupDate())
            reportTemp.setNextPickupDate(item.getPack().getNextPickUpDate())
            reportTemp.setRegime(prescriptionDetail.getTherapeuticRegimen().getDescription())
            reportTemp.setPackId(item.getPack().getId())
            reportTemp.setStartReason(item.getEpisode().getStartStopReason().getReason()) //PatientType
            reportTemp.setPrep("PREP".equalsIgnoreCase(item.getEpisode().getPatientServiceIdentifier().getService().getCode()) && item.getEpisode().getStartStopReason().getIsStartReason() ? "Sim" : "")

            save(reportTemp)
            for (DrugQuantityTemp prod : patientVisitDetailsService.getProducts(item.getId(), item.getPack().getClinicId(), searchParams.getStartDate(), searchParams.getEndDate())) {
                prod.setArvDailyRegisterReportTemp(reportTemp)
                save(prod)
            }

            processMonitor.setProgress(processMonitor.getProgress() + percentageUnit)
            if (100 == processMonitor.progress.intValue() || 99 == processMonitor.progress.intValue()) {
                setProcessMonitor(processMonitor)
            }
            reportProcessMonitorService.save(processMonitor)
        }
    }

    @Override
    List<ArvDailyRegisterReportTemp> getReportDataByReportId(String reportId) {
        return ArvDailyRegisterReportTemp.findAllByReportId(reportId)
    }


    private ReportProcessMonitor setProcessMonitor(ReportProcessMonitor processMonitor) {
        processMonitor.setProgress(100)
        processMonitor.setMsg(PROCESS_STATUS_PROCESSING_FINISHED)
    }


}
