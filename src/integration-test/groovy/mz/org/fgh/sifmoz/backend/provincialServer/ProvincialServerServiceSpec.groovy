package mz.org.fgh.sifmoz.backend.provincialServer

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class ProvincialServerServiceSpec extends Specification {

    ProvincialServerService provincialServerService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new ProvincialServer(...).save(flush: true, failOnError: true)
        //new ProvincialServer(...).save(flush: true, failOnError: true)
        //ProvincialServer provincialServer = new ProvincialServer(...).save(flush: true, failOnError: true)
        //new ProvincialServer(...).save(flush: true, failOnError: true)
        //new ProvincialServer(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //provincialServer.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        provincialServerService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<ProvincialServer> provincialServerList = provincialServerService.list(max: 2, offset: 2)

        then:
        provincialServerList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        provincialServerService.count() == 5
    }

    void "test delete"() {
        Long provincialServerId = setupData()

        expect:
        provincialServerService.count() == 5

        when:
        provincialServerService.delete(provincialServerId)
        datastore.currentSession.flush()

        then:
        provincialServerService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        ProvincialServer provincialServer = new ProvincialServer()
        provincialServerService.save(provincialServer)

        then:
        provincialServer.id != null
    }
}
