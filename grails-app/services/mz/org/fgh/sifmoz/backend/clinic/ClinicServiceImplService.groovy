package mz.org.fgh.sifmoz.backend.clinic

import grails.gorm.transactions.Transactional

@Transactional
class ClinicServiceImplService implements ClinicService {

    def serviceMethod() {

    }

    @Override
    Clinic get(Serializable id) {
        return null
    }

    @Override
    List<Clinic> list(Map args) {
        return null
    }

    @Override
    Long count() {
        return null
    }

    @Override
    Clinic delete(Serializable id) {
        return null
    }

    @Override
    Clinic save(Clinic clinic) {
        return null
    }

    @Override
    Clinic findClinicByCode(String code) {
        return null
    }

    @Override
    Clinic findClinicByUuid(String uuid) {
        return Clinic.findByUuid(uuid)
    }
}
