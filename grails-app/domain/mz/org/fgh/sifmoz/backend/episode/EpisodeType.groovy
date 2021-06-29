package mz.org.fgh.sifmoz.backend.episode

import grails.rest.Resource


@Resource(uri='/api/episodeType')
class EpisodeType {

    String code
    String description

    static mapping = {
        version false
    }

    static constraints = {
        code nullable: false, unique: true
        description nullable: false
    }

    @Override
    String toString() {
        description
    }
}
