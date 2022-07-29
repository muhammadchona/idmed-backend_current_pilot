package mz.org.fgh.sifmoz.backend.migration.stage

import grails.gorm.transactions.Transactional
import groovy.util.logging.Slf4j
import mz.org.fgh.sifmoz.backend.migration.base.engine.MigrationEngineImpl
import mz.org.fgh.sifmoz.backend.migration.entity.patient.PatientMigrationRecord
import mz.org.fgh.sifmoz.backend.migration.params.PatientMigrationSearchParams
import mz.org.fgh.sifmoz.backend.multithread.ExecutorThreadProvider
import mz.org.fgh.sifmoz.backend.task.SynchronizerTask
import mz.org.fgh.sifmoz.backend.utilities.Utilities
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.EnableScheduling

import java.util.concurrent.ExecutorService

@Transactional
@EnableScheduling
@Slf4j
class MigrationService extends SynchronizerTask{

    List<MigrationStage> migrationStageList
    MigrationStage curMigrationStage
    List<MigrationEngineImpl> migrationEngineList
    private static ExecutorService executor;
    @Autowired
    MigrationStageService migrationStageService


    @Override
    void execute() {
        if (!isProvincial()) {
            curMigrationStage = MigrationStage.findByValue("IN_PROGRESS")
            executor = ExecutorThreadProvider.getInstance().getExecutorService();
            migrationEngineList = new ArrayList<>();
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
    }

    private void initPatientMigrationEngine () {
        PatientMigrationSearchParams params = new PatientMigrationSearchParams()
        MigrationEngineImpl<PatientMigrationRecord> patientMigrationEngine = new MigrationEngineImpl<>(params, MigrationEngineImpl.PATIENT_MIGRATION_ENGINE)
        this.migrationEngineList.add(patientMigrationEngine)
    }
}
