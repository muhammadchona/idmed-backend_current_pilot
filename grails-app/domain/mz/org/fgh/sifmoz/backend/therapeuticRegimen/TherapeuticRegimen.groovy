package mz.org.fgh.sifmoz.backend.therapeuticRegimen

import grails.rest.Resource

@Resource(uri = '/api/therapeuticRegimen')
class TherapeuticRegimen {

    String regimenScheme
    boolean active
    String code
    boolean pedhiatric
    boolean adult
    boolean description

    static constraints = {
    }
}
