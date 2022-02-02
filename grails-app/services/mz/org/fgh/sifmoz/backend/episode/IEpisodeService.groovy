package mz.org.fgh.sifmoz.backend.episode

interface IEpisodeService {

    Episode get(Serializable id)

    List<Episode> list(Map args)

    Long count()

    Episode delete(Serializable id)

    Episode save(Episode episode)

    List<Episode> getAllByClinicId(String clinicId, int offset, int max)

    List<Episode> getAllByIndentifier(String identifierId, int offset, int max)

}
