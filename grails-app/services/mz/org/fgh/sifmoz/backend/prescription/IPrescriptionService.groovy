package mz.org.fgh.sifmoz.backend.prescription

import grails.gorm.services.Service
import mz.org.fgh.sifmoz.backend.patient.Patient


interface IPrescriptionService {

    Prescription get(Serializable id)

    List<Prescription> list(Map args)

    Long count()

    Prescription delete(Serializable id)

    Prescription save(Prescription prescription)

    List<Prescription> getAllByClinicId(String clinicId, int offset, int max)

    Prescription getByVisitIds(String pvdsId, int offset, int max)

}
