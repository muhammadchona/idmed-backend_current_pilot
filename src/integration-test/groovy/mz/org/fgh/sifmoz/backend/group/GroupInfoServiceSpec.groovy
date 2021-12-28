package mz.org.fgh.sifmoz.backend.group

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class GroupInfoServiceSpec extends Specification {

    GroupInfoService groupInfoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new GroupInfo(...).save(flush: true, failOnError: true)
        //new GroupInfo(...).save(flush: true, failOnError: true)
        //GroupInfo groupInfo = new GroupInfo(...).save(flush: true, failOnError: true)
        //new GroupInfo(...).save(flush: true, failOnError: true)
        //new GroupInfo(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //groupInfo.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        groupInfoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<GroupInfo> groupInfoList = groupInfoService.list(max: 2, offset: 2)

        then:
        groupInfoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        groupInfoService.count() == 5
    }

    void "test delete"() {
        Long groupInfoId = setupData()

        expect:
        groupInfoService.count() == 5

        when:
        groupInfoService.delete(groupInfoId)
        datastore.currentSession.flush()

        then:
        groupInfoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        GroupInfo groupInfo = new GroupInfo()
        groupInfoService.save(groupInfo)

        then:
        groupInfo.id != null
    }
}
