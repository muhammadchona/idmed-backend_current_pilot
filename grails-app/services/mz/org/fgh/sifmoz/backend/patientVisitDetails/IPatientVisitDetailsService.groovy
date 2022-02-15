package mz.org.fgh.sifmoz.backend.patientVisitDetails

import grails.gorm.services.Service
import mz.org.fgh.sifmoz.backend.packaging.Pack
import mz.org.fgh.sifmoz.backend.patient.Patient


interface IPatientVisitDetailsService {

    PatientVisitDetails get(Serializable id)

    List<PatientVisitDetails> list(Map args)

    Long count()

    PatientVisitDetails delete(Serializable id)

    PatientVisitDetails save(PatientVisitDetails patientVisitDetails)

    List<PatientVisitDetails> getAllByClinicId(String clinicId, int offset, int max)

    List<PatientVisitDetails> getAllByEpisodeId(String episodeId, int offset, int max)

    PatientVisitDetails getByPack(Pack pack)

}
