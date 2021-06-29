package mz.org.fgh.sifmoz.backend.clinic

import grails.gorm.services.Service

@Service(ClinicSector)
interface ClinicSectorService {

    ClinicSector get(Serializable id)

    List<ClinicSector> list(Map args)

    Long count()

    ClinicSector delete(Serializable id)

    ClinicSector save(ClinicSector clinicSector)

}
