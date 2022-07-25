package mz.org.fgh.sifmoz.backend.migration.stage

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class MigrationStageServiceSpec extends Specification {

    MigrationStageService migrationStageService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new MigrationStage(...).save(flush: true, failOnError: true)
        //new MigrationStage(...).save(flush: true, failOnError: true)
        //MigrationStage migrationStage = new MigrationStage(...).save(flush: true, failOnError: true)
        //new MigrationStage(...).save(flush: true, failOnError: true)
        //new MigrationStage(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //migrationStage.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        migrationStageService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<MigrationStage> migrationStageList = migrationStageService.list(max: 2, offset: 2)

        then:
        migrationStageList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        migrationStageService.count() == 5
    }

    void "test delete"() {
        Long migrationStageId = setupData()

        expect:
        migrationStageService.count() == 5

        when:
        migrationStageService.delete(migrationStageId)
        datastore.currentSession.flush()

        then:
        migrationStageService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        MigrationStage migrationStage = new MigrationStage()
        migrationStageService.save(migrationStage)

        then:
        migrationStage.id != null
    }
}
