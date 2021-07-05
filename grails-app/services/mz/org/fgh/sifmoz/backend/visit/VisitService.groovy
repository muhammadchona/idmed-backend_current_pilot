package mz.org.fgh.sifmoz.backend.visit

import grails.gorm.services.Service

@Service(Visit)
interface VisitService {

    Visit get(Serializable id)

    List<Visit> list(Map args)

    Long count()

    Visit delete(Serializable id)

    Visit save(Visit visit)

}
