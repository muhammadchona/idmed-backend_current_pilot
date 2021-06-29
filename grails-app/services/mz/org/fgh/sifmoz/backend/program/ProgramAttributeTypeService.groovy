package mz.org.fgh.sifmoz.backend.program

import grails.gorm.services.Service

@Service(ProgramAttributeType)
interface ProgramAttributeTypeService {

    ProgramAttributeType get(Serializable id)

    List<ProgramAttributeType> list(Map args)

    Long count()

    ProgramAttributeType delete(Serializable id)

    ProgramAttributeType save(ProgramAttributeType programAttributeType)

}
