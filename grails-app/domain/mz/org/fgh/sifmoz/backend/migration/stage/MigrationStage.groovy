package mz.org.fgh.sifmoz.backend.migration.stage

import mz.org.fgh.sifmoz.backend.base.BaseEntity

class MigrationStage {

    String id

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
    }

}
