package mz.org.fgh.sifmoz.backend.therapeuticRegimen

import grails.gorm.services.Service

@Service(TherapeuticalLine)
interface TherapeuticalLineService {

    TherapeuticalLine get(Serializable id)

    List<TherapeuticalLine> list(Map args)

    Long count()

    TherapeuticalLine delete(Serializable id)

    TherapeuticalLine save(TherapeuticalLine therapeuticalLine)

}
