package mz.org.fgh.sifmoz.backend.patientVisitDetails

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.episode.Episode
import mz.org.fgh.sifmoz.backend.patient.Patient
import mz.org.fgh.sifmoz.backend.patientIdentifier.PatientServiceIdentifier
import mz.org.fgh.sifmoz.backend.patientVisit.PatientVisit
import mz.org.fgh.sifmoz.backend.prescription.Prescription

@Transactional
@Service(PatientVisitDetails)
abstract class PatientVisitDetailsService implements IPatientVisitDetailsService{
    @Override
    List<PatientVisitDetails> getAllByClinicId(String clinicId, int offset, int max) {
        return PatientVisitDetails.findAllByClinic(Clinic.findById(clinicId),[offset: offset, max: max])
    }

    @Override
    List<PatientVisitDetails> getAllByEpisodeId(String episodeId, int offset, int max) {
        def patientVisitDetails = PatientVisitDetails.findAllWhere(episode: Episode.findById(episodeId))
        return patientVisitDetails
    }
}
