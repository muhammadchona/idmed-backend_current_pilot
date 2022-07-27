package mz.org.fgh.sifmoz.migration.entity.prescription;

import mz.org.fgh.sifmoz.backend.migrationLog.MigrationLog;
import mz.org.fgh.sifmoz.migration.base.log.AbstractMigrationLog;
import mz.org.fgh.sifmoz.migration.base.record.AbstractMigrationRecord;
import mz.org.fgh.sifmoz.migration.base.record.MigratedRecord;
import mz.org.fgh.sifmoz.migration.base.record.MigrationRecord;

import java.util.List;

public class LinhaTerapeuticaMigrationRecord extends AbstractMigrationRecord {
    private int linhaid;
    private String linhanome;
    private boolean active;

    public int getLinhaid() {
        return linhaid;
    }

    public void setLinhaid(int linhaid) {
        this.linhaid = linhaid;
    }

    public String getLinhanome() {
        return linhanome;
    }

    public void setLinhanome(String linhanome) {
        this.linhanome = linhanome;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public List<MigrationLog> migrate() {
        return null;
    }

    @Override
    public void updateIDMEDInfo() {

    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public String getEntityName() {
        return null;
    }

    @Override
    public void generateUnknowMigrationLog(MigrationRecord record, String message) {

    }

    @Override
    public MigratedRecord initMigratedRecord() {
        return null;
    }
}
