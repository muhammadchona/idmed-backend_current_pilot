package mz.org.fgh.sifmoz.backend.episode

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.clinicSector.ClinicSector
import mz.org.fgh.sifmoz.backend.episodeType.EpisodeType
import mz.org.fgh.sifmoz.backend.patient.Patient
import mz.org.fgh.sifmoz.backend.patientIdentifier.PatientServiceIdentifier
import mz.org.fgh.sifmoz.backend.patientVisitDetails.IPatientVisitDetailsService
import mz.org.fgh.sifmoz.backend.patientVisitDetails.PatientVisitDetails
import mz.org.fgh.sifmoz.backend.prescription.Prescription
import mz.org.fgh.sifmoz.backend.reports.referralManagement.IReferredPatientsReportService
import mz.org.fgh.sifmoz.backend.service.ClinicalService
import mz.org.fgh.sifmoz.backend.startStopReason.StartStopReason
import org.springframework.beans.factory.annotation.Autowired

@Transactional
@Service(Episode)
abstract class EpisodeService implements IEpisodeService{

    IReferredPatientsReportService referredPatientsReportService
    @Autowired
    IPatientVisitDetailsService iPatientVisitDetailsService

    @Override
    List<Episode> getAllByClinicId(String clinicId, int offset, int max) {
        return Episode.findAllByClinic(Clinic.findById(clinicId),[offset: offset, max: max])
    }
    @Override
    List<Episode> getAllByIndentifier(String identifierId, int offset, int max) {
        def episodes = Episode.findAllWhere(patientServiceIdentifier: PatientServiceIdentifier.findById(identifierId), [sort: ['episodeDate': 'desc'], max: 4])
        return episodes
    }

    @Override
    List<Episode> getEpisodeOfReferralOrBackReferral(Clinic clinic, ClinicalService clinicalService, String startStopReasonCode, Date startDate, Date endDate) {

        StartStopReason startStop = StartStopReason.findByCode(startStopReasonCode)
       // render Episode.findAllByStartStopReasonAndPatientServiceIdentifierInListAndEpisodeDateBetween(startStop,patients,startDate,endDate)
        return Episode.executeQuery("select ep from Episode ep " +
                "inner join ep.startStopReason stp " +
                "inner join ep.patientServiceIdentifier psi " +
                "inner join psi.patient p "+
                "inner join ep.clinic c " +
                "where c.id= ?0 and stp.id = ?1 and psi.service.id = ?2 and ep.episodeDate >= ?3 and ep.episodeDate <= ?4 and ep.episodeDate = (select max(ep2.episodeDate) " +
                "from Episode ep2 " +
                "inner join ep2.patientServiceIdentifier psi2 " +
                "inner join psi2.patient p2 " +
                "inner join ep2.clinic c2 " +
                "where p.id = p2.id and psi.id = psi2.id and c2.id = ?0 and ep2.episodeDate >= ?3 and ep2.episodeDate <= ?4) ", [clinic.id, startStop.id, clinicalService.id, startDate, endDate])
    }

    @Override
    Episode getEpisodeOfReferralByPatientServiceIdentfierAndBelowEpisodeDate(PatientServiceIdentifier patientServiceIdentifier, Date episodeDate) {
       // StartStopReason startStop = StartStopReason.findByCode("REFERIDO_PARA")

        def episodes = Episode.executeQuery("select ep from Episode ep " +
                "inner join ep.startStopReason stp " +
                "inner join ep.patientServiceIdentifier psi " +
                "inner join psi.patient p "+
                "inner join ep.clinic c " +
                "where psi = :patientServiceIdentifier and stp.code = 'REFERIDO_PARA' and ep.episodeDate <= :episodeDate order by ep.episodeDate desc", [patientServiceIdentifier:patientServiceIdentifier, episodeDate:episodeDate])

        return episodes.get(0)
    }

    @Override
    Episode getLastWithVisitByIndentifier(PatientServiceIdentifier patientServiceIdentifier, Clinic clinic) {
        def episodes = Episode.executeQuery("select ep from Episode ep " +
                "inner join ep.startStopReason stp " +
                "inner join ep.patientServiceIdentifier psi " +
                "inner join ep.clinic c " +
                "where psi = :patientServiceIdentifier " +
                "and exists (select pvd from PatientVisitDetails pvd where pvd.episode = ep ) " +
                //"and ep.clinic = :clinic" +
                "order by ep.episodeDate desc", [patientServiceIdentifier: patientServiceIdentifier])
        Episode episode = episodes.get(0)
        PatientVisitDetails patientVisitDetails = iPatientVisitDetailsService.getLastVisitByEpisodeId(episode.id)
        patientVisitDetails.setPrescription(Prescription.findById(patientVisitDetails.prescription.id))
       // episode.setPatientVisitDetails(new HashSet<PatientVisitDetails>())
       // episode.getPatientVisitDetails().add(patientVisitDetails)
        return episode
    }

    @Override
    Episode getLastInitialEpisodeByIdentifier(String identifierId) {
       EpisodeType episodeType = EpisodeType.findByCode("INICIO")

        def episode = Episode.findByPatientServiceIdentifierAndEpisodeType(PatientServiceIdentifier.findById(identifierId),
                episodeType, [sort: ['episodeDate': 'desc']])

        return episode
    }

    @Override
    Episode getLastEpisodeByIdentifier(Patient patient, String serviceCode) {
        return Episode.findByPatientServiceIdentifier(PatientServiceIdentifier.findByPatientAndService(patient, ClinicalService.findByCode(serviceCode)), [sort: ['episodeDate': 'desc']])
    }

    @Override
    List<Episode> getLastWithVisitByClinicAndClinicSector(ClinicSector clinicSector) {

        def episodes = Episode.executeQuery("select  ep from Episode ep " +
                "inner join ep.startStopReason stp " +
                "inner join ep.patientServiceIdentifier psi " +
                "inner join psi.patient p" +
                "inner join ep.clinic c " +
                "where ep.clinicSector = :clinicSector " +
                "and exists (select pvd from PatientVisitDetails pvd where pvd.episode = ep ) " +
                "and ep.episodeDate = ( " +
                "  SELECT MAX(e.episodeDate)" +
                "  FROM Episode e" +
                "  WHERE e.patientServiceIdentifier = ep.patientServiceIdentifier" +
                ")" +
                //"and ep.clinic = :clinic" +
                "order by ep.episodeDate desc", [clinicSector: clinicSector])
        episodes.each { it ->
   //         PatientVisitDetails patientVisitDetails = iPatientVisitDetailsService.getLastVisitByEpisodeId(it.id)
   //         patientVisitDetails.setPrescription(Prescription.findById(patientVisitDetails.prescription.id))
      //      it.setPatientVisitDetails(new HashSet<PatientVisitDetails>())
      //      it.getPatientVisitDetails().add(patientVisitDetails)
        }
        return episodes
    }


}
