package mz.org.fgh.sifmoz.backend.patient

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import mz.org.fgh.sifmoz.backend.patient.vitalsigns.PatientVitalSigns
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class VitalSignsScreeningServiceSpec extends Specification {

    PatientVitalSignsService patientVitalSignsService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new PatientVitalSigns(...).save(flush: true, failOnError: true)
        //new PatientVitalSigns(...).save(flush: true, failOnError: true)
        //PatientVitalSigns patientVitalSigns = new PatientVitalSigns(...).save(flush: true, failOnError: true)
        //new PatientVitalSigns(...).save(flush: true, failOnError: true)
        //new PatientVitalSigns(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //patientVitalSigns.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        patientVitalSignsService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<PatientVitalSigns> patientVitalSignsList = patientVitalSignsService.list(max: 2, offset: 2)

        then:
        patientVitalSignsList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        patientVitalSignsService.count() == 5
    }

    void "test delete"() {
        Long patientVitalSignsId = setupData()

        expect:
        patientVitalSignsService.count() == 5

        when:
        patientVitalSignsService.delete(patientVitalSignsId)
        datastore.currentSession.flush()

        then:
        patientVitalSignsService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        PatientVitalSigns patientVitalSigns = new PatientVitalSigns()
        patientVitalSignsService.save(patientVitalSigns)

        then:
        patientVitalSigns.id != null
    }
}
