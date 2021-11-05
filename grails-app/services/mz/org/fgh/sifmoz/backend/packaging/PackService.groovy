package mz.org.fgh.sifmoz.backend.packaging

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.patient.Patient

@Transactional
@Service(Pack)
abstract class PackService implements IPackService{
    @Override
    List<Pack> getAllByClinicId(String clinicId, int offset, int max) {
        return Pack.findAllByClinic(Clinic.findById(clinicId),[offset: offset, max: max])
    }
}
