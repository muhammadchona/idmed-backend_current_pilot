package mz.org.fgh.sifmoz.backend.dispense

import grails.gorm.services.Service

@Service(Dispense)
interface DispenseService {

    Dispense get(Serializable id)

    List<Dispense> list(Map args)

    Long count()

    Dispense delete(Serializable id)

    Dispense save(Dispense dispense)

}
