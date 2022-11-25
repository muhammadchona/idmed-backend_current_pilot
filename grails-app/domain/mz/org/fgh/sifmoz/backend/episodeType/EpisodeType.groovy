package mz.org.fgh.sifmoz.backend.episodeType


import mz.org.fgh.sifmoz.backend.base.BaseEntity

class EpisodeType extends BaseEntity {
    String id
    String code
    String description

    static mapping = {
        id generator: "assigned"
    }

    def beforeInsert() {
        if (!id) {
            id = UUID.randomUUID()
        }
    }

    static constraints = {
        code nullable: false
        description nullable: false
    }
}
