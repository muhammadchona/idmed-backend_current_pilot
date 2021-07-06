package mz.org.fgh.sifmoz.backend.dispensedDrug

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class DispensedDrugServiceSpec extends Specification {

    DispensedDrugService dispensedDrugService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new DispensedDrug(...).save(flush: true, failOnError: true)
        //new DispensedDrug(...).save(flush: true, failOnError: true)
        //DispensedDrug dispensedDrug = new DispensedDrug(...).save(flush: true, failOnError: true)
        //new DispensedDrug(...).save(flush: true, failOnError: true)
        //new DispensedDrug(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //dispensedDrug.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        dispensedDrugService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<DispensedDrug> dispensedDrugList = dispensedDrugService.list(max: 2, offset: 2)

        then:
        dispensedDrugList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        dispensedDrugService.count() == 5
    }

    void "test delete"() {
        Long dispensedDrugId = setupData()

        expect:
        dispensedDrugService.count() == 5

        when:
        dispensedDrugService.delete(dispensedDrugId)
        datastore.currentSession.flush()

        then:
        dispensedDrugService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        DispensedDrug dispensedDrug = new DispensedDrug()
        dispensedDrugService.save(dispensedDrug)

        then:
        dispensedDrug.id != null
    }
}
