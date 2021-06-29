package mz.org.fgh.sifmoz.backend.program

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class ProgramServiceSpec extends Specification {

    ProgramService programService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Program(...).save(flush: true, failOnError: true)
        //new Program(...).save(flush: true, failOnError: true)
        //Program program = new Program(...).save(flush: true, failOnError: true)
        //new Program(...).save(flush: true, failOnError: true)
        //new Program(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //program.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        programService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Program> programList = programService.list(max: 2, offset: 2)

        then:
        programList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        programService.count() == 5
    }

    void "test delete"() {
        Long programId = setupData()

        expect:
        programService.count() == 5

        when:
        programService.delete(programId)
        datastore.currentSession.flush()

        then:
        programService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Program program = new Program()
        programService.save(program)

        then:
        program.id != null
    }
}
