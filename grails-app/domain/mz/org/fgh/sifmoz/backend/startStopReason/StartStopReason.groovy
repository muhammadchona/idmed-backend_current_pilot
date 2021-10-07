package mz.org.fgh.sifmoz.backend.startStopReason

class StartStopReason {
    String id
    boolean isStartReason
    String reason

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        reason unique: true
    }
}
