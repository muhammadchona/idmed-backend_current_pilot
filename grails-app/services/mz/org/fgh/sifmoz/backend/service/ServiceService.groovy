package mz.org.fgh.sifmoz.backend.service

import grails.gorm.services.Service

@Service(mz.org.fgh.sifmoz.backend.service.Service)
interface ServiceService {

    mz.org.fgh.sifmoz.backend.service.Service get(Serializable id)

    List<mz.org.fgh.sifmoz.backend.service.Service> list(Map args)

    Long count()

    mz.org.fgh.sifmoz.backend.service.Service delete(Serializable id)

    mz.org.fgh.sifmoz.backend.service.Service save(mz.org.fgh.sifmoz.backend.service.Service program)

}
