package mz.org.fgh.sifmoz.backend.program

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class ProgramAttributeTypeServiceSpec extends Specification {

    ProgramAttributeTypeService programAttributeTypeService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new ProgramAttributeType(...).save(flush: true, failOnError: true)
        //new ProgramAttributeType(...).save(flush: true, failOnError: true)
        //ProgramAttributeType programAttributeType = new ProgramAttributeType(...).save(flush: true, failOnError: true)
        //new ProgramAttributeType(...).save(flush: true, failOnError: true)
        //new ProgramAttributeType(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //programAttributeType.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        programAttributeTypeService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<ProgramAttributeType> programAttributeTypeList = programAttributeTypeService.list(max: 2, offset: 2)

        then:
        programAttributeTypeList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        programAttributeTypeService.count() == 5
    }

    void "test delete"() {
        Long programAttributeTypeId = setupData()

        expect:
        programAttributeTypeService.count() == 5

        when:
        programAttributeTypeService.delete(programAttributeTypeId)
        datastore.currentSession.flush()

        then:
        programAttributeTypeService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        ProgramAttributeType programAttributeType = new ProgramAttributeType()
        programAttributeTypeService.save(programAttributeType)

        then:
        programAttributeType.id != null
    }
}
