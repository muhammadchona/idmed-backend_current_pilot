package mz.org.fgh.sifmoz.backend.patientVisitDetails

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.episode.Episode
import mz.org.fgh.sifmoz.backend.packaging.Pack
import mz.org.fgh.sifmoz.backend.reports.monitoringAndEvaluation.DrugQuantityTemp
import mz.org.fgh.sifmoz.backend.stock.Stock
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired

@Transactional
@Service(PatientVisitDetails)
abstract class PatientVisitDetailsService implements IPatientVisitDetailsService {

    @Autowired
    SessionFactory sessionFactory

    @Override
    List<PatientVisitDetails> getAllByClinicId(String clinicId, int offset, int max) {
        return PatientVisitDetails.findAllByClinic(Clinic.findById(clinicId), [offset: offset, max: max])
    }

    @Override
    List<PatientVisitDetails> getAllLastVisitOfClinic(String clinicId, int offset, int max) {
        Session session = sessionFactory.getCurrentSession()

        String queryString = "select *  " +
                "from patient_last_visit_details_vw  " +
                "where clinic_id = :clinic offset :offset limit :max "


        def query = session.createSQLQuery(queryString).addEntity(PatientVisitDetails.class)
        query.setParameter("clinic", clinicId)
        query.setParameter("offset", offset)
        query.setParameter("max", max)
        List<PatientVisitDetails> result = query.list()
        return result
    }

    @Override
    List<PatientVisitDetails> getAllByEpisodeId(String episodeId, int offset, int max) {
        def patientVisitDetails = PatientVisitDetails.findAllByEpisode(Episode.findById(episodeId))
        return patientVisitDetails
    }

    @Override
    PatientVisitDetails getByPack(Pack pack) {
        return PatientVisitDetails.findByPack(pack)
    }

    @Override
    List<PatientVisitDetails> getARVDailyReport(String clinicId, Date startDate, Date endDate, String clincalServiceId) {
        def queryString = " select  " +
                " psi.value, " +
                " pat.first_names , " +
                " pat.middle_names, " +
                " pat.last_names , " +
                " pr.patient_type, " +
                " pat.date_of_birth, " +
                " CASE  " +
                "  WHEN dt.code = 'DT' THEN " +
                "    CASE WHEN  pack2.pickup_date >= :startDate THEN 'DT' " +
                "       ELSE 'DT-TRANSPORTE'" +
                "         END " +
                "     WHEN dt.code = 'DS' THEN " +
                "       CASE WHEN  pack2.pickup_date >= :startDate THEN 'DS' " +
                "         ELSE 'DS-TRANSPORTE'" +
                "           END " +
                "        WHEN dt.code = 'DM' THEN " +
                "        CASE WHEN  pack2.pickup_date >= :startDate THEN 'DS' " +
                "          ELSE 'DM-TRANSPORTE' " +
                "          END " +
                "     END AS tipodispensa," +
                " tl.description as description2, " +
                " pack2.pickup_date, " +
                " pack2.next_pick_up_date, " +
                " tr.description as description3, " +
                " pack2.id as id1, " +
                " ssr.reason, " +
                " cs.code, " +
                " ssr.is_start_reason," +
                " pvd.id as id2 " +
                //  "EXTRACT(year FROM age(:endDate, p.date_of_birth)) as idade" +
                " from patient_visit_details  pvd " +
                " inner join pack pack2 on (pvd.pack_id = pack2.id) " +
                " inner join prescription pr on (pvd.prescription_id = pr.id) " +
                " inner join prescription_detail prd on (pr.id = prd.prescription_id) " +
                " inner join dispense_type dt on (prd.dispense_type_id = dt.id) " +
                " inner join episode ep on (ep.id = pvd.episode_id) " +
                " inner join start_stop_reason ssr on (ep.start_stop_reason_id = ssr.id) " +
                " inner join patient_Service_identifier psi on (ep.patient_Service_identifier_id = psi.id) " +
                " inner join patient pat on (psi.patient_id = pat.id) " +
                " inner join therapeutic_regimen tr on (prd.therapeutic_regimen_id = tr.id) " +
                " inner join therapeutic_line tl on (prd.therapeutic_line_id = tl.id) " +
                " inner join clinical_service cs on (psi.service_id = cs.id) " +
                " where ((Date(pack2.pickup_date) BETWEEN :startDate AND :endDate) OR " +
                " pg_catalog.date(pack2.pickup_date) < :startDate and pg_catalog.date(pack2.next_pick_up_date) > :endDate AND " +
                " DATE(pack2.pickup_date + (INTERVAL '1 month'* cast (date_part('day',  cast (:endDate as timestamp) - cast (pack2.pickup_date as timestamp))/30 as integer))) >= :startDate " +
                " and DATE(pack2.pickup_date + (INTERVAL '1 month'*cast (date_part('day', cast (:endDate as timestamp) - cast (pack2.pickup_date as timestamp))/30 as integer))) <= :endDate) " +
                "  and pack2.clinic_id =:clinicId " +
                "  and psi.service_id =:clincalServiceId  "

        Session session = sessionFactory.getCurrentSession()
        def query = session.createSQLQuery(queryString)
        query.setParameter("clinicId", clinicId)
        query.setParameter("startDate", startDate)
        query.setParameter("endDate", endDate)
        query.setParameter("clincalServiceId", clincalServiceId)

        List<Object[]> list = query.list()
        return list

    }


    List<DrugQuantityTemp> getProducts(String patientVisitDetailId, String clinicId) {
        List<DrugQuantityTemp> listDrugTemp = new ArrayList<>()

        def list = Stock.executeQuery("select  " +
                "d.name , sum(pd.quantitySupplied)  from PatientVisitDetails pvd " +
                "inner join pvd.pack p  " +
                "inner join p.packagedDrugs pd  " +
                "inner join pd.drug d  " +
                " where pvd.id =:patientVisitDetailId And " +
                " p.clinic.id =:clinicId " +
                " group by d.name "
                , [patientVisitDetailId: patientVisitDetailId, clinicId: clinicId])
        list.each { item ->
            def drugName = item[0]
            def quantity = item[1]
            listDrugTemp.add(new DrugQuantityTemp(String.valueOf(drugName), Long.valueOf(quantity)))
        }
        return listDrugTemp
    }

    @Override
    PatientVisitDetails getLastVisitByEpisodeId(String episodeId) {
        def patientVisitDetails = PatientVisitDetails.findAllByEpisode(Episode.findById(episodeId), [sort: ['patientVisit.visitDate': 'desc']])
        return patientVisitDetails.get(0)
    }

    @Override
    PatientVisitDetails getLastByEpisodeId(String episodeId) {
        def list = PatientVisitDetails.executeQuery("select pvd " +
                "from PatientVisitDetails pvd " +
                "inner join pvd.patientVisit pv " +
                "inner join pvd.episode e " +
                "where e = :episode " +
                "order by pv.visitDate desc",
                [episode: Episode.findById(episodeId)])

        if (list == null || list.size() <= 0) return null
        return list.get(0)
    }
}
