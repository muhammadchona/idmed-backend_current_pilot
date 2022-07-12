package mz.org.fgh.sifmoz.backend.prescription

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class SpetialPrescriptionMotiveServiceSpec extends Specification {

    SpetialPrescriptionMotiveService spetialPrescriptionMotiveService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new SpetialPrescriptionMotive(...).save(flush: true, failOnError: true)
        //new SpetialPrescriptionMotive(...).save(flush: true, failOnError: true)
        //SpetialPrescriptionMotive spetialPrescriptionMotive = new SpetialPrescriptionMotive(...).save(flush: true, failOnError: true)
        //new SpetialPrescriptionMotive(...).save(flush: true, failOnError: true)
        //new SpetialPrescriptionMotive(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //spetialPrescriptionMotive.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        spetialPrescriptionMotiveService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<SpetialPrescriptionMotive> spetialPrescriptionMotiveList = spetialPrescriptionMotiveService.list(max: 2, offset: 2)

        then:
        spetialPrescriptionMotiveList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        spetialPrescriptionMotiveService.count() == 5
    }

    void "test delete"() {
        Long spetialPrescriptionMotiveId = setupData()

        expect:
        spetialPrescriptionMotiveService.count() == 5

        when:
        spetialPrescriptionMotiveService.delete(spetialPrescriptionMotiveId)
        datastore.currentSession.flush()

        then:
        spetialPrescriptionMotiveService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        SpetialPrescriptionMotive spetialPrescriptionMotive = new SpetialPrescriptionMotive()
        spetialPrescriptionMotiveService.save(spetialPrescriptionMotive)

        then:
        spetialPrescriptionMotive.id != null
    }
}
