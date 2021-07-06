package mz.org.fgh.sifmoz.backend.packaging

import grails.gorm.services.Service

@Service(Pack)
interface PackService {

    Pack get(Serializable id)

    List<Pack> list(Map args)

    Long count()

    Pack delete(Serializable id)

    Pack save(Pack pack)

}
