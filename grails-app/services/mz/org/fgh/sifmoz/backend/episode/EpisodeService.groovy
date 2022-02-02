package mz.org.fgh.sifmoz.backend.episode

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.patient.Patient
import mz.org.fgh.sifmoz.backend.patientIdentifier.PatientServiceIdentifier

@Transactional
@Service(Episode)
abstract class EpisodeService implements IEpisodeService{

    @Override
    List<Episode> getAllByClinicId(String clinicId, int offset, int max) {
        return Episode.findAllByClinic(Clinic.findById(clinicId),[offset: offset, max: max])
    }
    @Override
    List<Episode> getAllByIndentifier(String identifierId, int offset, int max) {
        def episodes = Episode.findAllWhere(patientServiceIdentifier: PatientServiceIdentifier.findById(identifierId))
        return episodes
    }
}
