package mz.org.fgh.sifmoz.backend.reports.common

import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.reports.patients.ActivePatientReport
import mz.org.fgh.sifmoz.backend.utilities.Utilities
import mz.org.fgh.sifmoz.dashboard.ActivePatientPercentage
import mz.org.fgh.sifmoz.dashboard.DashboardServiceButton
import mz.org.fgh.sifmoz.dashboard.DispensesByAge
import mz.org.fgh.sifmoz.dashboard.DispensesByGender
import mz.org.fgh.sifmoz.dashboard.PatientsFirstDispenseByAge
import mz.org.fgh.sifmoz.dashboard.PatientsFirstDispenseByGender
import mz.org.fgh.sifmoz.dashboard.RegisteredPatientsByDispenseType
import mz.org.fgh.sifmoz.dashboard.StockAlert
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired

@Transactional
class DashBoardService {

    @Autowired
    SessionFactory sessionFactory

    def List<RegisteredPatientsByDispenseType> getRegisteredPatientByDispenseType (Date startDate, Date endDate, String clinicId, String serviceCode) {

        Clinic clinic = Clinic.findById(clinicId)

        String queryString ="select count(*) quantity, pat_disp.dispense_type dispense_type, pat_disp.month " +
                            "from ( " +
                            "       select " +
                            "               distinct(p.id), " +
                            "               psi.start_date, " +
                            "               dt.code as dispense_type, " +
                            "               cs.code as service, " +
                            "               pv2.visit_date, " +
                            "               (CASE " +
                            "                   WHEN (EXTRACT(DAY FROM psi.start_date) > 20) THEN EXTRACT(MONTH FROM (psi.start_date + interval '1 month')) " +
                            "                   WHEN (EXTRACT(DAY FROM psi.start_date) <= 20) THEN EXTRACT(MONTH FROM psi.start_date) " +
                            "               END) as month " +
                            "               from patient_visit pv2 inner join patient p on pv2.patient_id = p.id " +
                            "                                       inner join patient_service_identifier psi on p.id = psi.patient_id " +
                            "                                       inner join episode e on e.patient_service_identifier_id  = psi .id " +
                            "                                       inner join patient_visit_details pvd on pvd.episode_id = e.id and pvd.patient_visit_id = pv2.id " +
                            "                                       inner join prescription pre on pre.id = pvd.prescription_id " +
                            "                                       inner join prescription_detail pd on pd.prescription_id = pre.id " +
                            "                                       inner join dispense_type dt on dt.id = pd.dispense_type_id " +
                            "                                       inner join clinical_service cs on psi.service_id = cs.id " +
                            "       where 1=1 and " +
                            "           not exists (select * " +
                            "                       from patient_visit pv3 " +
                            "                       where pv3.patient_id = pv2.patient_id " +
                            "                               and pv3.visit_date <= :endDate " +
                            "                               and pv3.visit_date > pv2.visit_date) " +
                            "           and (psi.start_date >= :startDate and psi.start_date <= :endDate) " +
                            "           and cs.code = :service " +
                            "           ) pat_disp " +
                            "group by pat_disp.dispense_type, pat_disp.month " +
                            "order by pat_disp.month asc"

        Session session = sessionFactory.getCurrentSession()
        def query = session.createSQLQuery(queryString)
        query.setParameter("startDate", startDate)
        query.setParameter("endDate", endDate)
        query.setParameter("service", serviceCode)
        List<Object[]> result = query.list()

        List<RegisteredPatientsByDispenseType> registeredPatientsByDispenseTypeList = new ArrayList<>()

        if (Utilities.listHasElements(result as ArrayList<?>)) {

            for (item in result) {
                registeredPatientsByDispenseTypeList.add(new RegisteredPatientsByDispenseType(String.valueOf(item[1]), Integer.valueOf(String.valueOf(item[0])), (int) Double.parseDouble(String.valueOf(item[2]))))
            }
        }
        return registeredPatientsByDispenseTypeList
    }


    def List<PatientsFirstDispenseByGender> getPatientsFirstDispenseByGender (Date startDate, Date endDate, String clinicId, String serviceCode) {

        Clinic clinic = Clinic.findById(clinicId)

        String queryString ="select count(*) quantity, pat_disp.month, pat_disp.gender " +
                "from ( " +
                "  select  " +
                "      distinct(p.id), " +
                "      psi.start_date, " +
                "      dt.code as dispense_type, " +
                "      cs.code as service, " +
                "      pv2.visit_date, " +
                "      p.gender, " +
                "      (CASE  " +
                "         WHEN (EXTRACT(DAY FROM pv2.visit_date) > 20) THEN EXTRACT(MONTH FROM (pv2.visit_date + interval '1 month')) " +
                "         WHEN (EXTRACT(DAY FROM pv2.visit_date) <= 20) THEN EXTRACT(MONTH FROM pv2.visit_date) " +
                "    END) as month " +
                "   from patient_visit pv2 inner join patient p on pv2.patient_id = p.id  " +
                "         inner join patient_service_identifier psi on p.id = psi.patient_id  " +
                "         inner join episode e on e.patient_service_identifier_id  = psi .id  " +
                "         inner join patient_visit_details pvd on pvd.episode_id = e.id and pvd.patient_visit_id = pv2.id  " +
                "         inner join prescription pre on pre.id = pvd.prescription_id  " +
                "         inner join prescription_detail pd on pd.prescription_id = pre.id  " +
                "         inner join dispense_type dt on dt.id = pd.dispense_type_id  " +
                "           inner join clinical_service cs on psi.service_id = cs.id  " +
                "  where 1=1 and  " +
                "   not exists (select * " +
                "       from patient_visit pv3 " +
                "       where pv3.patient_id = pv2.patient_id " +
                "       and pv3.visit_date < pv2.visit_date) " +
                "   and (pv2.visit_date >= :startDate and pv2.visit_date <= :endDate) " +
                "   and cs.code = :service " +
                //"   and pre.patient_type = 'INICIO' " +
                " ) pat_disp " +
                " group by pat_disp.dispense_type, pat_disp.month, pat_disp.gender " +
                " order by pat_disp.month asc"

        Session session = sessionFactory.getCurrentSession()
        def query = session.createSQLQuery(queryString)
        query.setParameter("startDate", startDate)
        query.setParameter("endDate", endDate)
        query.setParameter("service", serviceCode)
        List<Object[]> result = query.list()

        List<PatientsFirstDispenseByGender> registeredPatientsByDispenseTypeList = new ArrayList<>()

        if (Utilities.listHasElements(result as ArrayList<?>)) {

            for (item in result) {
                registeredPatientsByDispenseTypeList.add(new PatientsFirstDispenseByGender(Integer.valueOf(String.valueOf(item[0])), (int) Double.parseDouble(String.valueOf(item[1])), String.valueOf(item[2])))
            }
        }
        return registeredPatientsByDispenseTypeList
    }


    def List<PatientsFirstDispenseByAge> getPatientsFirstDispenseByAge (Date startDate, Date endDate, String clinicId, String serviceCode) {

        Clinic clinic = Clinic.findById(clinicId)

        String queryString ="select count(*) quantity, pat_disp.month, pat_disp.faixa " +
                "from ( " +
                "  select  " +
                "      distinct(p.id), " +
                "      psi.start_date, " +
                "      dt.code as dispense_type, " +
                "      cs.code as service, " +
                "      pv2.visit_date, " +
                "      (CASE  " +
                "         WHEN (date_part('year', age(p.date_of_birth)) < 18) THEN 'MENOR' " +
                "         WHEN (date_part('year', age(p.date_of_birth)) >= 18) THEN 'ADULTO' " +
                "    END) as faixa, " +
                "      (CASE  " +
                "         WHEN (EXTRACT(DAY FROM pv2.visit_date) > 20) THEN EXTRACT(MONTH FROM (pv2.visit_date + interval '1 month')) " +
                "         WHEN (EXTRACT(DAY FROM pv2.visit_date) <= 20) THEN EXTRACT(MONTH FROM pv2.visit_date) " +
                "    END) as month " +
                "   from patient_visit pv2 inner join patient p on pv2.patient_id = p.id  " +
                "         inner join patient_service_identifier psi on p.id = psi.patient_id  " +
                "         inner join episode e on e.patient_service_identifier_id  = psi .id  " +
                "         inner join patient_visit_details pvd on pvd.episode_id = e.id and pvd.patient_visit_id = pv2.id  " +
                "         inner join prescription pre on pre.id = pvd.prescription_id  " +
                "         inner join prescription_detail pd on pd.prescription_id = pre.id  " +
                "         inner join dispense_type dt on dt.id = pd.dispense_type_id  " +
                "           inner join clinical_service cs on psi.service_id = cs.id  " +
                "  where 1=1 and  " +
                "   not exists (select * " +
                "       from patient_visit pv3 " +
                "       where pv3.patient_id = pv2.patient_id " +
                "       and pv3.visit_date < pv2.visit_date) " +
                "   and (pv2.visit_date >= :startDate and pv2.visit_date <= :endDate) " +
                "   and cs.code = :service " +
                //"   and pre.patient_type = 'INICIO' " +
                " ) pat_disp " +
                " group by pat_disp.dispense_type, pat_disp.month, pat_disp.faixa " +
                " order by pat_disp.month asc"

        Session session = sessionFactory.getCurrentSession()
        def query = session.createSQLQuery(queryString)
        query.setParameter("startDate", startDate)
        query.setParameter("endDate", endDate)
        query.setParameter("service", serviceCode)
        List<Object[]> result = query.list()

        List<PatientsFirstDispenseByAge> registeredPatientsByDispenseTypeList = new ArrayList<>()

        if (Utilities.listHasElements(result as ArrayList<?>)) {

            for (item in result) {
                registeredPatientsByDispenseTypeList.add(new PatientsFirstDispenseByAge(Integer.valueOf(String.valueOf(item[0])), (int) Double.parseDouble(String.valueOf(item[1])), String.valueOf(item[2])))
            }
        }
        return registeredPatientsByDispenseTypeList
    }

    def List<ActivePatientPercentage> getActivePatientPercentage (Date endDate, String clinicId, String serviceCode) {

        Clinic clinic = Clinic.findById(clinicId)

        String queryString ="SELECT count(*) / SUM(COUNT(*)) OVER () * 100 percent, pat.gender, count(*) quantity " +
                            "FROM Patient pat " +
                            "                   INNER JOIN patient_service_identifier psi ON psi.patient_id = pat.id " +
                            "                   INNER JOIN clinical_service cs ON psi.service_id = cs.id " +
                            "                   INNER JOIN identifier_type idt ON psi.identifier_type_id = idt.id " +
                            "                   INNER JOIN ( SELECT patient_service_identifier_id, start_stop_reason_id, id, max(episode_date) as episode_date " +
                            "                               from episode " +
                            "                               Group by 1,2,3 ) as ep on ep.patient_service_identifier_id = psi.id and ep.episode_date <= :endDate " +
                            "                   INNER JOIN start_stop_reason ststreason ON ep.start_stop_reason_id =  ststreason.id and ststreason.is_start_reason = true " +
                            "                   INNER JOIN ( SELECT patient_id, max(visit_date) as visit_date " +
                            "                               from patient_visit " +
                            "                               Group by 1 ) as pvAux on pvAux.patient_id = pat.id " +
                            "                   INNER JOIN patient_visit pv on pv.visit_date = pvAux.visit_date and pv.patient_id = pvAux.patient_id " +
                            "                   INNER JOIN patient_visit_details pvd  ON pvd.episode_id = ep.id and pvd.patient_visit_id = pv.id " +
                            "                   INNER JOIN pack on pvd.pack_id = pack.id " +
                            "                   INNER JOIN ( SELECT id, clinic_id, patient_type, max(prescription.prescription_date) as prescription_date " +
                            "                               from prescription " +
                            "                               Group by 1,2,3 ) as pre on pvd.prescription_id = pre.id and pre.prescription_date <= pack.pickup_date " +
                            "                   INNER JOIN prescription_detail pred ON pred.prescription_id = pre.id " +
                            "                   INNER JOIN therapeutic_regimen regime ON pred.therapeutic_regimen_id = regime.id " +
                            "                   INNER JOIN dispense_type dt ON pred.dispense_type_id = dt.id " +
                            "                   INNER JOIN therapeutic_line lt ON pred.therapeutic_line_id = lt.id " +
                            "where DATE(pack.next_pick_up_date) + :days >= :endDate " +
                            "   and cs.code = :service " +
                            "group by pat.gender order by pat.gender asc"

        Session session = sessionFactory.getCurrentSession()
        def query = session.createSQLQuery(queryString)
        query.setParameter("days", 3)
        query.setParameter("endDate", endDate)
        query.setParameter("service", serviceCode)
        List<Object[]> result = query.list()

        List<ActivePatientPercentage> registeredPatientsByDispenseTypeList = new ArrayList<>()

        if (Utilities.listHasElements(result as ArrayList<?>)) {

            for (item in result) {
                registeredPatientsByDispenseTypeList.add(new ActivePatientPercentage((int) Double.parseDouble(String.valueOf(item[2])), Double.parseDouble(String.valueOf(item[0])),  String.valueOf(item[1])))
            }
        }
        return registeredPatientsByDispenseTypeList
    }


    def List<DispensesByAge> getDispenseByAge (Date startDate, Date endDate, String clinicId, String serviceCode) {

        Clinic clinic = Clinic.findById(clinicId)

        String queryString ="select  dt.description  dispense_type,   " +
                "SUM(CASE WHEN date_part('year', age(pat.date_of_birth)) >= 18 THEN 1 ELSE 0 END) ADULTO,  " +
                "SUM(CASE WHEN date_part('year', age(pat.date_of_birth)) < 18 THEN 1 ELSE 0 END) MENOR  " +
                "from pack p inner join patient_visit_details pvd on p.id = pvd.pack_id   " +
                "      inner join patient_visit pv on pv.id = pvd.patient_visit_id   " +
                "      inner join episode e on e.id = pvd.episode_id   " +
                "      inner join patient_service_identifier psi on psi.id = e.patient_service_identifier_id   " +
                "      inner join clinical_service cs on cs.id = psi.service_id   " +
                "      inner join prescription pre on pre.id = pvd.prescription_id   " +
                "      inner join prescription_detail pd on pd.prescription_id = pre.id   " +
                "      inner join dispense_type dt ON dt.id = pd.dispense_type_id   " +
                "      inner join patient pat on pat.id = pv.patient_id   " +
                "where (p.pickup_date  >= :startDate and p.pickup_date  <= :endDate)  " +
                "    and cs.code = :service  " +
                "    and p.clinic_id = :clinic " +
                "group by dt.description"

        Session session = sessionFactory.getCurrentSession()
        def query = session.createSQLQuery(queryString)
        query.setParameter("startDate", startDate)
        query.setParameter("endDate", endDate)
        query.setParameter("service", serviceCode)
        query.setParameter("clinic", clinic.id)
        List<Object[]> result = query.list()

        List<DispensesByAge> registeredPatientsByDispenseTypeList = new ArrayList<>()

        if (Utilities.listHasElements(result as ArrayList<?>)) {

            for (item in result) {
                registeredPatientsByDispenseTypeList.add(new DispensesByAge((int) Double.parseDouble(String.valueOf(item[1])), (int) Double.parseDouble(String.valueOf(item[2])), String.valueOf(item[0])))
            }
        }
        return registeredPatientsByDispenseTypeList
    }

    def List<DispensesByGender> getDispensesByGender (Date startDate, Date endDate, String clinicId, String serviceCode) {

        Clinic clinic = Clinic.findById(clinicId)

        String queryString ="select  dt.description  dispense_type,   " +
                "SUM(CASE WHEN pat.gender = 'Masculino' THEN 1 ELSE 0 END) masculino,  " +
                "SUM(CASE WHEN pat.gender = 'Feminino' THEN 1 ELSE 0 END) feminino  " +
                "from pack p inner join patient_visit_details pvd on p.id = pvd.pack_id   " +
                "      inner join patient_visit pv on pv.id = pvd.patient_visit_id   " +
                "      inner join episode e on e.id = pvd.episode_id   " +
                "      inner join patient_service_identifier psi on psi.id = e.patient_service_identifier_id   " +
                "      inner join clinical_service cs on cs.id = psi.service_id   " +
                "      inner join prescription pre on pre.id = pvd.prescription_id   " +
                "      inner join prescription_detail pd on pd.prescription_id = pre.id   " +
                "      inner join dispense_type dt ON dt.id = pd.dispense_type_id   " +
                "      inner join patient pat on pat.id = pv.patient_id   " +
                "where (p.pickup_date  >= :startDate and p.pickup_date  <= :endDate)  " +
                "    and cs.code = :service  " +
                "    and p.clinic_id = :clinic " +
                "group by dt.description"

        Session session = sessionFactory.getCurrentSession()
        def query = session.createSQLQuery(queryString)
        query.setParameter("startDate", startDate)
        query.setParameter("endDate", endDate)
        query.setParameter("service", serviceCode)
        query.setParameter("clinic", clinic.id)
        List<Object[]> result = query.list()

        List<DispensesByGender> registeredPatientsByDispenseTypeList = new ArrayList<>()

        if (Utilities.listHasElements(result as ArrayList<?>)) {

            for (item in result) {
                registeredPatientsByDispenseTypeList.add(new DispensesByGender((int) Double.parseDouble(String.valueOf(item[1])), (int) Double.parseDouble(String.valueOf(item[2])), String.valueOf(item[0])))
            }
        }
        return registeredPatientsByDispenseTypeList
    }


    def List<StockAlert> getStockAlert (String clinicId, String serviceCode) {

        Clinic clinic = Clinic.findById(clinicId)

        String queryString ="select   " +
                "  id,  " +
                "  drug,  " +
                "  balance,  " +
                "  consuption / 3 as avg_consuption,  " +
                "  (CASE   " +
                "      WHEN consuption = 0 THEN 'Sem Consumo'  " +
                "      WHEN balance > (consuption / 3) THEN 'Acima do Consumo Máximo'  " +
                "      WHEN balance < (consuption / 3) THEN 'Ruptura de Stock'  " +
                "      ELSE 'Stock Normal'  " +
                "  END) as state  " +
                "from (  " +
                "select d.id, d.name drug, sum(s.stock_moviment) balance,  " +
                "     (select (coalesce(sum(pds.quantity_supplied), 0))  " +
                "      from packaged_drug pd inner join pack p on pd.pack_id = p.id  " +
                "                  inner join packaged_drug_stock pds on pds.packaged_drug_id = pd.id  " +
                "      where pd.drug_id = d.id  and (p.pickup_date >=  CURRENT_DATE - interval '1 month')) consuption  " +
                "from stock s inner join stock_entrance se on s.entrance_id = se.id   " +
                "       inner join drug d on d.id = s.drug_id   " +
                "       inner join clinical_service cs on cs.id = d.clinical_service_id   " +
                "where cs.code = :service and se.clinic_id =  :clinic " +
                "group by d.id, d.name ) stock_consuption  "

        Session session = sessionFactory.getCurrentSession()
        def query = session.createSQLQuery(queryString)
        query.setParameter("service", serviceCode)
        query.setParameter("clinic", clinic.id)
        List<Object[]> result = query.list()

        List<StockAlert> registeredPatientsByDispenseTypeList = new ArrayList<>()

        if (Utilities.listHasElements(result as ArrayList<?>)) {

            for (item in result) {
                registeredPatientsByDispenseTypeList.add(new StockAlert(String.valueOf(item[0]), String.valueOf(item[1]), (int) Double.parseDouble(String.valueOf(item[2])), Double.parseDouble(String.valueOf(item[3])), String.valueOf(item[4])))
            }
        }
        return registeredPatientsByDispenseTypeList
    }


    def List<DashboardServiceButton> getDashboardServiceButton(Date endDate, String clinicId) {

        Clinic clinic = Clinic.findById(clinicId)

        String queryString ="select count(*) quantity, cs.code  service  " +
                "from patient_service_identifier psi inner join clinical_service cs on psi.service_id = cs.id   " +
                "where psi.clinic_id = :clinic and psi.start_date <= :endDate " +
                "group by cs.code "

        Session session = sessionFactory.getCurrentSession()
        def query = session.createSQLQuery(queryString)
        query.setParameter("endDate", endDate)
        query.setParameter("clinic", clinic.id)
        List<Object[]> result = query.list()

        List<DashboardServiceButton> registeredPatientsByDispenseTypeList = new ArrayList<>()

        if (Utilities.listHasElements(result as ArrayList<?>)) {

            for (item in result) {
                registeredPatientsByDispenseTypeList.add(new DashboardServiceButton((int) Double.parseDouble(String.valueOf(item[0])), String.valueOf(item[1])))
            }
        }
        return registeredPatientsByDispenseTypeList
    }
}
