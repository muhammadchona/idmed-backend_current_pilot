package mz.org.fgh.sifmoz.backend.therapeuticRegimen

import grails.rest.Resource

// @Resource(uri = '/api/therapeuticRegimen')
class TherapeuticRegimen {

    String regimenScheme
    boolean active
    String code
    boolean pedhiatric
    String description

    static constraints = {
        code nullable: false, unique: true
        regimenScheme nullable: false
        description nullable: true
    }
}
