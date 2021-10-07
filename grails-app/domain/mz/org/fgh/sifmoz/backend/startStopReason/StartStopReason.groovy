package mz.org.fgh.sifmoz.backend.startStopReason

import grails.rest.Resource

// @Resource(uri='/api/startStopReason')
class StartStopReason {

    boolean isStartReason
    String reason

    static constraints = {
        reason unique: true
    }
}
