package mz.org.fgh.sifmoz.backend.stockadjustment

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class StockAdjustmentServiceSpec extends Specification {

    StockAdjustmentService stockAdjustmentService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new StockAdjustment(...).save(flush: true, failOnError: true)
        //new StockAdjustment(...).save(flush: true, failOnError: true)
        //StockAdjustment stockAdjustment = new StockAdjustment(...).save(flush: true, failOnError: true)
        //new StockAdjustment(...).save(flush: true, failOnError: true)
        //new StockAdjustment(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //stockAdjustment.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        stockAdjustmentService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<StockAdjustment> stockAdjustmentList = stockAdjustmentService.list(max: 2, offset: 2)

        then:
        stockAdjustmentList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        stockAdjustmentService.count() == 5
    }

    void "test delete"() {
        Long stockAdjustmentId = setupData()

        expect:
        stockAdjustmentService.count() == 5

        when:
        stockAdjustmentService.delete(stockAdjustmentId)
        datastore.currentSession.flush()

        then:
        stockAdjustmentService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        StockAdjustment stockAdjustment = new StockAdjustment()
        stockAdjustmentService.save(stockAdjustment)

        then:
        stockAdjustment.id != null
    }
}
