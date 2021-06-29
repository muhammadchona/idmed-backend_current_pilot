package mz.org.fgh.sifmoz.backend.appointment

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class VisitServiceSpec extends Specification {

    VisitService visitService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Visit(...).save(flush: true, failOnError: true)
        //new Visit(...).save(flush: true, failOnError: true)
        //Visit visit = new Visit(...).save(flush: true, failOnError: true)
        //new Visit(...).save(flush: true, failOnError: true)
        //new Visit(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //visit.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        visitService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Visit> visitList = visitService.list(max: 2, offset: 2)

        then:
        visitList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        visitService.count() == 5
    }

    void "test delete"() {
        Long visitId = setupData()

        expect:
        visitService.count() == 5

        when:
        visitService.delete(visitId)
        datastore.currentSession.flush()

        then:
        visitService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Visit visit = new Visit()
        visitService.save(visit)

        then:
        visit.id != null
    }
}
