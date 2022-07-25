package mz.org.fgh.sifmoz.migration.base.record;

public abstract class AbstractMigrationRecord implements MigrationRecord{

    protected MigratedRecord migratedRecord;

    public AbstractMigrationRecord() {
    }

    @Override
    public void setAsMigratedSuccessfully() {
        // Registo migrado com sucesso: origem [this.getEntityName() : this.getId()] - destino: [this.migratedRecord.getEntity() : this.migratedRecord.getId()]
    }

    @Override
    public void setAsRejectedForMigration() {
        // Registo não migrado: [this.entityName() : this.getId()]
    }

    public void setMigratedRecord(MigratedRecord migratedRecord) {
        this.migratedRecord = migratedRecord;
    }

    public MigratedRecord getMigratedRecord() {
        return migratedRecord;
    }
}
