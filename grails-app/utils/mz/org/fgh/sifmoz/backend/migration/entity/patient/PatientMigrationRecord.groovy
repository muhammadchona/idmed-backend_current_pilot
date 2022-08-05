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


public class PatientMigrationRecord extends AbstractMigrationRecord {

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
    String firstnames;
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
    List<MigrationLog> migrate() {
        List<MigrationLog> logs = new ArrayList<>()
        Patient.withTransaction {
        getMigratedRecord().setAddress(this.getAddress3() + " " +getAddress1())
        getMigratedRecord().setAddressReference(this.getAddress2())
        getMigratedRecord().setAccountstatus(this.getAccountStatus())
        getMigratedRecord().setFirstNames(this.getFirstnames())
        getMigratedRecord().setId(this.getUuidopenmrs())
        getMigratedRecord().setCellphone(this.getCellphone())
        getMigratedRecord().setDateOfBirth(this.getDateOfBirth())
        setClinicToMigratedRecord(logs)
        setProvinceToMigratedRecord(logs, this.province)
        getMigratedRecord().setMiddleNames("-")
        getMigratedRecord().setLastNames(this.getLastname())
        getMigratedRecord().setGender(this.getSex() == 'M' ? "Masculino" : "Feminino")
        getMigratedRecord().setDistrict(getMigratedRecord().getClinic().getDistrict())
        if (Utilities.listHasElements(logs)) return logs
            getMigratedRecord().validate()
            if (!getMigratedRecord().hasErrors()) {
                getMigratedRecord().save(flush: true)
            } else {
                logs.addAll(generateUnknowMigrationLog(this, getMigratedRecord().getErrors().toString()))
                return logs
            }
        }
        return logs
    }

    @Override
    void updateIDMEDInfo() {

    }

    @Override
    int getId() {
        return this.id
    }

    @Override
    String getEntityName() {
        return "Patient"
    }

    @Override
    MigratedRecord initMigratedRecord() {
        return new Patient()
    }

    @Override
    Patient getMigratedRecord() {
        return (Patient) super.getMigratedRecord()
    }

    void setClinicToMigratedRecord(List<MigrationLog> logs) {
        Clinic clinic = null
        Clinic.withTransaction {
            clinic = Clinic.findByMainClinic(true)
        }
        if (clinic != null) {
            getMigratedRecord().setClinic(clinic)
        } else {
            String[] msg = {String.format(MigrationError.CLINIC_NOT_FOUND.getDescription(), this.clinicuuid)}
            logs.add(new MigrationLog(MigrationError.CLINIC_NOT_FOUND.getCode(),msg,getId(), getEntityName()))
        }
    }

    void setProvinceToMigratedRecord(List<MigrationLog> logs, String provinceName) {
        Province province = null
        Province.withTransaction {
            province = Province.findByDescription(provinceName)
        }
        if (province != null) {
            getMigratedRecord().setProvince(province)
        } else {
            String[] msg = {String.format(MigrationError.PROVINCE_NOT_FOUND.getDescription(), this.province)}
            logs.add(new MigrationLog(MigrationError.PROVINCE_NOT_FOUND.getCode(),msg, getId(), getEntityName()))
        }
    }

}
