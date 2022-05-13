package mz.org.fgh.sifmoz.backend.reports.pharmacyManagement

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.convertDateUtils.ConvertDateUtils
import mz.org.fgh.sifmoz.backend.episode.Episode
import mz.org.fgh.sifmoz.backend.episode.EpisodeService
import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams
import mz.org.fgh.sifmoz.backend.packaging.Pack
import mz.org.fgh.sifmoz.backend.packaging.PackService
import mz.org.fgh.sifmoz.backend.prescription.Prescription
import mz.org.fgh.sifmoz.backend.prescription.PrescriptionService
import mz.org.fgh.sifmoz.backend.prescriptionDetail.PrescriptionDetail
import mz.org.fgh.sifmoz.backend.reports.referralManagement.IReferredPatientsReportService
import mz.org.fgh.sifmoz.backend.service.ClinicalService

@Transactional
@Service(ReferredPatientsReport)
abstract class ReferredPatientsReportService implements IReferredPatientsReportService{

    PrescriptionService prescriptionService

    PackService packService
    EpisodeService episodeService

    @Override
    int processReferredAndBackReferredReportRecords(ReportSearchParams searchParams) {

        Clinic clinic = Clinic.findById(searchParams.clinicId)
        ClinicalService clinicalService = ClinicalService.findById(searchParams.clinicalService)
        Map<String , Prescription> prescriptionMap= prescriptionService.getLastPrescriptionsByClinicAndClinicalService(clinic,clinicalService)
        List<Episode> episodes = episodeService.getEpisodeOfReferralOrBackReferral(clinic,clinicalService,searchParams.reportType,searchParams.startDate,searchParams.endDate)
        println(episodes)
        List<ReferredPatientsReport> referencesToCreate = new ArrayList<>()
        for (Episode episode:episodes) {
            Prescription lastPrescription = prescriptionMap.get(episode.patientServiceIdentifier.patient.id)
            PrescriptionDetail prescriptionDetail = lastPrescription.prescriptionDetails.getAt(0)

            ReferredPatientsReport referredPatient = setGenericInfo(searchParams,clinic,episode)
            if (searchParams.reportType.equals('REFERIDO_PARA')) {
                referredPatient.setReferrenceDate(episode.episodeDate)
                referredPatient.setLastPrescriptionDate(lastPrescription.prescriptionDate)
                referredPatient.setDispenseType(prescriptionDetail.dispenseType.description)
                referredPatient.setTherapeuticalRegimen(prescriptionDetail.therapeuticRegimen.description)
                referredPatient.setNextPickUpDate(lastPrescription.patientVisitDetails.getAt(0).pack.nextPickUpDate)
            } else {
                referredPatient.setDateBackUs(episode.episodeDate)
                referredPatient.setReferrenceDate(episodeService.getEpisodeOfReferralByPatientServiceIdentfierAndBelowEpisodeDate(episode.patientServiceIdentifier,episode.episodeDate).episodeDate)
                referredPatient.setLastPickUpDate(lastPrescription.patientVisitDetails.getAt(0).pack.pickupDate)
                referredPatient.setNotes(episode.notes)
            }
            referencesToCreate.add(referredPatient)
            save(referredPatient)
        }
        return episodes.size()
    }

    void processReportReferredDispenseRecords(ReportSearchParams searchParams) {
        Clinic clinic = Clinic.findById(searchParams.clinicId)
        ClinicalService clinicalService = ClinicalService.findById(searchParams.clinicalService)
        //     Map<String , Prescription> prescriptionMap= prescriptionService.getLastPrescriptionsByClinicAndClinicalService(clinic,clinicalService)
        List<Pack> packs = packService.getPacksOfReferredPatientsByClinicalServiceAndClinicOnPeriod(clinicalService,clinic,searchParams.startDate,searchParams.endDate)

        for (Pack pack:packs) {
            Prescription prescription = pack.patientVisitDetails.getAt(0).prescription
            PrescriptionDetail prescriptionDetail = prescription.prescriptionDetails.getAt(0)
            Episode episode = pack.patientVisitDetails.getAt(0).episode

            ReferredPatientsReport referredPatientDispense = setGenericInfo(searchParams,clinic,episode)
            referredPatientDispense.setDispenseType(prescriptionDetail.dispenseType.description)
            referredPatientDispense.setTherapeuticalRegimen(prescriptionDetail.therapeuticRegimen.description)
            referredPatientDispense.setPickUpDate(pack.pickupDate)
            referredPatientDispense.setNextPickUpDate(pack.nextPickUpDate)
            referredPatientDispense.setTarvType(prescription.patientType)
            save(referredPatientDispense)
        }
    }

    void processReportAbsentReferredDispenseRecords(ReportSearchParams searchParams) {
        Clinic clinic = Clinic.findById(searchParams.clinicId)
        ClinicalService clinicalService = ClinicalService.findById(searchParams.clinicalService)
        List<Pack> packs = packService.getAbsentReferredPatientsByClinicalServiceAndClinicOnPeriod(clinicalService,clinic,searchParams.startDate,searchParams.endDate)

        for (Pack pack:packs) {
            Prescription prescription = pack.patientVisitDetails.getAt(0).prescription
            PrescriptionDetail prescriptionDetail = prescription.prescriptionDetails.getAt(0)
            Episode episode = pack.patientVisitDetails.getAt(0).episode

            ReferredPatientsReport referredPatientAbsent = setGenericInfo(searchParams,clinic,episode)
            referredPatientAbsent.setDateMissedPickUp(pack.nextPickUpDate)
            referredPatientAbsent.setDateIdentifiedAbandonment(ConvertDateUtils.addDaysDate(pack.nextPickUpDate,60))
            referredPatientAbsent.setContact(episode.patientServiceIdentifier.patient.cellphone)
            save(referredPatientAbsent)
        }
    }

    private ReferredPatientsReport setGenericInfo(ReportSearchParams searchParams,Clinic clinic,Episode episode) {

        ReferredPatientsReport referredPatient = new ReferredPatientsReport()
        referredPatient.setPharmacyId(clinic.id)
        referredPatient.setStartDate(searchParams.startDate)
        referredPatient.setEndDate(searchParams.endDate)
        referredPatient.setClinicalServiceId(episode.patientServiceIdentifier.service.code)
        referredPatient.setReportId(searchParams.id)
        referredPatient.setPeriodType(searchParams.periodType)
        referredPatient.setReportId(searchParams.id)
        referredPatient.setAge(ConvertDateUtils.getAge(episode.patientServiceIdentifier.patient.dateOfBirth).intValue())
        referredPatient.setNid(episode.patientServiceIdentifier.value)
        referredPatient.setName(episode.patientServiceIdentifier.patient.firstNames +' '+episode.patientServiceIdentifier.patient.lastNames)
        referredPatient.setReferralPharmacy(episode.referralClinic.clinicName)
        return referredPatient
    }
}
