package mz.org.fgh.sifmoz.backend.migration.base.record;

import groovy.lang.Closure;
import mz.org.fgh.sifmoz.backend.migrationLog.MigrationLog;
import mz.org.fgh.sifmoz.backend.migrationLog.MigrationLogService;
import mz.org.fgh.sifmoz.backend.restUtils.RestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public abstract class AbstractMigrationRecord implements MigrationRecord{

    protected MigratedRecord migratedRecord;
    static Logger logger = LogManager.getLogger(AbstractMigrationRecord.class);
    @Autowired
    protected MigrationLogService migrationLogService;

    public AbstractMigrationRecord() {
        this.migratedRecord = initMigratedRecord();
    }

    @Override
    public void setAsMigratedSuccessfully(RestService restServiceProvider) {
        logger.info("Registo migrado com sucesso: origem ["+this.getEntityName() +" : "+ this.getId()+"] - destino: ["+this.migratedRecord.getEntity() +" : "+  this.migratedRecord.getId()+"]");
        String uri = "/" + this.getEntityName().toLowerCase() + "?id=eq." + getId();
        restServiceProvider.patch(uri, "{\"migration_status\":\"MIGRATED\"}");
        MigrationLog log = new MigrationLog("UNKNOWN", "Registo Migrado com Sucesso", getId(), getEntityName(), this.migratedRecord.getId(), this.migratedRecord.getEntity())
        MigrationLog.withTransaction {
                log.save(flush: true);
            }
    }

    @Override
    public List<MigrationLog> generateUnknowMigrationLog(MigrationRecord record, String message) {
        List<MigrationLog> migrationLogs = new ArrayList<>();
        migrationLogs.add(new MigrationLog("UNKNOWN", message, record.getId(), record.getEntityName()));
        return migrationLogs;
    }

    @Override
    public void setAsRejectedForMigration(RestService restServiceProvider) {
        logger.info("Registo não migrado: ["+this.getEntityName() +" : "+  this.getId()+"]");
        String uri = "/" + this.getEntityName().toLowerCase() + "?id=eq." + getId();
        restServiceProvider.patch(uri, "{\"migration_status\":\"REJECTED\"}");
    }

    public MigratedRecord getMigratedRecord() {
        return migratedRecord;
    }

    @Override
    public void saveMigrationLogs(List<MigrationLog> migrationLogs) {
        MigrationLog.withTransaction {
            for (MigrationLog migrationLog: migrationLogs) {
                migrationLog.save(flush: true);
            }
        }
    }
}
