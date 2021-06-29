package mz.org.fgh.sifmoz.backend.dispense

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class DispenseServiceSpec extends Specification {

    DispenseService dispenseService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Dispense(...).save(flush: true, failOnError: true)
        //new Dispense(...).save(flush: true, failOnError: true)
        //Dispense dispense = new Dispense(...).save(flush: true, failOnError: true)
        //new Dispense(...).save(flush: true, failOnError: true)
        //new Dispense(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //dispense.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        dispenseService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Dispense> dispenseList = dispenseService.list(max: 2, offset: 2)

        then:
        dispenseList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        dispenseService.count() == 5
    }

    void "test delete"() {
        Long dispenseId = setupData()

        expect:
        dispenseService.count() == 5

        when:
        dispenseService.delete(dispenseId)
        datastore.currentSession.flush()

        then:
        dispenseService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Dispense dispense = new Dispense()
        dispenseService.save(dispense)

        then:
        dispense.id != null
    }
}
