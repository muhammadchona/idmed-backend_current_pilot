package mz.org.fgh.sifmoz.migration.base.record;

import mz.org.fgh.sifmoz.backend.restUtils.RestServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractMigrationRecord implements MigrationRecord{

    protected MigratedRecord migratedRecord;
    static Logger logger = LogManager.getLogger(AbstractMigrationRecord.class);

    public AbstractMigrationRecord() {
        this.migratedRecord = initMigratedRecord();
    }

    @Override
    public void setAsMigratedSuccessfully() {
        logger.info("Registo migrado com sucesso: origem ["+this.getEntityName() +" : "+ this.getId()+"] - destino: ["+this.migratedRecord.getEntity() +" : "+  this.migratedRecord.getId()+"]");
    }

    @Override
    public void setAsRejectedForMigration() {
        logger.info("Registo não migrado: ["+this.getEntityName() +" : "+  this.getId()+"]");
    }

    public MigratedRecord getMigratedRecord() {
        return migratedRecord;
    }
}
