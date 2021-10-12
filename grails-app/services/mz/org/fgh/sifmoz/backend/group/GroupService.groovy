package mz.org.fgh.sifmoz.backend.group

import grails.gorm.services.Service

@Service(GroupInfo)
interface GroupService {

    GroupInfo get(Serializable id)

    List<GroupInfo> list(Map args)

    Long count()

    GroupInfo delete(Serializable id)

    GroupInfo save(GroupInfo group)

}
