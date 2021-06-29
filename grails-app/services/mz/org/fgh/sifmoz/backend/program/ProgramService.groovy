package mz.org.fgh.sifmoz.backend.program

import grails.gorm.services.Service

@Service(Program)
interface ProgramService {

    Program get(Serializable id)

    List<Program> list(Map args)

    Long count()

    Program delete(Serializable id)

    Program save(Program program)

}
