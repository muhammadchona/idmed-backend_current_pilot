package mz.org.fgh.sifmoz.backend.prescription

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class PrescriptionDetailsServiceSpec extends Specification {

    PrescriptionDetailsService prescriptionDetailsService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new PrescriptionDetails(...).save(flush: true, failOnError: true)
        //new PrescriptionDetails(...).save(flush: true, failOnError: true)
        //PrescriptionDetails prescriptionDetails = new PrescriptionDetails(...).save(flush: true, failOnError: true)
        //new PrescriptionDetails(...).save(flush: true, failOnError: true)
        //new PrescriptionDetails(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //prescriptionDetails.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        prescriptionDetailsService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<PrescriptionDetails> prescriptionDetailsList = prescriptionDetailsService.list(max: 2, offset: 2)

        then:
        prescriptionDetailsList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        prescriptionDetailsService.count() == 5
    }

    void "test delete"() {
        Long prescriptionDetailsId = setupData()

        expect:
        prescriptionDetailsService.count() == 5

        when:
        prescriptionDetailsService.delete(prescriptionDetailsId)
        datastore.currentSession.flush()

        then:
        prescriptionDetailsService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        PrescriptionDetails prescriptionDetails = new PrescriptionDetails()
        prescriptionDetailsService.save(prescriptionDetails)

        then:
        prescriptionDetails.id != null
    }
}
