package mz.org.fgh.sifmoz.migration.base.engine;

import mz.org.fgh.sifmoz.backend.healthInformationSystem.SystemConfigs;
import mz.org.fgh.sifmoz.backend.migrationLog.MigrationLog;
import mz.org.fgh.sifmoz.migration.base.log.AbstractMigrationLog;
import mz.org.fgh.sifmoz.migration.base.record.AbstractMigrationRecord;
import mz.org.fgh.sifmoz.migration.base.record.MigrationRecord;
import mz.org.fgh.sifmoz.migration.base.search.SearchEngine;
import mz.org.fgh.sifmoz.backend.utilities.Utilities;
import mz.org.fgh.sifmoz.migration.base.search.params.AbstractMigrationSearchParams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class MigrationEngineImpl<T extends AbstractMigrationRecord> implements MigrationEngine<T> {

    static Logger logger = LogManager.getLogger(MigrationEngineImpl.class);

    protected SearchEngine<T> searchEngine;
    protected List<T> recordList;
    protected AbstractMigrationSearchParams<T> searchParams;
    private String engineType;
    private List<SystemConfigs> systemConfigs;

    public static final String PARAMS_MIGRATION_STAGE = "PARAMS_MIGRATION_STAGE";
    public static final String STOCK_MIGRATION_STAGE = "STOCK_MIGRATION_STAGE";
    public static final String PATIENT_MIGRATION_STAGE = "PATIENT_MIGRATION_STAGE";

    public static final String MIGRATION_ON= "ON";
    public static final String MIGRATION_OFF = "OFF";

    public static final String PATIENT_MIGRATION_ENGINE = "PATIENT_MIGRATION_ENGINE";
    public static final String STOCK_MIGRATION_ENGINE = "STOCK_MIGRATION_ENGINE";
    public static final String PARAMS_MIGRATION_ENGINE = "PARAMS_MIGRATION_ENGINE";

    public MigrationEngineImpl(AbstractMigrationSearchParams<T> searchParams, String engineType, List<SystemConfigs> systemConfigs) {
        this.searchParams = searchParams;
        this.searchEngine = this.initSearchEngine(searchParams);
        this.engineType = engineType;
        this.systemConfigs = systemConfigs;
    }

    public void doMigration() {
        this.searchRecords();

        if (Utilities.listHasElements((ArrayList<?>) this.recordList)) {
            for (MigrationRecord record : recordList) {
                logger.info("Iniciando a migração do registo [" + record.getEntityName()+" : "+record.getId()+"]");

                List<MigrationLog> migrationLogs = null;
                try {
                    migrationLogs = record.migrate();
                } catch (Exception e) {
                    migrationLogs = record.generateUnknowMigrationLog(record, e.getMessage());
                } finally {
                    if (Utilities.listHasElements((ArrayList<?>) migrationLogs)) {
                        record.setAsRejectedForMigration(this.searchParams.getRestServiceProvider());
                        record.saveMigrationLogs(migrationLogs);
                    } else {
                        record.setAsMigratedSuccessfully(this.searchParams.getRestServiceProvider());
                        logger.info("Registo migrado com sucesso, Destino no iDMED: [" + record.getMigratedRecord().getEntity()+" : ID gerado: "+record.getMigratedRecord().getId()+"]");
                    }
                }
            }
            while(stopRequested()) {
                try {
                    logger.info("Motor de Migração " + engineType + "Desligado");
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(!stopRequested()) {
                this.doMigration();
            }
        }
    }

    private SystemConfigs getEngineConfig(){
        for (SystemConfigs configs: this.systemConfigs) {
            if (configs.getKey().equals(this.engineType)) return configs;
        }
        return null;
    }

    private boolean stopRequested() {
        if (getEngineConfig() == null) throw new RuntimeException("Não foram encontradas as configurações desta migração.");
        if (getEngineConfig().getValue().equals(MIGRATION_OFF)) return true;
        return false;
    }


    public void searchRecords () {
        this.recordList = this.searchEngine.doSearch();
    }

    @Override
    public SearchEngine<T> initSearchEngine(AbstractMigrationSearchParams<T> searchParams) {
        return new SearchEngine<>(searchParams);
    }

    @Override
    public void run() {
        while(stopRequested()) {
            try {
                logger.info("Motor de Migração " + engineType + "Desligado");
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!stopRequested()) this.doMigration();
    }
}
