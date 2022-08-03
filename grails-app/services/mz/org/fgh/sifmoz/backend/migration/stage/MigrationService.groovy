package mz.org.fgh.sifmoz.backend.migration.stage

import com.google.gson.Gson
import grails.gorm.transactions.Transactional
import groovy.util.logging.Slf4j
import mz.org.fgh.sifmoz.backend.migration.base.engine.MigrationEngineImpl
import mz.org.fgh.sifmoz.backend.migration.base.status.MigrationSatus
import mz.org.fgh.sifmoz.backend.migration.entity.patient.PatientMigrationRecord
import mz.org.fgh.sifmoz.backend.migration.params.PatientMigrationSearchParams
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
class MigrationService extends SynchronizerTask{

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
    }

    private void initMigrationProcess() {
        if (!Utilities.listHasElements(migrationEngineList as ArrayList<?>)) return

        for (MigrationEngineImpl engine : migrationEngineList) {
            if (engine.getRelatedStage() == this.curMigrationStage.getCode()) {
                executor.execute(engine)
            }
        }
        if (existRunningEngineOnStage()) {
            while (getCurrStageStatus().getStage_progress() < 100 || getCurrStageStatus().getTotal_rejcted() > 0) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            curMigrationStage.setValue(MigrationStage.STAGE_COMPLETED)
            migrationStageService.save(curMigrationStage)
            if (curMigrationStage.getCode() == MigrationEngineImpl.PARAMS_MIGRATION_STAGE) {
                curMigrationStage = MigrationStage.findByCode(MigrationEngineImpl.STOCK_MIGRATION_STAGE)
                curMigrationStage.setValue(MigrationStage.STAGE_IN_PROGRESS)
                migrationStageService.save(curMigrationStage)
                this.execute()
            } else if (curMigrationStage.getCode() == MigrationEngineImpl.STOCK_MIGRATION_STAGE) {
                curMigrationStage = MigrationStage.findByCode(MigrationEngineImpl.PATIENT_MIGRATION_STAGE)
                curMigrationStage.setValue(MigrationStage.STAGE_IN_PROGRESS)
                migrationStageService.save(curMigrationStage)
                this.execute()
            }
        }
    }

    private void initPatientMigrationEngine () {
        PatientMigrationSearchParams params = new PatientMigrationSearchParams()
        MigrationEngineImpl<PatientMigrationRecord> patientMigrationEngine = new MigrationEngineImpl<>(params, MigrationEngineImpl.PATIENT_MIGRATION_ENGINE)
        this.migrationEngineList.add(patientMigrationEngine)
    }

    private MigrationSatus getCurrStageStatus() {
        Gson gson = new Gson()
        RestService restService = new RestService("MIGRATION", "IDART")
        List<MigrationSatus> migrationSatuses = new ArrayList<>()
        JSONArray jsonArray = restService.get("/migration_progress")
        MigrationSatus[] migrationStatusList = gson.fromJson(jsonArray.toString(), MigrationSatus[].class);
        migrationSatuses.addAll(Arrays.asList(migrationStatusList))
        for (MigrationSatus migrationSatus : migrationSatuses) {
            if (migrationSatus.getMigration_stage() == curMigrationStage.getCode()) return migrationSatus
        }
        return null
    }

    boolean existRunningEngineOnStage() {
        for (MigrationEngineImpl engine : this.migrationEngineList) {
            if (engine.isRunning() && engine.getRelatedStage() == this.curMigrationStage.getCode()) return true
        }
        return false
    }
}
