package mz.org.fgh.sifmoz.backend.stock.inventory

import grails.gorm.services.Service

@Service(Inventory)
interface InventoryService {

    Inventory get(Serializable id)

    List<Inventory> list(Map args)

    Long count()

    Inventory delete(Serializable id)

    Inventory save(Inventory inventory)

}
