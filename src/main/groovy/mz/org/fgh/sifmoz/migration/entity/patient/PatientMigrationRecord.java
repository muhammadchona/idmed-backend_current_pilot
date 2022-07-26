package mz.org.fgh.sifmoz.migration.entity.patient;

import mz.org.fgh.sifmoz.backend.patient.Patient;
import mz.org.fgh.sifmoz.migration.base.log.AbstractMigrationLog;
import mz.org.fgh.sifmoz.migration.base.record.AbstractMigrationRecord;
import mz.org.fgh.sifmoz.migration.base.record.MigratedRecord;
import mz.org.fgh.sifmoz.migration.base.record.MigrationRecord;

import java.util.Date;
import java.util.List;


public class PatientMigrationRecord extends AbstractMigrationRecord {

    private Integer id;
    private Boolean accountStatus;
    private Boolean isPatientEmTransito = Boolean.FALSE;
    private String address1;
    private String address2;
    private String address3;
    private String cellphone;
    private Date dateOfBirth;
    private ClinicMigrationRecord clinic;
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

    public ClinicMigrationRecord getClinic() {
        return clinic;
    }

    public void setClinic(ClinicMigrationRecord clinic) {
        this.clinic = clinic;
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
    public List<AbstractMigrationLog> migrate() {
        getMigratedRecord().setFirstNames(this.firstNames);
        getMigratedRecord().setLastNames(this.lastname);
        return null;
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
}
