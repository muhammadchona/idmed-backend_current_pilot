package mz.org.fgh.sifmoz.backend.clinic

import grails.gorm.services.Service

@Service(NationalClinics)
interface NationalClinicsService {

    NationalClinics get(Serializable id)

    List<NationalClinics> list(Map args)

    Long count()

    NationalClinics delete(Serializable id)

    NationalClinics save(NationalClinics nationalClinics)

}
