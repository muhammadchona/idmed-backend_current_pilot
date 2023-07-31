package mz.org.fgh.sifmoz.backend.reports.pharmacyManagement.mmia

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.convertDateUtils.ConvertDateUtils
import mz.org.fgh.sifmoz.backend.dispenseType.DispenseType
import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams
import mz.org.fgh.sifmoz.backend.packaging.IPackService
import mz.org.fgh.sifmoz.backend.packaging.Pack
import mz.org.fgh.sifmoz.backend.patient.Patient
import mz.org.fgh.sifmoz.backend.patientIdentifier.PatientServiceIdentifier
import mz.org.fgh.sifmoz.backend.patientVisitDetails.IPatientVisitDetailsService
import mz.org.fgh.sifmoz.backend.patientVisitDetails.PatientVisitDetails
import mz.org.fgh.sifmoz.backend.prescriptionDetail.PrescriptionDetail
import mz.org.fgh.sifmoz.backend.reports.common.ReportProcessMonitor
import mz.org.fgh.sifmoz.backend.reports.common.IReportProcessMonitorService
import mz.org.fgh.sifmoz.backend.service.ClinicalService
import mz.org.fgh.sifmoz.backend.utilities.Utilities
import org.springframework.beans.factory.annotation.Autowired



@Transactional
@Service(MmiaReport)
abstract class MmiaReportService implements IMmiaReportService {
    @Autowired
    IPackService packService
    @Autowired
    IReportProcessMonitorService reportProcessMonitorService
    @Autowired
    IPatientVisitDetailsService iPatientVisitDetailsService

    @Override
    void processReport(ReportSearchParams searchParams, MmiaReport curMmiaReport, ReportProcessMonitor processMonitor) {
        List<Pack> packList = packService.getPacksByServiceOnPeriod(ClinicalService.findById(searchParams.getClinicalService()), Clinic.findById(searchParams.getClinicId()), searchParams.getStartDate(), searchParams.getEndDate())
        double percentageUnit = 0
        if (Utilities.listHasElements(packList as ArrayList<?>)) {
            List<MmiaRegimenSubReport> regimenSubReportList = new ArrayList<>()
            HashSet<Patient> patientHashSet = new HashSet<>()

            int counter = 0
            percentageUnit = 65/packList.size()
            int adult = 0
            for (Pack pack : packList) {
                PatientVisitDetails patientVisitDetails = iPatientVisitDetailsService.getByPack(pack)
                for (PatientServiceIdentifier identifier : patientVisitDetails.getPatientVisit().getPatient().getIdentifiers()) {
                    if (identifier.getService().isPrep()) {
                        curMmiaReport.addTotalPacientesPREP()
                    }
                }

                if(patientHashSet.add(patientVisitDetails.getPatientVisit().getPatient())) {
                    Date birthDate = patientVisitDetails.getPatientVisit().getPatient().getDateOfBirth()
                    if (birthDate != null) {
                        if (ConvertDateUtils.getAgeByLocalDates(birthDate , searchParams.getEndDate()) >= 18) {
                            curMmiaReport.addTotalPacientesAdulto()
                        } else if (ConvertDateUtils.getAgeByLocalDates(birthDate , searchParams.getEndDate()) >= 0 &&ConvertDateUtils.getAgeByLocalDates(birthDate,searchParams.getEndDate()) <= 4) {
                            //  println(adult++)
                            curMmiaReport.addTotalPacientes04()
                        } else if (ConvertDateUtils.getAgeByLocalDates(birthDate , searchParams.getEndDate()) >= 5 && ConvertDateUtils.getAgeByLocalDates(birthDate , searchParams.getEndDate()) <= 9) {
                            curMmiaReport.addTotalPacientes59()
                        } else if (ConvertDateUtils.getAgeByLocalDates(birthDate , searchParams.getEndDate())  >= 10 && ConvertDateUtils.getAgeByLocalDates(birthDate , searchParams.getEndDate())  <= 14) {
                            curMmiaReport.addTotalPacientes1014()
                        }
                    }
                    if (patientVisitDetails.getPrescription().patientType == "NOVO") {
                        curMmiaReport.addTotalPacientesInicio()
                    } else if (patientVisitDetails.getPrescription().patientType == "MANUTENCAO") {
                        curMmiaReport.addTotalPacientesManter()
                    } else if (patientVisitDetails.getEpisode().getStartStopReason().isAlteracao()) {
                        curMmiaReport.addTotalPacientesAlterar()
                    } else if (patientVisitDetails.getEpisode().getStartStopReason().isTransito()) {
                        curMmiaReport.addTotalPacientesTransito()
                    } else if (patientVisitDetails.getPrescription().patientType == "TRANSFERENCIA") {
                        curMmiaReport.addTotalPacientesTransferido()
                    }

                    PrescriptionDetail detail = patientVisitDetails.getPrescription().getPrescriptionDetails().getAt(0)
                    if (existsOnMmiaRegimensArray(detail, regimenSubReportList)) {
                        doDetailsCount(detail, regimenSubReportList, patientVisitDetails.getEpisode().getStartStopReason().isReferido())
                    } else {
                        initNewMmiaRegimenRecord(detail, regimenSubReportList, searchParams, patientVisitDetails.getEpisode().getStartStopReason().isTransferido())
                    }


                    if (patientVisitDetails.getPrescription().getPrescriptionDetails().getAt(0).getDispenseType().isDM()) {
                        curMmiaReport.addTotalDM()
                    } else if (patientVisitDetails.getPrescription().getPrescriptionDetails().getAt(0).getDispenseType().isDT()) {
                        curMmiaReport.addTotalDtM0()
                    } else if (patientVisitDetails.getPrescription().getPrescriptionDetails().getAt(0).getDispenseType().isDS()) {
                        curMmiaReport.addTotalDsM0()
                    }

                    processMonitor.setProgress(processMonitor.getProgress() + percentageUnit)
                    reportProcessMonitorService.save(processMonitor)
                    //  println(counter++)
                }
            }
            curMmiaReport.setDsM1(countPacks(DispenseType.DS, determineDate(searchParams.getStartDate(), -1), determineDate(searchParams.getEndDate(), -1), searchParams))
            curMmiaReport.setDsM2(countPacks(DispenseType.DS, determineDate(searchParams.getStartDate(), -2), determineDate(searchParams.getEndDate(), -2), searchParams))
            curMmiaReport.setDsM3(countPacks(DispenseType.DS, determineDate(searchParams.getStartDate(), -3), determineDate(searchParams.getEndDate(), -3), searchParams))
            curMmiaReport.setDsM4(countPacks(DispenseType.DS, determineDate(searchParams.getStartDate(), -4), determineDate(searchParams.getEndDate(), -4), searchParams))
            curMmiaReport.setDsM5(countPacks(DispenseType.DS, determineDate(searchParams.getStartDate(), -5), determineDate(searchParams.getEndDate(), -5), searchParams))

            curMmiaReport.setDtM1(countPacks(DispenseType.DT, determineDate(searchParams.getStartDate(), -1), determineDate(searchParams.getEndDate(), -1), searchParams))
            curMmiaReport.setDtM2(countPacks(DispenseType.DT, determineDate(searchParams.getStartDate(), -2), determineDate(searchParams.getEndDate(), -2), searchParams))
            save(curMmiaReport)

            saveMmiaRegimenItems(regimenSubReportList)
        }
        else {
            processMonitor.setProgress(100)
            reportProcessMonitorService.save(processMonitor)
        }
    }

    private static void initNewMmiaRegimenRecord(PrescriptionDetail detail, List<MmiaRegimenSubReport> regimenSubReportList, ReportSearchParams searchParams, boolean isReferido) {
        if (regimenSubReportList == null) regimenSubReportList = new ArrayList<>()
        regimenSubReportList.add(new MmiaRegimenSubReport(detail, searchParams.getId(), isReferido))
    }

    private static void doDetailsCount(PrescriptionDetail detail, List<MmiaRegimenSubReport> regimenSubReportList, boolean isReferido) {
        for (MmiaRegimenSubReport mmiaRegimenSubReport : regimenSubReportList) {
            if (mmiaRegimenSubReport.getCode() == detail.getTherapeuticRegimen().getCode() && mmiaRegimenSubReport.getLineCode() == detail.getTherapeuticLine().getCode()) {
                isReferido ? mmiaRegimenSubReport.addpatientDC() : mmiaRegimenSubReport.addpatient()
            }
        }
    }

    private static Date determineDate(Date date, int value) {
        return ConvertDateUtils.addMonth(date, value)
    }

    private int countPacks (String dispenseTypeCode, Date startDate, Date endDate, ReportSearchParams searchParams) {
        return packService.countPacksByDispenseTypeAndServiceOnPeriod(DispenseType.findByCode(dispenseTypeCode), ClinicalService.findById(searchParams.getClinicalService()), Clinic.findById(searchParams.getClinicId()), startDate, endDate)
    }

    private static boolean existsOnMmiaRegimensArray(PrescriptionDetail detail, List<MmiaRegimenSubReport> regimenSubReportList) {
        if (!Utilities.listHasElements(regimenSubReportList as ArrayList<?>)) return false
        for (MmiaRegimenSubReport mmiaRegimenSubReport : regimenSubReportList) {
            if (mmiaRegimenSubReport.getCode() == detail.getTherapeuticRegimen().getCode() && mmiaRegimenSubReport.getLineCode() == detail.getTherapeuticLine().getCode()) return true
        }
        false
    }

    private static void saveMmiaRegimenItems(List<MmiaRegimenSubReport> regimenSubReportList) {
        for (MmiaRegimenSubReport mmiaRegimenSubReport : regimenSubReportList) {
            mmiaRegimenSubReport.save()
        }
    }
}
