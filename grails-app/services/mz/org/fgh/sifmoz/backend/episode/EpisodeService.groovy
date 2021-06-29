package mz.org.fgh.sifmoz.backend.episode

import grails.gorm.services.Service

@Service(Episode)
interface EpisodeService {

    Episode get(Serializable id)

    List<Episode> list(Map args)

    Long count()

    Episode delete(Serializable id)

    Episode save(Episode episode)

}
