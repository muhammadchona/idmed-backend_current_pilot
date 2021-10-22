package mz.org.fgh.sifmoz.backend.episode

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.clinic.Clinic

@Transactional
@Service(Episode)
abstract class EpisodeService implements IEpisodeService{

    @Override
    List<Episode> getAllByClinicId(String clinicId, int offset, int max) {
        return Episode.findAllByClinic(Clinic.findById(clinicId),[offset: offset, max: max])
    }
}
