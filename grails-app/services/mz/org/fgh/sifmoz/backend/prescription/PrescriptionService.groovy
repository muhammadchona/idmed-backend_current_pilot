package mz.org.fgh.sifmoz.backend.prescription

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.patientVisitDetails.PatientVisitDetails

@Transactional
@Service(Prescription)
abstract class PrescriptionService implements IPrescriptionService{
    @Override
    List<Prescription> getAllByClinicId(String clinicId, int offset, int max) {
        return Prescription.findAllByClinic(Clinic.findById(clinicId),[offset: offset, max: max])
    }
}
