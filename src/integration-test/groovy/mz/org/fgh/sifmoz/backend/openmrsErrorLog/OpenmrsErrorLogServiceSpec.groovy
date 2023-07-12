package mz.org.fgh.sifmoz.backend.openmrsErrorLog

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class OpenmrsErrorLogServiceSpec extends Specification {

    OpenmrsErrorLogService openmrsErrorLogService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new OpenmrsErrorLog(...).save(flush: true, failOnError: true)
        //new OpenmrsErrorLog(...).save(flush: true, failOnError: true)
        //OpenmrsErrorLog openmrsErrorLog = new OpenmrsErrorLog(...).save(flush: true, failOnError: true)
        //new OpenmrsErrorLog(...).save(flush: true, failOnError: true)
        //new OpenmrsErrorLog(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //openmrsErrorLog.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        openmrsErrorLogService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<OpenmrsErrorLog> openmrsErrorLogList = openmrsErrorLogService.list(max: 2, offset: 2)

        then:
        openmrsErrorLogList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        openmrsErrorLogService.count() == 5
    }

    void "test delete"() {
        Long openmrsErrorLogId = setupData()

        expect:
        openmrsErrorLogService.count() == 5

        when:
        openmrsErrorLogService.delete(openmrsErrorLogId)
        datastore.currentSession.flush()

        then:
        openmrsErrorLogService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        OpenmrsErrorLog openmrsErrorLog = new OpenmrsErrorLog()
        openmrsErrorLogService.save(openmrsErrorLog)

        then:
        openmrsErrorLog.id != null
    }
}
