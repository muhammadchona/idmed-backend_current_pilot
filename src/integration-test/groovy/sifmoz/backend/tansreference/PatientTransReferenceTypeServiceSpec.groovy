package sifmoz.backend.tansreference

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import mz.org.fgh.sifmoz.backend.tansreference.PatientTransReferenceType
import mz.org.fgh.sifmoz.backend.tansreference.PatientTransReferenceTypeService
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class PatientTransReferenceTypeServiceSpec extends Specification {

    PatientTransReferenceTypeService patientTransReferenceTypeService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new PatientTransReferenceType(...).save(flush: true, failOnError: true)
        //new PatientTransReferenceType(...).save(flush: true, failOnError: true)
        //PatientTransReferenceType patientTransReferenceType = new PatientTransReferenceType(...).save(flush: true, failOnError: true)
        //new PatientTransReferenceType(...).save(flush: true, failOnError: true)
        //new PatientTransReferenceType(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //patientTransReferenceType.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        patientTransReferenceTypeService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<PatientTransReferenceType> patientTransReferenceTypeList = patientTransReferenceTypeService.list(max: 2, offset: 2)

        then:
        patientTransReferenceTypeList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        patientTransReferenceTypeService.count() == 5
    }

    void "test delete"() {
        Long patientTransReferenceTypeId = setupData()

        expect:
        patientTransReferenceTypeService.count() == 5

        when:
        patientTransReferenceTypeService.delete(patientTransReferenceTypeId)
        datastore.currentSession.flush()

        then:
        patientTransReferenceTypeService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        PatientTransReferenceType patientTransReferenceType = new PatientTransReferenceType()
        patientTransReferenceTypeService.save(patientTransReferenceType)

        then:
        patientTransReferenceType.id != null
    }
}
