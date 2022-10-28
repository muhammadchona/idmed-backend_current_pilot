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
abstract class PatientVisitDetailsService implements IPatientVisitDetailsService{

    @Autowired
    SessionFactory sessionFactory

    @Override
    List<PatientVisitDetails> getAllByClinicId(String clinicId, int offset, int max) {
        return PatientVisitDetails.findAllByClinic(Clinic.findById(clinicId), [offset: offset, max: max])
    }

    @Override
    List<PatientVisitDetails> getAllLastVisitOfClinic(String clinicId, int offset, int max) {
        Session session = sessionFactory.getCurrentSession()

        String queryString ="select *  " +
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
        List<PatientVisitDetails> list = Stock.executeQuery("select  pvd from PatientVisitDetails  pvd " +
                " inner join pvd.pack  p" +
                " inner join pvd.prescription pr" +
                " inner join pr.prescriptionDetails prd" +
                " inner join prd.dispenseType dt" +
                " inner join pvd.episode ep" +
                " inner join ep.startStopReason sst" +
                " inner join ep.patientServiceIdentifier psi" +
                " inner join psi.patient pat" +
                " inner join prd.therapeuticRegimen tr" +
                " inner join prd.therapeuticLine tl" +
                " where p.pickupDate BETWEEN  :startDate AND :endDate  AND " +
                " p.clinic.id =:clinicId ",
               // " AND psi.service.id =:clincalServiceId ",
                [startDate: startDate, endDate: endDate, clinicId: clinicId])
        //clincalServiceId:clincalServiceId

        return list
    }


    List<DrugQuantityTemp> getProducts(String patientVisitDetailId, String clinicId, Date startDate, Date endDate) {
        List<DrugQuantityTemp> listDrugTemp = new ArrayList<>()

        def list = Stock.executeQuery("select  " +
                "d.name , sum(pd.quantitySupplied)  from PatientVisitDetails pvd " +
                "inner join pvd.pack p  " +
                "inner join p.packagedDrugs pd  " +
                "inner join pd.drug d  " +
                " where pvd.id =:patientVisitDetailId And " +
                " p.pickupDate BETWEEN  :startDate AND :endDate  And " +
                " p.clinic.id =:clinicId " +
                " group by d.name "
                , [patientVisitDetailId: patientVisitDetailId, startDate: startDate, endDate: endDate, clinicId: clinicId])
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
                [episode:Episode.findById(episodeId)])

        if (list == null || list.size() <=0) return null
        return list.get(0)
    }
}
