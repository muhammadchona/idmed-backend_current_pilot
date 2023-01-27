package mz.org.fgh.sifmoz.backend.packaging

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.dispenseType.DispenseType
import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams
import mz.org.fgh.sifmoz.backend.patient.Patient
import mz.org.fgh.sifmoz.backend.prescription.Prescription
import mz.org.fgh.sifmoz.backend.reports.common.ReportProcessMonitor
import mz.org.fgh.sifmoz.backend.service.ClinicalService
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired

@Transactional
@Service(Pack)
abstract class PackService implements IPackService{

    @Autowired
    SessionFactory sessionFactory

    @Override
    List<Pack> getAllLastPackOfClinic(String clinicId, int offset, int max) {
        Session session = sessionFactory.getCurrentSession()

        String queryString ="select *  " +
                "from patient_last_pack_vw  " +
                "where clinic_id = :clinic offset :offset limit :max "


        def query = session.createSQLQuery(queryString).addEntity(Pack.class)
        query.setParameter("clinic", clinicId)
        query.setParameter("offset", offset)
        query.setParameter("max", max)
        List<Pack> result = query.list()
        return result
    }

    @Override
    int countPacksByDispenseTypeAndServiceOnPeriod(DispenseType dispenseType, ClinicalService service, Clinic clinic, Date startDate, Date endDate) {
        int value = 0
        def count =Pack.executeQuery("select count(*) " +
                "from Pack as pk " +
                "inner join pk.clinic as cl " +
                "inner join pk.patientVisitDetails as pvd " +
                "inner join pvd.prescription as pr " +
                "inner join pvd.episode as ep " +
                "inner join pr.prescriptionDetails as prd " +
                "inner join ep.patientServiceIdentifier as pid " +
                "inner join pid.service as svc " +
                "where pk.pickupDate >= :startDate " +
                "       and pk.pickupDate <= :endDate " +
                "       and cl.id = :clinic " +
                "       and prd.dispenseType.code = :serviceCode " +
                "       and ep.patientServiceIdentifier.service.code = :serviceCode ",
                [startDate:startDate, endDate:endDate, serviceCode: service.getCode(), clinic: clinic.getId()])
        value = Integer.valueOf(count.get(0).toString())
        return value
    }

    @Override
    List<Pack> getPacksByServiceOnPeriod(ClinicalService service, Clinic clinic, Date startDate, Date endDate) {
        List<Pack> packList = Pack.executeQuery("select pk " +
                "from Pack as pk " +
                "inner join pk.patientVisitDetails as pvd " +
                "inner join pvd.episode as ep " +
                "inner join ep.startStopReason as rsn " +
                "inner join pvd.patientVisit as pv " +
                "inner join pvd.prescription as pr " +
                "inner join pr.prescriptionDetails as prd " +
                "inner join pv.patient as pt " +
                "inner join pt.identifiers as pid " +
                "inner join pid.service as svc " +
                "where pk.pickupDate >= :startDate " +
                "       and pk.pickupDate <= :endDate " +
                "       and pk.clinic= :clinic " +
                "       and ep.patientServiceIdentifier.service.code = :serviceCode ",
                [startDate:startDate, endDate:endDate, serviceCode: service.getCode(), clinic: clinic])
        return packList
    }

    @Override
    int countPacksByServiceOnPeriod(ClinicalService service, Clinic clinic, Date startDate, Date endDate) {
        int value = 0
        def count = Pack.executeQuery("select count(*) " +
                "from Pack as pk " +
                "inner join pk.patientVisitDetails as pvd " +
                "inner join pvd.episode as ep " +
                "inner join ep.startStopReason as rsn " +
                "inner join pvd.patientVisit as pv " +
                "inner join pvd.prescription as pr " +
                "inner join pr.prescriptionDetails as prd " +
                "inner join pv.patient as pt " +
                "inner join pt.identifiers as pid " +
                "inner join pid.service as svc " +
                "where pk.pickupDate >= :startDate " +
                "       and pk.pickupDate <= :endDate " +
                "       and pk.clinic = :clinic " +
                "       and ep.patientServiceIdentifier.service.code = :serviceCode ",
                [startDate:startDate, endDate:endDate, serviceCode: service.getCode(), clinic: clinic])
        value = Integer.valueOf(count.get(0).toString())
        return value
    }

    //Query Historico de Levantamento de Pacientes Referidos
    @Override
    List<Pack> getPacksOfReferredPatientsByClinicalServiceAndClinicOnPeriod(ClinicalService clinicalService,Clinic clinic, Date startDate, Date endDate) {

        return Pack.executeQuery("select pk from Pack pk " +
                "inner join pk.patientVisitDetails as pvd " +
                "inner join pvd.patientVisit as pv " +
                "inner join pvd.episode as ep " +
                "inner join ep.startStopReason as stp " +
                "inner join ep.patientServiceIdentifier as psi " +
                "inner join psi.patient as p " +
                "inner join psi.service as s " +
                "inner join ep.clinic c " +
                "where s.code = :serviceCode and c.id = :clinicId and pk.pickupDate >= :startDate and pk.pickupDate <= :endDate " +
                "and psi.id in (select psi2.id from Episode ep2 " +
                "inner join ep2.patientServiceIdentifier as psi2 " +
                "inner join ep2.startStopReason as stp2 " +
                "inner join psi2.service as s2 " +
                "where stp.code = 'REFERIDO_PARA' and s2.code = :serviceCode and ep2.episodeDate >= :startDate and ep2.episodeDate <= :endDate)",
                [serviceCode:clinicalService.code,clinicId:clinic.id,startDate:startDate,endDate:endDate])
    }

    @Override
    List getAbsentReferredPatientsByClinicalServiceAndClinicOnPeriod(ClinicalService clinicalService, Clinic clinic, Date startDate, Date endDate){

        def list = Pack.executeQuery("select ep as episode," +
                "pk.nextPickUpDate as dateMissedPickUp, " +
                "p.cellphone as contact, " +
                "(select pk4.pickupDate from Pack pk4 " +
                "inner join pk4.patientVisitDetails as pvd2 " +
                "inner join pvd2.patientVisit as pv2 " +
                "inner join pvd2.episode as ep3 " +
                "inner join ep3.patientServiceIdentifier as psi3 " +
                "inner join psi3.service as s3 " +
                "where psi.patient = psi3.patient and s3.code = :serviceCode and pk4.pickupDate > pk.nextPickUpDate and pk4.pickupDate <= :endDate) as returnedPickUp " +
                "from Pack pk " +
                "inner join pk.patientVisitDetails as pvd " +
                "inner join pvd.patientVisit as pv " +
                "inner join pvd.episode as ep " +
                "inner join ep.startStopReason as stp " +
                "inner join ep.patientServiceIdentifier as psi " +
                "inner join psi.patient as p " +
                "inner join psi.service as s " +
                "inner join ep.clinic c " +
                "where s.code = :serviceCode and c.id = :clinicId and pk.nextPickUpDate >= :startDate and pk.nextPickUpDate <= :endDate and DATE(pk.nextPickUpDate) + :days <= :endDate " +
                "and psi.id in (select psi2.id from Episode ep2 " +
                "inner join ep2.patientServiceIdentifier as psi2 " +
                "inner join ep2.startStopReason as stp2 " +
                "inner join psi2.service as s2 " +
                "where stp.code = 'REFERIDO_PARA' and s2.code = :serviceCode and ep2.episodeDate >= :startDate and ep2.episodeDate <= :endDate) " +
                "and pk.nextPickUpDate in (select max(pk2.nextPickUpDate) from Pack pk2 " +
                "inner join pk2.patientVisitDetails as pvd2 " +
                "inner join pvd2.patientVisit as pv2 " +
                "inner join pvd2.episode as ep3 " +
                "inner join ep3.patientServiceIdentifier as psi3 " +
                "inner join psi3.service as s3 " +
                "where psi.patient = psi3.patient and s3.code = :serviceCode and pk2.nextPickUpDate  >= :startDate and pk2.nextPickUpDate <= :endDate)",
                [serviceCode:clinicalService.code,clinicId:clinic.id,startDate:startDate,endDate:endDate,days: 3])

       return list
    }

    @Override
    List getAbsentPatientsByClinicalServiceAndClinicOnPeriod(ClinicalService clinicalService, Clinic clinic, Date startDate, Date endDate){

        /*
        def list = Pack.executeQuery("select ep as episode," +
                "pk.nextPickUpDate as dateMissedPickUp, " +
                "p.cellphone as contact " +
                "from Pack pk " +
                "inner join pk.patientVisitDetails as pvd " +
                "inner join pvd.patientVisit as pv " +
                "inner join pvd.episode as ep " +
                "inner join ep.startStopReason as stp " +
                "inner join ep.patientServiceIdentifier as psi " +
                "inner join psi.patient as p " +
                "inner join psi.service as s " +
                "inner join ep.clinic c " +
                "inner join psi.identifierType idt " +
                "where s.code = :serviceCode and c.id = :clinicId and pk.nextPickUpDate >= :startDate and pk.nextPickUpDate <= :endDate and DATE(pk.nextPickUpDate) + :days <= :endDate " +
                "and DATE_DIFF('day', pk.nextPickUpDate, :endDate) > 60 " +
                "and stp.code in ('NOVO_PACIENTE','INICIO_CCR','TRANSFERIDO_DE','REINICIO_TRATAMETO','MANUNTENCAO','VOLTOU_REFERENCIA') " +
                "and idt.code = 'NID' " +
                "and pk.nextPickUpDate in (select max(pk2.nextPickUpDate) from Pack pk2 " +
                "inner join pk2.patientVisitDetails as pvd2 " +
                "inner join pvd2.patientVisit as pv2 " +
                "inner join pvd2.episode as ep3 " +
                "inner join ep3.patientServiceIdentifier as psi3 " +
                "inner join psi3.service as s3 " +
                "where psi.patient = psi3.patient and s3.code = :serviceCode and pk2.nextPickUpDate  >= :startDate and pk2.nextPickUpDate <= :endDate)",
                [serviceCode:clinicalService.code,clinicId:clinic.id,startDate:startDate,endDate:endDate,days: 3])
   */
        def queryString = "SELECT distinct psi.value," +
                "pat.first_names," +
                "pat.middle_Names, " +
                "pat.last_Names, " +
                "max(pack.pickup_date) as dateMissedPickUp," +
                "pat.cellphone as contact," +
                "EXTRACT(DAY FROM (:endDate - (pack.next_pick_up_date + INTERVAL '0 days'))) as dayssinceexpected " +
                "FROM pack pack " +
                "INNER JOIN patient_visit_details pvd on pvd.pack_id = pack.id " +
                "INNER JOIN patient_visit pv on pv.id = pvd.patient_visit_id " +
                "INNER JOIN patient pat on pat.id = pv.patient_id " +
                "INNER JOIN patient_service_identifier psi ON psi.patient_id = pat.id " +
                "INNER JOIN identifier_type idt ON psi.identifier_type_id = idt.id " +
                "INNER JOIN clinical_service cs ON psi.service_id = cs.id " +
                "INNER JOIN prescription pre on pre.id = pvd.prescription_id " +
                "INNER JOIN prescription_detail pred on pred.prescription_id = pre.id " +
                "INNER JOIN episode ep on ep.id = pvd.episode_id " +
                "INNER JOIN start_stop_reason ssr on ssr.id = ep.start_stop_reason_id " +
                "where " +
                "ssr.code in ('NOVO_PACIENTE','INICIO_CCR','TRANSFERIDO_DE','REINICIO_TRATAMETO','MANUNTENCAO','VOLTOU_REFERENCIA') " +
                "AND (pack.next_pick_up_date + INTERVAL '3 days') < :endDate " +
                "AND EXTRACT(DAY FROM (:endDate - (pack.next_pick_up_date + INTERVAL '3 days'))) < 60 " +
                "AND cs.code = 'TARV' AND idt.code = 'NID' " +
                "group by 1,2,3,4,6,7"

        Session session = sessionFactory.getCurrentSession()
        def query = session.createSQLQuery(queryString)
        query.setParameter("endDate", endDate)
      //  query.setParameter("days", 3)
        List<Object[]> list = query.list()
        return list
    }

    List<Pack> getActivePatientsReportDataByReportParams (ReportSearchParams reportSearchParams) {
        def list =  Pack.executeQuery("select Distinct pat.firstNames," +
                "pat.middleNames," +
                "pat.lastNames," +
                "pat.gender," +
                "pat.dateOfBirth," +
                "pat.cellphone," +
                "psi.value," +
                "(select max(pk4.pickupDate) from Pack pk4 " +
                "inner join pk4.patientVisitDetails as pvd2 " +
                "inner join pvd2.patientVisit as pv2 " +
                "inner join pvd2.episode as ep3 "+
                "inner join ep3.patientServiceIdentifier as psi3 "+
                "inner join psi3.service as s3 "  +
                "where psi.patient = psi3.patient and s3.code = 'TARV') as pickupDate, " +
                "(select max(pk4.nextPickUpDate) from Pack pk4 " +
                "inner join pk4.patientVisitDetails as pvd2 " +
                "inner join pvd2.patientVisit as pv2 " +
                "inner join pvd2.episode as ep3 "+
                "inner join ep3.patientServiceIdentifier as psi3 "+
                "inner join psi3.service as s3 "  +
                "where psi.patient = psi3.patient and s3.code = 'TARV') as nextPickUpDate, " +
                "regimenThe.description," +
                "lineThe.description, " +
                "pre.patientType " +
                "from Pack p inner join p.patientVisitDetails pvd " +
                "inner join pvd.patientVisit pv " +
                "inner join pv.patient pat " +
                "inner join pvd.episode ep " +
                "inner join ep.patientServiceIdentifier psi " +
                "inner join psi.identifierType idt " +
                "inner join psi.service cs " +
                "inner join ep.startStopReason ssr " +
                "inner join pvd.prescription pre " +
                "inner join pre.prescriptionDetails pdl " +
                "inner join pdl.therapeuticLine lineThe " +
                "inner join pdl.therapeuticRegimen regimenThe " +
                "inner join ep.clinic cli " +
                "where ssr.code in ('NOVO_PACIENTE','INICIO_CCR','TRANSFERIDO_DE','REINICIO_TRATAMETO','MANUNTENCAO') " +
                "and cs.code = 'TARV' and idt.code = 'NID' and p.nextPickUpDate >= :endDate and DATE(p.nextPickUpDate) + :days >= :endDate and cli.id = :clinicId " +
                "order by pat.firstNames asc" ,
                [clinicId:reportSearchParams.clinicId,endDate:reportSearchParams.endDate,days: 3])
        return list
    }
}
