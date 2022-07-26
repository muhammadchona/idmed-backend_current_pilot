package mz.org.fgh.sifmoz.migration.entity.patient;

import mz.org.fgh.sifmoz.migration.base.log.AbstractMigrationLog;
import mz.org.fgh.sifmoz.migration.base.record.AbstractMigrationRecord;
import mz.org.fgh.sifmoz.migration.base.record.MigrationRecord;

import java.util.List;

public class ClinicMigrationRecord extends AbstractMigrationRecord {

    private Integer id;

    private boolean mainClinic;

    // Such as for CIPRA, Primary Investigator R.Wood
    private String notes;

    private String code;

    private String telephone;

    private NationalClinicsMigrationRecord clinicDetails;
    private String clinicName;

    private String province;

    private String district;

    private String subDistrict;

    private String uuid;

    private String facilityType;

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isMainClinic() {
        return mainClinic;
    }

    public void setMainClinic(boolean mainClinic) {
        this.mainClinic = mainClinic;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public NationalClinicsMigrationRecord getClinicDetails() {
        return clinicDetails;
    }

    public void setClinicDetails(NationalClinicsMigrationRecord clinicDetails) {
        this.clinicDetails = clinicDetails;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSubDistrict() {
        return subDistrict;
    }

    public void setSubDistrict(String subDistrict) {
        this.subDistrict = subDistrict;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
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
}
