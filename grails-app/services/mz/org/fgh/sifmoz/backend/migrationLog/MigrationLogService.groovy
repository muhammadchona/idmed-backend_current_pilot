package mz.org.fgh.sifmoz.backend.migrationLog

import grails.gorm.services.Service

@Service(MigrationLog)
interface MigrationLogService {

    MigrationLog get(Serializable id)

    List<MigrationLog> list(Map args)

    Long count()

    MigrationLog delete(Serializable id)

    MigrationLog save(MigrationLog migrationLog)

}
