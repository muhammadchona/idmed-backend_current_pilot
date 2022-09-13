package mz.org.fgh.sifmoz.backend.group

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class GroupPrescriptionServiceSpec extends Specification {

    GroupPrescriptionService groupPrescriptionService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new GroupPrescription(...).save(flush: true, failOnError: true)
        //new GroupPrescription(...).save(flush: true, failOnError: true)
        //GroupPrescription groupPrescription = new GroupPrescription(...).save(flush: true, failOnError: true)
        //new GroupPrescription(...).save(flush: true, failOnError: true)
        //new GroupPrescription(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //groupPrescription.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        groupPrescriptionService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<GroupMemberPrescription> groupPrescriptionList = groupPrescriptionService.list(max: 2, offset: 2)

        then:
        groupPrescriptionList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        groupPrescriptionService.count() == 5
    }

    void "test delete"() {
        Long groupPrescriptionId = setupData()

        expect:
        groupPrescriptionService.count() == 5

        when:
        groupPrescriptionService.delete(groupPrescriptionId)
        datastore.currentSession.flush()

        then:
        groupPrescriptionService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        GroupMemberPrescription groupPrescription = new GroupMemberPrescription()
        groupPrescriptionService.save(groupPrescription)

        then:
        groupPrescription.id != null
    }
}
