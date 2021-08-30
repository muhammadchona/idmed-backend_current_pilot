package mz.org.fgh.sifmoz.backend.serviceAttributeType

import grails.gorm.services.Service

@Service(ServiceAttributeType)
interface ServiceAttributeTypeService {

    ServiceAttributeType get(Serializable id)

    List<ServiceAttributeType> list(Map args)

    Long count()

    ServiceAttributeType delete(Serializable id)

    ServiceAttributeType save(ServiceAttributeType programAttributeType)

}
