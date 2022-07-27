package mz.org.fgh.sifmoz.migration.entity.patient;

import mz.org.fgh.sifmoz.backend.clinic.Clinic;
import mz.org.fgh.sifmoz.backend.clinic.ClinicService;
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.Province;
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.ProvinceService;
import mz.org.fgh.sifmoz.backend.migrationLog.MigrationLog;
import mz.org.fgh.sifmoz.backend.patient.Patient;
import mz.org.fgh.sifmoz.backend.patient.PatientService;
import mz.org.fgh.sifmoz.migration.base.log.AbstractMigrationLog;
import mz.org.fgh.sifmoz.migration.base.record.AbstractMigrationRecord;
import mz.org.fgh.sifmoz.migration.base.record.MigratedRecord;
import mz.org.fgh.sifmoz.migration.base.record.MigrationRecord;
import mz.org.fgh.sifmoz.migration.common.MigrationError;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PatientMigrationRecord extends AbstractMigrationRecord {

    ClinicService clinicService;

    PatientService patientService;

    ProvinceService provinceService;
    private Integer id;
    private Boolean accountStatus;
    private Boolean isPatientEmTransito = Boolean.FALSE;
    private String address1;
    private String address2;
    private String address3;
    private String cellphone;
    private Date dateOfBirth;
    private int clinicid;
    private String clinicname;
    private String clinicuuid;
    private String nextOfKinName;
    private String nextOfKinPhone;
    private String firstNames;
    private String homePhone;
    private String lastname;
    private char modified;
    private String patientId;
    private String province;
    private char sex;
    private String workPhone;
    private String race;
    private String uuidopenmrs;
    private String uuidlocationopenmrs;

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Boolean accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Boolean getPatientEmTransito() {
        return isPatientEmTransito;
    }

    public void setPatientEmTransito(Boolean patientEmTransito) {
        isPatientEmTransito = patientEmTransito;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getClinicid() {
        return clinicid;
    }

    public void setClinicid(int clinicid) {
        this.clinicid = clinicid;
    }

    public String getClinicname() {
        return clinicname;
    }

    public void setClinicname(String clinicname) {
        this.clinicname = clinicname;
    }

    public String getClinicuuid() {
        return clinicuuid;
    }

    public void setClinicuuid(String clinicuuid) {
        this.clinicuuid = clinicuuid;
    }

    public String getNextOfKinName() {
        return nextOfKinName;
    }

    public void setNextOfKinName(String nextOfKinName) {
        this.nextOfKinName = nextOfKinName;
    }

    public String getNextOfKinPhone() {
        return nextOfKinPhone;
    }

    public void setNextOfKinPhone(String nextOfKinPhone) {
        this.nextOfKinPhone = nextOfKinPhone;
    }

    public String getFirstNames() {
        return firstNames;
    }

    public void setFirstNames(String firstNames) {
        this.firstNames = firstNames;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public char getModified() {
        return modified;
    }

    public void setModified(char modified) {
        this.modified = modified;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getUuidopenmrs() {
        return uuidopenmrs;
    }

    public void setUuidopenmrs(String uuidopenmrs) {
        this.uuidopenmrs = uuidopenmrs;
    }

    public String getUuidlocationopenmrs() {
        return uuidlocationopenmrs;
    }

    public void setUuidlocationopenmrs(String uuidlocationopenmrs) {
        this.uuidlocationopenmrs = uuidlocationopenmrs;
    }

    @Override
    public List<MigrationLog> migrate() {
        List<MigrationLog> logs = new ArrayList<>();
        getMigratedRecord().setAddress(this.getAddress1());
        getMigratedRecord().setAccountstatus(this.getAccountStatus());
        getMigratedRecord().setFirstNames(this.getFirstNames());
        getMigratedRecord().setId(this.getUuidopenmrs());
        getMigratedRecord().setCellphone(this.getCellphone());
        getMigratedRecord().setDateOfBirth(this.getDateOfBirth());
        //setClinicToMigratedRecord(logs, this.clinic.getCode());
        setProvinceToMigratedRecord(logs, this.province);
        getMigratedRecord().setMiddleNames("-");
        getMigratedRecord().setLastNames(this.getLastname());
        getMigratedRecord().setGender(this.getSex() == 'M' ? "Masculino" : "Feminino");
        patientService.save(getMigratedRecord());
        return logs; 
    }

    @Override
    public void setAsMigratedSuccessfully() {

    }

    @Override
    public void setAsRejectedForMigration() {

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
        return "Patient";
    }

    @Override
    public void generateUnknowMigrationLog(MigrationRecord record, String message) {

    }

    @Override
    public MigratedRecord initMigratedRecord() {
        return new Patient();
    }

    @Override
    public Patient getMigratedRecord() {
        return (Patient) super.getMigratedRecord();
    }

    void setClinicToMigratedRecord(List<MigrationLog> logs, String clinicCode) {
        Clinic clinic = clinicService.findClinicByCode(clinicCode);
        if (clinic != null) {
            getMigratedRecord().setClinic(clinic);
        } else {
            logs.add(new MigrationLog(MigrationError.CLINIC_NOT_FOUND.getCode(),String.format(MigrationError.CLINIC_NOT_FOUND.getDescription(), this.clinic.getCode()),getMigratedRecord().getId(), getEntityName()));
        }
    }

    void setProvinceToMigratedRecord(List<MigrationLog> logs, String provinceName) {
        Province province = provinceService.findProvinceByDescription(provinceName);
        if (province != null) {
            getMigratedRecord().setProvince(province);
        } else {
            logs.add(new MigrationLog(MigrationError.PROVINCE_NOT_FOUND.getCode(),String.format(MigrationError.PROVINCE_NOT_FOUND.getDescription(), this.province),getMigratedRecord().getId(), getEntityName()));

        }
    }
}
