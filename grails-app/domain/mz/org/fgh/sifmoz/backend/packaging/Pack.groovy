package mz.org.fgh.sifmoz.backend.packaging

import grails.rest.Resource


@Resource(uri='/api/pack')
class Pack {

    Date dateLeft
    Date dateReceived
    boolean modified
    Date packDate
    Date pickupDate
    int weeksSupply
    Date dateReturned
    int stockReturned
    int packageReturned
    String reasonForPackageReturn
    String uuid = UUID.randomUUID().toString()

    static constraints = {
    }
}
