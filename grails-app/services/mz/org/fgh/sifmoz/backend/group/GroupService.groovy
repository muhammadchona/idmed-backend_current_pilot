package mz.org.fgh.sifmoz.backend.group

import grails.gorm.services.Service

@Service(Group)
interface GroupService {

    Group get(Serializable id)

    List<Group> list(Map args)

    Long count()

    Group delete(Serializable id)

    Group save(Group group)

}
