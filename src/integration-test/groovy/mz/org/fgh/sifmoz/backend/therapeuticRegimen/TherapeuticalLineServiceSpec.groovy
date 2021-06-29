package mz.org.fgh.sifmoz.backend.therapeuticRegimen

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class TherapeuticalLineServiceSpec extends Specification {

    TherapeuticalLineService therapeuticalLineService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new TherapeuticalLine(...).save(flush: true, failOnError: true)
        //new TherapeuticalLine(...).save(flush: true, failOnError: true)
        //TherapeuticalLine therapeuticalLine = new TherapeuticalLine(...).save(flush: true, failOnError: true)
        //new TherapeuticalLine(...).save(flush: true, failOnError: true)
        //new TherapeuticalLine(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //therapeuticalLine.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        therapeuticalLineService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<TherapeuticalLine> therapeuticalLineList = therapeuticalLineService.list(max: 2, offset: 2)

        then:
        therapeuticalLineList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        therapeuticalLineService.count() == 5
    }

    void "test delete"() {
        Long therapeuticalLineId = setupData()

        expect:
        therapeuticalLineService.count() == 5

        when:
        therapeuticalLineService.delete(therapeuticalLineId)
        datastore.currentSession.flush()

        then:
        therapeuticalLineService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        TherapeuticalLine therapeuticalLine = new TherapeuticalLine()
        therapeuticalLineService.save(therapeuticalLine)

        then:
        therapeuticalLine.id != null
    }
}
