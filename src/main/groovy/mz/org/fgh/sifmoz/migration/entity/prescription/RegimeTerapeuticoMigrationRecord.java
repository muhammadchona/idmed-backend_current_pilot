package mz.org.fgh.sifmoz.migration.entity.prescription;

import mz.org.fgh.sifmoz.migration.base.log.AbstractMigrationLog;
import mz.org.fgh.sifmoz.migration.base.record.AbstractMigrationRecord;
import mz.org.fgh.sifmoz.migration.base.record.MigratedRecord;
import mz.org.fgh.sifmoz.migration.base.record.MigrationRecord;

import java.util.List;

public class RegimeTerapeuticoMigrationRecord extends AbstractMigrationRecord {
    private Integer regimeid;
    private String regimeesquema;
    private boolean active;
    private String regimenomeespecificado;
    private String codigoregime;
    private String tipoDoenca;
    private String regimeesquemaidart;

    public Integer getRegimeid() {
        return regimeid;
    }

    public void setRegimeid(Integer regimeid) {
        this.regimeid = regimeid;
    }

    public String getRegimeesquema() {
        return regimeesquema;
    }

    public void setRegimeesquema(String regimeesquema) {
        this.regimeesquema = regimeesquema;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRegimenomeespecificado() {
        return regimenomeespecificado;
    }

    public void setRegimenomeespecificado(String regimenomeespecificado) {
        this.regimenomeespecificado = regimenomeespecificado;
    }

    public String getCodigoregime() {
        return codigoregime;
    }

    public void setCodigoregime(String codigoregime) {
        this.codigoregime = codigoregime;
    }

    public String getTipoDoenca() {
        return tipoDoenca;
    }

    public void setTipoDoenca(String tipoDoenca) {
        this.tipoDoenca = tipoDoenca;
    }

    public String getRegimeesquemaidart() {
        return regimeesquemaidart;
    }

    public void setRegimeesquemaidart(String regimeesquemaidart) {
        this.regimeesquemaidart = regimeesquemaidart;
    }

    @Override
    public List<AbstractMigrationLog> migrate() {
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