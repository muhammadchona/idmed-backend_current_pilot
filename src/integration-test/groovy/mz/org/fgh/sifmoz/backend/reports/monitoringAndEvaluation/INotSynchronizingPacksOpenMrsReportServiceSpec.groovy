package mz.org.fgh.sifmoz.backend.reports.monitoringAndEvaluation

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class INotSynchronizingPacksOpenMrsReportServiceSpec extends Specification {

    INotSynchronizingPacksOpenMrsReportService notSynchronizingPacksOpenMrsReportService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new NotSynchronizingPacksOpenMrsReport(...).save(flush: true, failOnError: true)
        //new NotSynchronizingPacksOpenMrsReport(...).save(flush: true, failOnError: true)
        //NotSynchronizingPacksOpenMrsReport notSynchronizingPacksOpenMrsReport = new NotSynchronizingPacksOpenMrsReport(...).save(flush: true, failOnError: true)
        //new NotSynchronizingPacksOpenMrsReport(...).save(flush: true, failOnError: true)
        //new NotSynchronizingPacksOpenMrsReport(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //notSynchronizingPacksOpenMrsReport.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        notSynchronizingPacksOpenMrsReportService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<NotSynchronizingPacksOpenMrsReport> notSynchronizingPacksOpenMrsReportList = notSynchronizingPacksOpenMrsReportService.list(max: 2, offset: 2)

        then:
        notSynchronizingPacksOpenMrsReportList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        notSynchronizingPacksOpenMrsReportService.count() == 5
    }

    void "test delete"() {
        Long notSynchronizingPacksOpenMrsReportId = setupData()

        expect:
        notSynchronizingPacksOpenMrsReportService.count() == 5

        when:
        notSynchronizingPacksOpenMrsReportService.delete(notSynchronizingPacksOpenMrsReportId)
        datastore.currentSession.flush()

        then:
        notSynchronizingPacksOpenMrsReportService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        NotSynchronizingPacksOpenMrsReport notSynchronizingPacksOpenMrsReport = new NotSynchronizingPacksOpenMrsReport()
        notSynchronizingPacksOpenMrsReportService.save(notSynchronizingPacksOpenMrsReport)

        then:
        notSynchronizingPacksOpenMrsReport.id != null
    }
}
