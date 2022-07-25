package sifmoz.backend.tansreference

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import mz.org.fgh.sifmoz.backend.tansreference.PatientTransReference
import mz.org.fgh.sifmoz.backend.tansreference.IPatientTransReferenceService
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class IPatientTransReferenceServiceSpec extends Specification {

    IPatientTransReferenceService patientTransReferenceService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new PatientTransReference(...).save(flush: true, failOnError: true)
        //new PatientTransReference(...).save(flush: true, failOnError: true)
        //PatientTransReference patientTransReference = new PatientTransReference(...).save(flush: true, failOnError: true)
        //new PatientTransReference(...).save(flush: true, failOnError: true)
        //new PatientTransReference(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //patientTransReference.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        patientTransReferenceService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<PatientTransReference> patientTransReferenceList = patientTransReferenceService.list(max: 2, offset: 2)

        then:
        patientTransReferenceList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        patientTransReferenceService.count() == 5
    }

    void "test delete"() {
        Long patientTransReferenceId = setupData()

        expect:
        patientTransReferenceService.count() == 5

        when:
        patientTransReferenceService.delete(patientTransReferenceId)
        datastore.currentSession.flush()

        then:
        patientTransReferenceService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        PatientTransReference patientTransReference = new PatientTransReference()
        patientTransReferenceService.save(patientTransReference)

        then:
        patientTransReference.id != null
    }
}
