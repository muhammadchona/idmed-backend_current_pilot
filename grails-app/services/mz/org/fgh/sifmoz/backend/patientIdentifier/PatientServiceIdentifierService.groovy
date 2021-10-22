package mz.org.fgh.sifmoz.backend.patientIdentifier

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.clinic.Clinic

@Transactional
@Service(PatientServiceIdentifier)
abstract class PatientServiceIdentifierService implements IPatientServiceIdentifierService{
    @Override
    List<PatientServiceIdentifier> getAllByClinicId(String clinicId, int offset, int max) {
        return PatientServiceIdentifier.findAllByClinic(Clinic.findById(clinicId),[offset: offset, max: max])
    }
}
