package mz.org.fgh.sifmoz.backend.packaging

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.clinic.Clinic

@Transactional
@Service(Pack)
interface PackService {
    Pack get(Serializable id)

    List<Pack> list(Map args)

    Long count()

    Pack delete(Serializable id)

    Pack save(Pack pack)

  //  List<Pack> getAllByClinicId(String clinicId, int offset, int max)

    //List<Pack> getAllPacksReadyToSend(String status)

}
