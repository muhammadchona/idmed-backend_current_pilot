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
                "from  PatientVisitDetails as pvd  " +
                "inner join  pvd.pack as pk " +
                "inner join pk.clinic as cl " +
                "inner join pvd.prescription as pr " +
                "inner join pvd.episode as ep " +
                "inner join pr.prescriptionDetails as prd " +
                "inner join ep.patientServiceIdentifier as pid " +
                "inner join pid.service as svc " +
                "where pk.pickupDate >= :startDate " +
                "       and pk.pickupDate <= :endDate " +
                "       and cl.id = :clinic " +
                "       and prd.dispenseType.code = :dispenseTypeCode " +
                "       and ep.patientServiceIdentifier.service.code = :serviceCode ",
                [startDate:startDate, endDate:endDate, serviceCode: service.getCode(), clinic: clinic.getId(), dispenseTypeCode: dispenseType.getCode()])
        value = Integer.valueOf(count.get(0).toString())
        return value
    }

    @Override
    List<Pack> getPacksByServiceOnPeriod(ClinicalService service, Clinic clinic, Date startDate, Date endDate) {
        List<Pack> packList = Pack.executeQuery("select pk " +
                "from PatientVisitDetails as pvd " +
                "inner join  pvd.pack as pk  " +
                "inner join pvd.episode as ep " +
                "inner join ep.startStopReason as rsn " +
                //  "inner join pvd.patientVisit as pv " +
                //  "inner join pvd.prescription as pr " +
                //  "inner join pr.prescriptionDetails as prd " +
                //    "inner join pv.patient as pt " +
                //   "inner join pt.identifiers as pid " +
                //    "inner join pid.service as svc " +
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
                "from PatientVisitDetails as pvd" +
                "inner join  pvd.pack as pk  " +
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

        return Pack.executeQuery("select pk from PatientVisitDetails as pvd " +
                "inner join Pack pk" +
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
                "(select pk4.pickupDate from PatientVisitDetails pvd2 " +
                "inner join pvd2.pack pk4 " +
                "inner join pvd2.patientVisit as pv2 " +
                "inner join pvd2.episode as ep3 " +
                "inner join ep3.patientServiceIdentifier as psi3 " +
                "inner join psi3.service as s3 " +
                "where psi.patient = psi3.patient and s3.code = :serviceCode and pk4.pickupDate > pk.nextPickUpDate and pk4.pickupDate <= :endDate) as returnedPickUp " +
                "from PatientVisitDetails as pvd  " +
                "inner join  pvd.pack pk" +
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
                "and pk.nextPickUpDate in (select max(pk2.nextPickUpDate) from PatientVisitDetails pvd2 " +
                "inner join pvd2.pack pk2 " +
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
                "max(pack.next_pick_up_date) as dateMissedPickUp," +
                "pat.cellphone as contact," +
                "EXTRACT(DAY FROM (:endDate - (pack.next_pick_up_date + INTERVAL '0 days'))) as dayssinceexpected " +
                "FROM patient_visit_details pvd  " +
                "INNER JOIN  pack pack on pvd.pack_id = pack.id " +
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
                "ssr.code in ('NOVO_PACIENTE','INICIO_CCR','TRANSFERIDO_DE','REINICIO_TRATAMETO','MANUNTENCAO','VOLTOU_REFERENCIA','OUTRO') " +
                "AND (pack.next_pick_up_date + INTERVAL '3 days') < :endDate " +
                "AND EXTRACT(DAY FROM (:endDate - (pack.next_pick_up_date + INTERVAL '3 days'))) < 60 " +
                "AND cs.code = :csCode AND idt.code = :idCode " +
                "and pack.next_pick_up_date = " +
                "(select max(pck.next_pick_up_date) from pack pck " +
                "INNER JOIN patient_visit_details pvd1 on pvd1.pack_id = pck.id " +
                "                INNER JOIN patient_visit pv1 on pv1.id = pvd1.patient_visit_id " +
                "                INNER JOIN patient pat1 on pat1.id = pv1.patient_id  where pat1.id = pat.id and (pack.next_pick_up_date +  INTERVAL '3 days') < :endDate" +
                "                AND EXTRACT(DAY FROM (:endDate - (pack.next_pick_up_date + INTERVAL '3 days'))) < 60)  " +
                "group by 1,2,3,4,6,7 " +
                "order by dateMissedPickUp asc"

        Session session = sessionFactory.getCurrentSession()
        def query = session.createSQLQuery(queryString)
        query.setParameter("endDate", endDate)
        //    query.setParameter("days", 3)
        query.setParameter("csCode", clinicalService.code)
        query.setParameter("idCode", clinicalService.identifierType.code)
        List<Object[]> list = query.list()
        return list
    }

    @Override
    List<Pack> getActivePatientsReportDataByReportParams (ReportSearchParams reportSearchParams) {
        def list =  Pack.executeQuery("select Distinct pat.firstNames," +
                "pat.middleNames," +
                "pat.lastNames," +
                "pat.gender," +
                "pat.dateOfBirth," +
                "pat.cellphone," +
                "psi.value," +
                "(select max(pk4.pickupDate) from " +
                "PatientVisitDetails as pvd2  inner join  pvd2.pack as pk4 " +
                "inner join pvd2.patientVisit as pv2 " +
                "inner join pvd2.episode as ep3 "+
                "inner join ep3.patientServiceIdentifier as psi3 "+
                "inner join psi3.service as s3 "  +
                "where psi.patient = psi3.patient and s3.code = 'TARV') as pickupDate, " +
                "(select max(pk4.nextPickUpDate) from " +
                "PatientVisitDetails pvd2  inner join  pvd2.pack as pk4 " +
                "inner join pvd2.patientVisit as pv2 " +
                "inner join pvd2.episode as ep3 "+
                "inner join ep3.patientServiceIdentifier as psi3 "+
                "inner join psi3.service as s3 "  +
                "where psi.patient = psi3.patient and s3.code = 'TARV') as nextPickUpDate, " +
                "regimenThe.description," +
                "lineThe.description, " +
                "pre.patientType " +
                "from PatientVisitDetails as pvd  inner join  pvd.pack  as p " +
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

    //Historico de Levantamentos
    @Override
    List<Pack> getPacksByClinicalServiceAndClinicOnPeriod(ClinicalService clinicalService,Clinic clinic,Date startDate, Date endDate) {
/*
        return Pack.executeQuery("select " +
                "psi.value," +
                "p.firstNames," +
                "p.middleNames," +
                "p.lastNames," +
                "p.dateOfBirth," +
                "p.cellphone," +
                "pre.patientStatus, " +
                "regimenThe.description," +
                "dt.description, " +
                "dm.description, " +
                "pk.pickupDate, " +
                "pk.nextPickUpDate " +
                "from Pack pk " +
                "inner join pk.patientVisitDetails as pvd " +
                "inner join pvd.patientVisit as pv " +
                "inner join pvd.episode as ep " +
                "inner join ep.startStopReason as stp " +
                "inner join ep.patientServiceIdentifier as psi " +
                "inner join psi.patient as p " +
                "inner join psi.service as s " +
                "inner join ep.clinic c " +
                "inner join pvd.prescription pre " +
                "inner join pre.prescriptionDetails pdl " +
                "inner join pdl.therapeuticRegimen regimenThe " +
                "inner join pdl.dispenseType dt " +
                "inner join pk.dispenseMode dm " +
                "where s.code = :serviceCode and c.id = :clinicId and (pk.pickupDate >= :startDate and pk.pickupDate <= :endDate) " +
                "OR (pk.pickupDate >= :startDate and DATE(pk.pickupDate) - 30 >= :startDate and DATE(pk.pickupDate) + 30 <= :endDate) " +
                "order by pk.pickupDate asc",
                [serviceCode:clinicalService.code,clinicId:clinic.id,startDate:startDate,endDate:endDate])
 */
        def queryString = "select psi.value," +
                "p.first_names," +
                "p.middle_names," +
                "p.last_names," +
                "EXTRACT(year FROM age(:endDate, p.date_of_birth)) as idade," +
                "p.cellphone," +
                "prc.patient_type," +
                "tr.description as regimeDescription," +
                " CASE  " +
                "  WHEN dt.code = 'DT' THEN " +
                "    CASE WHEN  pack2.pickup_date >= :startDate THEN 'DT' " +
                "       ELSE 'DT - TRANSPORTE'" +
                "         END " +
                "     WHEN dt.code = 'DS' THEN " +
                "       CASE WHEN  pack2.pickup_date >= :startDate THEN 'DS' " +
                "         ELSE 'DS - TRANSPORTE'" +
                "           END " +
                "        WHEN dt.code = 'DM' THEN " +
                "        CASE WHEN  pack2.pickup_date >= :startDate THEN 'DS' " +
                "          ELSE 'DM - TRANSPORTE' " +
                "          END " +
                "     END AS tipodispensa," +
                "dm.description as dispenseMode," +
                "pack2.pickup_date," +
                "pack2.next_pick_up_date " +
                "from patient p " +
                "inner join patient_service_identifier psi on psi.patient_id = p.id " +
                "INNER JOIN clinical_service cs ON psi.service_id = cs.id " +
                "inner join episode ep on ep.patient_service_identifier_id = psi.id " +
                "inner join patient_visit_details pvd ON pvd.episode_id = ep.id " +
                "INNER JOIN patient_visit pv on pv.id = pvd.patient_visit_id " +
                "inner join patient ON patient.id = psi.patient_id " +
                "inner join pack pack2 ON pack2.id = pvd.pack_id " +
                "inner join prescription prc ON prc.id = pvd.prescription_id " +
                "INNER JOIN start_stop_reason ssr on ssr.id = ep.start_stop_reason_id " +
                "INNER JOIN prescription_detail pred on pred.prescription_id = prc.id " +
                "inner join therapeutic_regimen tr on tr.id = pred.therapeutic_regimen_id " +
                "inner join dispense_type dt ON dt.id = pred.dispense_type_id " +
                "inner join dispense_mode dm ON dm.id = pack2.dispense_mode_id " +
                "inner join clinic c on c.id = ep.clinic_id " +
                "where " +
                "((Date(pack2.pickup_date) BETWEEN :startDate AND :endDate) OR " +
                "pg_catalog.date(pack2.pickup_date) < :startDate and pg_catalog.date(pack2.next_pick_up_date) > :endDate AND " +
                "DATE(pack2.pickup_date + (INTERVAL '1 month'* cast (date_part('day',  cast (:endDate as timestamp) - cast (pack2.pickup_date as timestamp))/30 as integer))) >= :startDate " +
                "and DATE(pack2.pickup_date + (INTERVAL '1 month'*cast (date_part('day', cast (:endDate as timestamp) - cast (pack2.pickup_date as timestamp))/30 as integer))) <= :endDate) " +
                "and cs.code = :serviceCode " +
                "and c.id = :clinicId " +
                "and ssr.code in ('NOVO_PACIENTE','INICIO_CCR','TRANSFERIDO_DE','REINICIO_TRATAMETO','MANUNTENCAO','OUTRO') " +
                "group by 1,2,3,4,5,6,7,8,9,10,11,12 " +
                "ORDER BY PACK2.pickup_date asc"

        Session session = sessionFactory.getCurrentSession()
        def query = session.createSQLQuery(queryString)
        query.setParameter("endDate", endDate)
        query.setParameter("startDate", startDate)
        query.setParameter("serviceCode", clinicalService.code)
        query.setParameter("clinicId", clinic.id)
        List<Object[]> list = query.list()
        return list
    }
}
