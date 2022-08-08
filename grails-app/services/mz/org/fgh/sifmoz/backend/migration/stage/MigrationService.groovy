package mz.org.fgh.sifmoz.backend.migration.stage

import com.google.gson.Gson
import grails.gorm.transactions.Transactional
import groovy.util.logging.Slf4j
import mz.org.fgh.sifmoz.backend.migration.base.engine.MigrationEngineImpl
import mz.org.fgh.sifmoz.backend.migration.base.status.MigrationSatus
import mz.org.fgh.sifmoz.backend.migration.entity.parameter.clinic.ClinicMigrationRecord
import mz.org.fgh.sifmoz.backend.migration.entity.parameter.clinicSector.ClinicSectorMigrationRecord
import mz.org.fgh.sifmoz.backend.migration.entity.parameter.doctor.DoctorMigrationRecord
import mz.org.fgh.sifmoz.backend.migration.entity.parameter.drug.DrugMigrationRecord
import mz.org.fgh.sifmoz.backend.migration.entity.parameter.regimeTerapeutico.RegimeTerapeuticoMigrationRecord
import mz.org.fgh.sifmoz.backend.migration.entity.patient.PatientMigrationRecord
import mz.org.fgh.sifmoz.backend.migration.entity.prescription.PackagedDrugsMigrationRecord
import mz.org.fgh.sifmoz.backend.migration.entity.prescription.PrescribedDrugsMigrationRecord
import mz.org.fgh.sifmoz.backend.migration.entity.prescription.PrescriptionMigrationRecord
import mz.org.fgh.sifmoz.backend.migration.entity.stock.StockAdjustmentMigrationRecord
import mz.org.fgh.sifmoz.backend.migration.entity.stock.StockCenterMigrationRecord
import mz.org.fgh.sifmoz.backend.migration.entity.stock.StockMigrationRecord
import mz.org.fgh.sifmoz.backend.migration.entity.stock.StockTakeMigrationRecord
import mz.org.fgh.sifmoz.backend.migration.params.patient.PatientMigrationSearchParams
import mz.org.fgh.sifmoz.backend.migration.params.parameter.ClinicMigrationSearchParams
import mz.org.fgh.sifmoz.backend.migration.params.parameter.ClinicSectorMigrationSearchParams
import mz.org.fgh.sifmoz.backend.migration.params.parameter.DoctorMigrationSearchParams
import mz.org.fgh.sifmoz.backend.migration.params.parameter.DrugMigrationSearchParams
import mz.org.fgh.sifmoz.backend.migration.params.parameter.RegimeTerapeuticoMigrationSearchParams
import mz.org.fgh.sifmoz.backend.migration.params.prescription.PackagedDrugsMigrationSearchParams
import mz.org.fgh.sifmoz.backend.migration.params.prescription.PrescribedDrugsMigrationSearchParams
import mz.org.fgh.sifmoz.backend.migration.params.prescription.PrescriptionMigrationSearchParams
import mz.org.fgh.sifmoz.backend.migration.params.stock.StockAdjustmentSearchParams
import mz.org.fgh.sifmoz.backend.migration.params.stock.StockCenterMigrationSearchParams
import mz.org.fgh.sifmoz.backend.migration.params.stock.StockMigrationSearchParams
import mz.org.fgh.sifmoz.backend.migration.params.stock.StockTakeMigrationSearchParams
import mz.org.fgh.sifmoz.backend.multithread.ExecutorThreadProvider
import mz.org.fgh.sifmoz.backend.restUtils.RestService
import mz.org.fgh.sifmoz.backend.task.SynchronizerTask
import mz.org.fgh.sifmoz.backend.utilities.Utilities
import org.grails.web.json.JSONArray
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.EnableScheduling

import java.util.concurrent.ExecutorService

@Transactional
@EnableScheduling
@Slf4j
class MigrationService extends SynchronizerTask implements Runnable{

    MigrationStage curMigrationStage
    List<MigrationEngineImpl> migrationEngineList
    private static ExecutorService executor;
    @Autowired
    MigrationStageService migrationStageService

    @Override
    void execute() {
        if (!isProvincial()) {
            curMigrationStage = MigrationStage.findByValue(MigrationStage.STAGE_IN_PROGRESS)
            if (curMigrationStage == null) return
            executor = ExecutorThreadProvider.getInstance().getExecutorService();
            if (!Utilities.listHasElements(migrationEngineList as ArrayList<?>)) migrationEngineList = new ArrayList<>();
            initMigrationEngines()
            initMigrationProcess()
        }
    }

    private void initMigrationEngines() {
        initPatientMigrationEngine()
        initStockMigrationEngine()
        initParameterMigrationEngine()
    }

    private void initMigrationProcess() {
        if (!Utilities.listHasElements(migrationEngineList as ArrayList<?>)) return

        for (MigrationEngineImpl engine : migrationEngineList) {
            if (engine.getRelatedStage() == this.curMigrationStage.getCode()) {
                executor.execute(engine)
            }
        }
        //if (existRunningEngineOnStage()) {
            while (getCurrStageStatus().getStage_progress() < 100 || getCurrStageStatus().getTotal_rejcted() > 0) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        MigrationStage.withTransaction {
            curMigrationStage.setValue(MigrationStage.STAGE_COMPLETED)
            //migrationStageService.save(curMigrationStage)
            curMigrationStage.save(flush: true, insert: true)
            if (curMigrationStage.getCode() == MigrationEngineImpl.PARAMS_MIGRATION_STAGE) {
                curMigrationStage = MigrationStage.findByCode(MigrationEngineImpl.STOCK_MIGRATION_STAGE)
                curMigrationStage.setValue(MigrationStage.STAGE_IN_PROGRESS)
                curMigrationStage.save(flush: true, insert: true)
                this.execute()
            } else if (curMigrationStage.getCode() == MigrationEngineImpl.STOCK_MIGRATION_STAGE) {
                curMigrationStage = MigrationStage.findByCode(MigrationEngineImpl.PATIENT_MIGRATION_STAGE)
                curMigrationStage.setValue(MigrationStage.STAGE_IN_PROGRESS)
                curMigrationStage.save(flush: true, insert: true)
                this.execute()
            }
        }
    }

    private void initPatientMigrationEngine () {
        PatientMigrationSearchParams params = new PatientMigrationSearchParams()
        PrescriptionMigrationSearchParams prescriptionMigrationSearchParams = new PrescriptionMigrationSearchParams()
        PackagedDrugsMigrationSearchParams packagedDrugsMigrationSearchParams = new PackagedDrugsMigrationSearchParams()
        PrescribedDrugsMigrationSearchParams prescribedDrugsMigrationSearchParams = new PrescribedDrugsMigrationSearchParams()

        MigrationEngineImpl<PatientMigrationRecord> patientMigrationEngine = new MigrationEngineImpl<>(params, MigrationEngineImpl.PATIENT_MIGRATION_ENGINE)
        MigrationEngineImpl<PrescriptionMigrationRecord> prescriptionMigrationEngine = new MigrationEngineImpl<>(prescriptionMigrationSearchParams, MigrationEngineImpl.PATIENT_MIGRATION_ENGINE)
        MigrationEngineImpl<PackagedDrugsMigrationRecord> packagedDrugsMigrationEngine = new MigrationEngineImpl<>(packagedDrugsMigrationSearchParams, MigrationEngineImpl.PATIENT_MIGRATION_ENGINE)
        MigrationEngineImpl<PrescribedDrugsMigrationRecord> prescribedDrugsMigrationEngine = new MigrationEngineImpl<>(prescribedDrugsMigrationSearchParams, MigrationEngineImpl.PATIENT_MIGRATION_ENGINE)
        this.migrationEngineList.add(patientMigrationEngine)
        this.migrationEngineList.add(prescriptionMigrationEngine)
        this.migrationEngineList.add(packagedDrugsMigrationEngine)
        this.migrationEngineList.add(prescribedDrugsMigrationEngine)
    }

    private void initStockMigrationEngine () {
        StockMigrationSearchParams params = new StockMigrationSearchParams()
        MigrationEngineImpl<StockMigrationRecord> stockMigrationEngine = new MigrationEngineImpl<>(params, MigrationEngineImpl.STOCK_MIGRATION_ENGINE)
        StockTakeMigrationSearchParams paramsStockTake = new StockTakeMigrationSearchParams()
        MigrationEngineImpl<StockTakeMigrationRecord> stockTakeMigrationEngine = new MigrationEngineImpl<>(paramsStockTake, MigrationEngineImpl.STOCK_MIGRATION_ENGINE)
        StockCenterMigrationSearchParams paramsStockCenter = new StockCenterMigrationSearchParams()
        MigrationEngineImpl<StockCenterMigrationRecord> stockCenterMigrationEngine = new MigrationEngineImpl<>(paramsStockCenter, MigrationEngineImpl.STOCK_MIGRATION_ENGINE)
        StockAdjustmentSearchParams paramsStockAdjustment = new StockAdjustmentSearchParams()
        MigrationEngineImpl<StockAdjustmentMigrationRecord> stockAdjustmentMigrationEngine = new MigrationEngineImpl<>(paramsStockAdjustment, MigrationEngineImpl.STOCK_MIGRATION_ENGINE)
        this.migrationEngineList.add(stockCenterMigrationEngine)
        this.migrationEngineList.add(stockMigrationEngine)
        this.migrationEngineList.add(stockTakeMigrationEngine)
        this.migrationEngineList.add(stockAdjustmentMigrationEngine)
    }

    private void initParameterMigrationEngine(){
        ClinicMigrationSearchParams paramsClinic = new ClinicMigrationSearchParams()
        ClinicSectorMigrationSearchParams paramsClinicSector = new ClinicSectorMigrationSearchParams()
        DoctorMigrationSearchParams paramsDoctor = new DoctorMigrationSearchParams()
        DrugMigrationSearchParams paramsDrug = new DrugMigrationSearchParams()
        RegimeTerapeuticoMigrationSearchParams paramsRegimen = new RegimeTerapeuticoMigrationSearchParams()

        MigrationEngineImpl<ClinicMigrationRecord> clinicMigrationEngine = new MigrationEngineImpl<>(paramsClinic, MigrationEngineImpl.PARAMS_MIGRATION_ENGINE)
        MigrationEngineImpl<ClinicSectorMigrationRecord> clinicSectorMigrationEngine = new MigrationEngineImpl<>(paramsClinicSector, MigrationEngineImpl.PARAMS_MIGRATION_ENGINE)
        MigrationEngineImpl<DoctorMigrationRecord> doctorMigrationEngine = new MigrationEngineImpl<>(paramsDoctor, MigrationEngineImpl.PARAMS_MIGRATION_ENGINE)
        MigrationEngineImpl<DrugMigrationRecord> drugMigrationEngine = new MigrationEngineImpl<>(paramsDrug, MigrationEngineImpl.PARAMS_MIGRATION_ENGINE)
        MigrationEngineImpl<RegimeTerapeuticoMigrationRecord> regimeTerapeuticoMigrationEngine = new MigrationEngineImpl<>(paramsRegimen, MigrationEngineImpl.PARAMS_MIGRATION_ENGINE)

        this.migrationEngineList.add(clinicMigrationEngine)
        this.migrationEngineList.add(clinicSectorMigrationEngine)
        this.migrationEngineList.add(doctorMigrationEngine)
        this.migrationEngineList.add(drugMigrationEngine)
        this.migrationEngineList.add(regimeTerapeuticoMigrationEngine)
    }

    private MigrationSatus getCurrStageStatus() {
        List<MigrationSatus> migrationSatuses = getMigrationStatus()
        for (MigrationSatus migrationSatus : migrationSatuses) {
            if (migrationSatus.getMigration_stage() == curMigrationStage.getCode()) return migrationSatus
        }
        return null
    }

    public List<MigrationSatus> getMigrationStatus() {
        Gson gson = new Gson()
        RestService restService = new RestService("MIGRATION", "IDART")
        List<MigrationSatus> migrationSatuses = new ArrayList<>()
        JSONArray jsonArray = restService.get("/migration_progress")
        MigrationSatus[] migrationStatusList = gson.fromJson(jsonArray.toString(), MigrationSatus[].class);
        migrationSatuses.addAll(Arrays.asList(migrationStatusList))
        return migrationSatuses
    }

    boolean existRunningEngineOnStage() {
        for (MigrationEngineImpl engine : this.migrationEngineList) {
            if (engine.isRunning() && engine.getRelatedStage() == this.curMigrationStage.getCode()) return true
        }
        return false
    }

    @Override
    void run() {
        this.execute()
    }
}
