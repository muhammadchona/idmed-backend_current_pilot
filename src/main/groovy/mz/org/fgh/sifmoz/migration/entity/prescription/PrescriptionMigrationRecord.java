package mz.org.fgh.sifmoz.migration.entity.prescription;

import mz.org.fgh.sifmoz.migration.base.log.AbstractMigrationLog;
import mz.org.fgh.sifmoz.migration.base.record.AbstractMigrationRecord;
import mz.org.fgh.sifmoz.migration.base.record.MigratedRecord;
import mz.org.fgh.sifmoz.migration.base.record.MigrationRecord;
import mz.org.fgh.sifmoz.migration.entity.patient.DoctorMigrationRecord;
import mz.org.fgh.sifmoz.migration.entity.patient.PatientMigrationRecord;

import java.util.Date;
import java.util.List;

public class PrescriptionMigrationRecord extends AbstractMigrationRecord {

    private Integer id;

    private int clinicalStage;

    private char current;

    private Date date;
    private Date datainicionoutroservico;

    private DoctorMigrationRecord doctor;

    private RegimeTerapeuticoMigrationRecord regimeTerapeutico;


    private String motivoMudanca;

    private int duration;

    private char modified;

    private PatientMigrationRecord patient;
    private String prescriptionId;

    private String reasonForUpdate;

    private String notes;

    private Double weight;

    private Date endDate;

    private String drugTypes;

    private int dispensaTrimestral;

    private int dispensaSemestral;

    private String tipoDT;

    private String tipoDS;

    private String durationSentence;

    private String tipoDoenca;


    private LinhaTerapeuticaMigrationRecord linha;

    private char ppe;
    private char ptv;
    private char tb;
    private char tpc;
    private char tpi;
    private char saaj;

    private char gaac;
    private char af;
    private char ca;
    private char fr;
    private char cpn;
    private char ccr;
    private char dc;
    private char prep;
    private char ce;
    private char prescricaoespecial;
    private String motivocriacaoespecial;

    public void setId(Integer id) {
        this.id = id;
    }

    public int getClinicalStage() {
        return clinicalStage;
    }

    public void setClinicalStage(int clinicalStage) {
        this.clinicalStage = clinicalStage;
    }

    public char getCurrent() {
        return current;
    }

    public void setCurrent(char current) {
        this.current = current;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDatainicionoutroservico() {
        return datainicionoutroservico;
    }

    public void setDatainicionoutroservico(Date datainicionoutroservico) {
        this.datainicionoutroservico = datainicionoutroservico;
    }

    public DoctorMigrationRecord getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorMigrationRecord doctor) {
        this.doctor = doctor;
    }

    public RegimeTerapeuticoMigrationRecord getRegimeTerapeutico() {
        return regimeTerapeutico;
    }

    public void setRegimeTerapeutico(RegimeTerapeuticoMigrationRecord regimeTerapeutico) {
        this.regimeTerapeutico = regimeTerapeutico;
    }

    public String getMotivoMudanca() {
        return motivoMudanca;
    }

    public void setMotivoMudanca(String motivoMudanca) {
        this.motivoMudanca = motivoMudanca;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public char getModified() {
        return modified;
    }

    public void setModified(char modified) {
        this.modified = modified;
    }

    public PatientMigrationRecord getPatient() {
        return patient;
    }

    public void setPatient(PatientMigrationRecord patient) {
        this.patient = patient;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getReasonForUpdate() {
        return reasonForUpdate;
    }

    public void setReasonForUpdate(String reasonForUpdate) {
        this.reasonForUpdate = reasonForUpdate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDrugTypes() {
        return drugTypes;
    }

    public void setDrugTypes(String drugTypes) {
        this.drugTypes = drugTypes;
    }

    public int getDispensaTrimestral() {
        return dispensaTrimestral;
    }

    public void setDispensaTrimestral(int dispensaTrimestral) {
        this.dispensaTrimestral = dispensaTrimestral;
    }

    public int getDispensaSemestral() {
        return dispensaSemestral;
    }

    public void setDispensaSemestral(int dispensaSemestral) {
        this.dispensaSemestral = dispensaSemestral;
    }

    public String getTipoDT() {
        return tipoDT;
    }

    public void setTipoDT(String tipoDT) {
        this.tipoDT = tipoDT;
    }

    public String getTipoDS() {
        return tipoDS;
    }

    public void setTipoDS(String tipoDS) {
        this.tipoDS = tipoDS;
    }

    public String getDurationSentence() {
        return durationSentence;
    }

    public void setDurationSentence(String durationSentence) {
        this.durationSentence = durationSentence;
    }

    public String getTipoDoenca() {
        return tipoDoenca;
    }

    public void setTipoDoenca(String tipoDoenca) {
        this.tipoDoenca = tipoDoenca;
    }

    public LinhaTerapeuticaMigrationRecord getLinha() {
        return linha;
    }

    public void setLinha(LinhaTerapeuticaMigrationRecord linha) {
        this.linha = linha;
    }

    public char getPpe() {
        return ppe;
    }

    public void setPpe(char ppe) {
        this.ppe = ppe;
    }

    public char getPtv() {
        return ptv;
    }

    public void setPtv(char ptv) {
        this.ptv = ptv;
    }

    public char getTb() {
        return tb;
    }

    public void setTb(char tb) {
        this.tb = tb;
    }

    public char getTpc() {
        return tpc;
    }

    public void setTpc(char tpc) {
        this.tpc = tpc;
    }

    public char getTpi() {
        return tpi;
    }

    public void setTpi(char tpi) {
        this.tpi = tpi;
    }

    public char getSaaj() {
        return saaj;
    }

    public void setSaaj(char saaj) {
        this.saaj = saaj;
    }

    public char getGaac() {
        return gaac;
    }

    public void setGaac(char gaac) {
        this.gaac = gaac;
    }

    public char getAf() {
        return af;
    }

    public void setAf(char af) {
        this.af = af;
    }

    public char getCa() {
        return ca;
    }

    public void setCa(char ca) {
        this.ca = ca;
    }

    public char getFr() {
        return fr;
    }

    public void setFr(char fr) {
        this.fr = fr;
    }

    public char getCpn() {
        return cpn;
    }

    public void setCpn(char cpn) {
        this.cpn = cpn;
    }

    public char getCcr() {
        return ccr;
    }

    public void setCcr(char ccr) {
        this.ccr = ccr;
    }

    public char getDc() {
        return dc;
    }

    public void setDc(char dc) {
        this.dc = dc;
    }

    public char getPrep() {
        return prep;
    }

    public void setPrep(char prep) {
        this.prep = prep;
    }

    public char getCe() {
        return ce;
    }

    public void setCe(char ce) {
        this.ce = ce;
    }

    public char getPrescricaoespecial() {
        return prescricaoespecial;
    }

    public void setPrescricaoespecial(char prescricaoespecial) {
        this.prescricaoespecial = prescricaoespecial;
    }

    public String getMotivocriacaoespecial() {
        return motivocriacaoespecial;
    }

    public void setMotivocriacaoespecial(String motivocriacaoespecial) {
        this.motivocriacaoespecial = motivocriacaoespecial;
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
