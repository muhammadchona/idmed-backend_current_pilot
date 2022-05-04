package mz.org.fgh.sifmoz.backend.reports.pharmacyManagement.mmia

import grails.validation.ValidationException
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.convertDateUtils.ConvertDateUtils
import mz.org.fgh.sifmoz.backend.dispenseType.DispenseType
import mz.org.fgh.sifmoz.backend.multithread.MultiThreadRestReportController
import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams
import mz.org.fgh.sifmoz.backend.packaging.IPackService
import mz.org.fgh.sifmoz.backend.packaging.Pack
import mz.org.fgh.sifmoz.backend.prescriptionDetail.PrescriptionDetail
import mz.org.fgh.sifmoz.backend.service.ClinicalService
import mz.org.fgh.sifmoz.backend.utilities.Utilities

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class MmiaReportController extends MultiThreadRestReportController{

    MmiaReportService mmiaReportService
    List<Pack> packList
    MmiaReport curMmiaReport
    IPackService packService
    List<MmiaRegimenSubReport> regimenSubReportList

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    MmiaReportController() {
        super(MmiaReport)
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
        respond mmiaReportService.list(params), model:[mmiaReportCount: mmiaReportService.count()]
    }

    def show(Long id) {
        respond mmiaReportService.get(id)
    }

    @Transactional
    def save(MmiaReport mmiaReport) {
        if (mmiaReport == null) {
            render status: NOT_FOUND
            return
        }
        if (mmiaReport.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond mmiaReport.errors
            return
        }

        try {
            mmiaReportService.save(mmiaReport)
        } catch (ValidationException e) {
            respond mmiaReport.errors
            return
        }

        respond mmiaReport, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(MmiaReport mmiaReport) {
        if (mmiaReport == null) {
            render status: NOT_FOUND
            return
        }
        if (mmiaReport.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond mmiaReport.errors
            return
        }

        try {
            mmiaReportService.save(mmiaReport)
        } catch (ValidationException e) {
            respond mmiaReport.errors
            return
        }

        respond mmiaReport, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || mmiaReportService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }

    def initReportProcess (ReportSearchParams searchParams) {
        super.initReportParams(searchParams)
        render qtyToProcess: qtyRecordsToProcess
        doProcessReport()
    }

    @Override
    void run() {
        initMmiaReportRecord()
        searchAndProcessPacks()
        processMmiaStock()
    }

    void processMmiaStock() {

    }

    void searchAndProcessPacks() {
        this.packList = packService.getPacksByServiceOnPeriod(ClinicalService.findByCode(getSearchParams().getServiceCode()), Clinic.findById(getSearchParams().getClinicId()), getSearchParams().getStartDate(), getSearchParams().getEndDate())

        if (Utilities.listHasElements(packList as ArrayList<?>)) {
            for (Pack pack : packList) {
                if (ConvertDateUtils.getAge(pack.getPatientVisitDetails().getAt(0).getPatientVisit().getPatient().getDateOfBirth()) >= 18) {
                    curMmiaReport.addTotalPacientesAdulto()
                } else if (ConvertDateUtils.getAge(pack.getPatientVisitDetails().getAt(0).getPatientVisit().getPatient().getDateOfBirth()) <= 4) {
                    curMmiaReport.addTotalPacientes04()
                } else if (ConvertDateUtils.getAge(pack.getPatientVisitDetails().getAt(0).getPatientVisit().getPatient().getDateOfBirth()) >= 5 && ConvertDateUtils.getAge(pack.getPatientVisitDetails().getAt(0).getPatientVisit().getPatient().getDateOfBirth()) <= 9) {
                    curMmiaReport.addTotalPacientes59()
                } else if (ConvertDateUtils.getAge(pack.getPatientVisitDetails().getAt(0).getPatientVisit().getPatient().getDateOfBirth()) >= 10 && ConvertDateUtils.getAge(pack.getPatientVisitDetails().getAt(0).getPatientVisit().getPatient().getDateOfBirth()) <= 14) {
                    curMmiaReport.addTotalPacientes1014()
                }
                if (pack.getPatientVisitDetails().getAt(0).getEpisode().getStartStopReason().isNew()) {
                    curMmiaReport.addTotalPacientesInicio()
                } else if (pack.getPatientVisitDetails().getAt(0).getEpisode().getStartStopReason().isManutencao()) {
                    curMmiaReport.addTotalPacientesManter()
                } else if (pack.getPatientVisitDetails().getAt(0).getEpisode().getStartStopReason().isAlteracao()) {
                    curMmiaReport.addTotalPacientesAlterar()
                } else if (pack.getPatientVisitDetails().getAt(0).getEpisode().getStartStopReason().isTransito()) {
                    curMmiaReport.addTotalPacientesTransito()
                } else if (pack.getPatientVisitDetails().getAt(0).getEpisode().getStartStopReason().isTransferido()) {
                    curMmiaReport.addTotalPacientesTransferido()
                }

                PrescriptionDetail detail = pack.getPatientVisitDetails().getAt(0).getPrescription().getPrescriptionDetails().getAt(0)
                if (existsOnMmiaRegimensArray(detail)) {
                    doDetailsCount(detail)
                } else {
                    initNewMmiaRegimenRecord(detail)
                }

                if (pack.getPatientVisitDetails().getAt(0).getPrescription().getPrescriptionDetails().getAt(0).getDispenseType().isDM()) {
                    curMmiaReport.addTotalDM()
                } else if (pack.getPatientVisitDetails().getAt(0).getPrescription().getPrescriptionDetails().getAt(0).getDispenseType().isDT()) {
                    curMmiaReport.addTotalDtM0()
                } else if (pack.getPatientVisitDetails().getAt(0).getPrescription().getPrescriptionDetails().getAt(0).getDispenseType().isDS()) {
                    curMmiaReport.addTotalDsM0()
                }
            }
            curMmiaReport.setDsM1(countPacks(DispenseType.DS, determineDate(getSearchParams().getStartDate(), -1), determineDate(getSearchParams().getEndDate(), -1)))
            curMmiaReport.setDsM2(countPacks(DispenseType.DS, determineDate(getSearchParams().getStartDate(), -2), determineDate(getSearchParams().getEndDate(), -2)))
            curMmiaReport.setDsM3(countPacks(DispenseType.DS, determineDate(getSearchParams().getStartDate(), -3), determineDate(getSearchParams().getEndDate(), -3)))
            curMmiaReport.setDsM4(countPacks(DispenseType.DS, determineDate(getSearchParams().getStartDate(), -4), determineDate(getSearchParams().getEndDate(), -4)))
            curMmiaReport.setDsM5(countPacks(DispenseType.DS, determineDate(getSearchParams().getStartDate(), -5), determineDate(getSearchParams().getEndDate(), -5)))

            curMmiaReport.setDtM1(countPacks(DispenseType.DT, determineDate(getSearchParams().getStartDate(), -1), determineDate(getSearchParams().getEndDate(), -1)))
            curMmiaReport.setDtM2(countPacks(DispenseType.DT, determineDate(getSearchParams().getStartDate(), -2), determineDate(getSearchParams().getEndDate(), -2)))
        } else {
            render status: NO_CONTENT
        }
    }

    int countPacks (String dispenseTypeCode, Date startDate, Date endDate) {
        return packService.countPacksByDispenseTypeAndServiceOnPeriod(DispenseType.findByCode(dispenseTypeCode), ClinicalService.findByCode(getSearchParams().getServiceCode()), Clinic.findById(getSearchParams().getClinicId()), startDate, endDate)
    }

    Date determineDate(Date date, int value) {
        return ConvertDateUtils.addMonth(date, value)
    }

    boolean existsOnMmiaRegimensArray(PrescriptionDetail detail) {
        if (Utilities.listHasElements(this.regimenSubReportList as ArrayList<?>)) return false
        for (MmiaRegimenSubReport mmiaRegimenSubReport : this.regimenSubReportList) {
            if (mmiaRegimenSubReport.getCode().equals(detail.getTherapeuticRegimen().getCode()) && mmiaRegimenSubReport.getLineCode().equals(detail.getTherapeuticLine().getCode())) return true
        }
        false
    }

    void initNewMmiaRegimenRecord(PrescriptionDetail detail) {
        if (this.regimenSubReportList == null) this.regimenSubReportList = new ArrayList<>()
        this.regimenSubReportList.add(new MmiaRegimenSubReport(detail, getSearchParams().getReportId()))
    }

    void doDetailsCount(PrescriptionDetail detail) {
        for (MmiaRegimenSubReport mmiaRegimenSubReport : this.regimenSubReportList) {
            if (mmiaRegimenSubReport.getCode().equals(detail.getTherapeuticRegimen().getCode()) && mmiaRegimenSubReport.getLineCode().equals(detail.getTherapeuticLine().getCode())) {
                mmiaRegimenSubReport.addpatient()
            }
        }
    }

    void initMmiaReportRecord() {
        this.curMmiaReport = new MmiaReport()
        this.curMmiaReport.setReportId(getSearchParams().getReportId())
        this.curMmiaReport.setClinicId(getSearchParams().getClinicId())
        this.curMmiaReport.setPeriodType(getSearchParams().getPeriodType())
        this.curMmiaReport.setPeriod(Integer.valueOf(getSearchParams().getPeriod()))
        this.curMmiaReport.setYear(getSearchParams().getYear())
    }
}
