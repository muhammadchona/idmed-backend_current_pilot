package mz.org.fgh.sifmoz.backend.healthInformationSystem

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class ISystemConfigsServiceSpec extends Specification {

    ISystemConfigsService systemConfigsService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new SystemConfigs(...).save(flush: true, failOnError: true)
        //new SystemConfigs(...).save(flush: true, failOnError: true)
        //SystemConfigs systemConfigs = new SystemConfigs(...).save(flush: true, failOnError: true)
        //new SystemConfigs(...).save(flush: true, failOnError: true)
        //new SystemConfigs(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //systemConfigs.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        systemConfigsService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<SystemConfigs> systemConfigsList = systemConfigsService.list(max: 2, offset: 2)

        then:
        systemConfigsList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        systemConfigsService.count() == 5
    }

    void "test delete"() {
        Long systemConfigsId = setupData()

        expect:
        systemConfigsService.count() == 5

        when:
        systemConfigsService.delete(systemConfigsId)
        datastore.currentSession.flush()

        then:
        systemConfigsService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        SystemConfigs systemConfigs = new SystemConfigs()
        systemConfigsService.save(systemConfigs)

        then:
        systemConfigs.id != null
    }
}
