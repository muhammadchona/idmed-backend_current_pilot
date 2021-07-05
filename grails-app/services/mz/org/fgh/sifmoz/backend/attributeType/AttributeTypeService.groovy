package mz.org.fgh.sifmoz.backend.attributeType

import grails.gorm.services.Service

@Service(AttributeType)
interface AttributeTypeService {

    AttributeType get(Serializable id)

    List<AttributeType> list(Map args)

    Long count()

    AttributeType delete(Serializable id)

    AttributeType save(AttributeType attributeType)

}
