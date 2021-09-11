package mz.org.fgh.sifmoz.backend.packaging

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class PackClinicalTreatmentSpec extends Specification {

    PackService packService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Pack(...).save(flush: true, failOnError: true)
        //new Pack(...).save(flush: true, failOnError: true)
        //Pack pack = new Pack(...).save(flush: true, failOnError: true)
        //new Pack(...).save(flush: true, failOnError: true)
        //new Pack(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //pack.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        packService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Pack> packList = packService.list(max: 2, offset: 2)

        then:
        packList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        packService.count() == 5
    }

    void "test delete"() {
        Long packId = setupData()

        expect:
        packService.count() == 5

        when:
        packService.delete(packId)
        datastore.currentSession.flush()

        then:
        packService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Pack pack = new Pack()
        packService.save(pack)

        then:
        pack.id != null
    }
}
