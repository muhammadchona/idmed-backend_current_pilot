package mz.org.fgh.sifmoz.backend.migration.stage

import mz.org.fgh.sifmoz.backend.base.BaseEntity

class MigrationStage {

    String id
    String code
    String value

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        code unique: true
    }

}
