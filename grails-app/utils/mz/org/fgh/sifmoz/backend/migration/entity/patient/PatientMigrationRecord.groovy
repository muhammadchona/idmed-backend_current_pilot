package mz.org.fgh.sifmoz.backend.migration.entity.patient;

import groovy.lang.MetaClass;
import mz.org.fgh.sifmoz.backend.clinic.Clinic;
import mz.org.fgh.sifmoz.backend.clinic.ClinicService;
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.Province;
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.ProvinceService
import mz.org.fgh.sifmoz.backend.migration.common.MigrationError;
import mz.org.fgh.sifmoz.backend.migrationLog.MigrationLog;
import mz.org.fgh.sifmoz.backend.patient.Patient;
import mz.org.fgh.sifmoz.backend.patient.PatientService;
import mz.org.fgh.sifmoz.backend.migration.base.record.AbstractMigrationRecord;
import mz.org.fgh.sifmoz.backend.migration.base.record.MigratedRecord
import mz.org.fgh.sifmoz.backend.utilities.Utilities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PatientMigrationRecord extends AbstractMigrationRecord {

    ClinicService clinicService;

    PatientService patientService;

    ProvinceService provinceService;
    int id;
    boolean accountStatus;
    boolean isPatientEmTransito = false
    String address1;
    String address2;
    String address3;
    String cellphone;
    Date dateOfBirth;
    int clinicid;
    String clinicname;
    String clinicuuid;
    String nextOfKinName;
    String nextOfKinPhone;
    String firstNames;
    String homePhone;
    String lastname;
    char modified;
    String patientId;
    String province;
    char sex;
    String workPhone;
    String race;
    String uuidopenmrs;
    String uuidlocationopenmrs;


    @Override
    public List<MigrationLog> migrate() {

        List<MigrationLog> logs = new ArrayList<>();
        getMigratedRecord().setAddress(this.getAddress3() + " " +getAddress1());
        getMigratedRecord().setAddressReference(this.getAddress2())
        getMigratedRecord().setAccountstatus(this.getAccountStatus());
        getMigratedRecord().setFirstNames(this.getFirstNames());
        getMigratedRecord().setId(this.getUuidopenmrs());
        getMigratedRecord().setCellphone(this.getCellphone());
        getMigratedRecord().setDateOfBirth(this.getDateOfBirth());
        setClinicToMigratedRecord(logs);
        setProvinceToMigratedRecord(logs, this.province);
        getMigratedRecord().setMiddleNames("-");
        getMigratedRecord().setLastNames(this.getLastname());
        getMigratedRecord().setGender(this.getSex() == 'M' ? "Masculino" : "Feminino");

        if (Utilities.listHasElements(logs)) return logs

        Patient.withTransaction {
            getMigratedRecord().save(flush: true)
        }
        return null;
    }

    @Override
    public void updateIDMEDInfo() {

    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getEntityName() {
        return "Patient";
    }

    @Override
    public MigratedRecord initMigratedRecord() {
        return new Patient();
    }

    @Override
    public Patient getMigratedRecord() {
        return (Patient) super.getMigratedRecord();
    }

    void setClinicToMigratedRecord(List<MigrationLog> logs) {
        Clinic clinic = null
        Clinic.withTransaction {
            clinic = Clinic.findByUuid(this.clinicuuid)
        }
        if (clinic != null) {
            getMigratedRecord().setClinic(clinic);
        } else {
            logs.add(new MigrationLog(MigrationError.CLINIC_NOT_FOUND.getCode(),String.format(MigrationError.CLINIC_NOT_FOUND.getDescription(), this.clinicuuid),getId(), getEntityName()));
        }
    }

    void setProvinceToMigratedRecord(List<MigrationLog> logs, String provinceName) {
        Province province = null
        Province.withTransaction {
            province = Province.findByDescription(provinceName);
        }
        if (province != null) {
            getMigratedRecord().setProvince(province);
        } else {
            logs.add(new MigrationLog(MigrationError.PROVINCE_NOT_FOUND.getCode(),String.format(MigrationError.PROVINCE_NOT_FOUND.getDescription(), this.province), getId(), getEntityName()));

        }
    }

}
