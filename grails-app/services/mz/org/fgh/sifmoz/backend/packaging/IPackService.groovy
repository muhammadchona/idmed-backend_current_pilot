package mz.org.fgh.sifmoz.backend.packaging

import grails.gorm.services.Service


interface IPackService {

    Pack get(Serializable id)

    List<Pack> list(Map args)

    Long count()

    Pack delete(Serializable id)

    Pack save(Pack pack)

    List<Pack> getAllByClinicId(String clinicId, int offset, int max)

    List<Pack> getAllPacksReadyToSend(char status)

}
