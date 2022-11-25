package mz.org.fgh.sifmoz.backend.tansreference;

import mz.org.fgh.sifmoz.backend.healthInformationSystem.HealthInformationSystem;

import java.util.ArrayList;
import java.util.Date;

public class TransReferenceData {

    Long id;
    String firstNames;
    String middleNames;
    String lastNames;
    String gender;
    Date dateOfBirth;
    String cellphone;
    String alternativeCellphone;
    String address;
    String addressReference;
    boolean accountstatus;
    String hisUuid;
    String hisLocation;
    String hisLocationName;
    HealthInformationSystem his;
    String provinceCode;
    String bairroCode;
    String districtCode;
    String postoAdministrativoCode;
    String originClinicUuid;
    String destinationClinicUuid;
    String clinicalServiceCode;
    String patientNid;
    Date startDate;
    Date endDate;
    Date reopenDate;
    String state;
    boolean prefered;
    String identifierTypeCode;
    Date patientVisitDate;
    Date episodeDate;
    String startStopReasonCode;
    String episodeNotes;
    String episodeTypeCode;
    String clinicSectorCode;
    String clinicSectorTypeCode;
    Date prescriptionDate;
    Date expiryDate;
    boolean current;
    String notes;
    String prescriptionSeq;
    String patientType;
    String patientStatus;
    int weeksDuration;
    String reasonForUpdate;
    String therapeuticLineCode;
    String therapeuticRegimenCode;
    String dispenseTypeCode;
    Date dateLeft;
    Date dateReceived;
    boolean modified;
    Date packDate;
    Date pickupDate;
    Date nextPickUpDate;
    int weeksSupply;
    Date dateReturned;
    int stockReturned;
    int packageReturned;
    String reasonForPackageReturn;
    String dispenseModeCode;


    ArrayList<String> jsonPrescribedDrug;
    ArrayList<String> jsonPackagedDrug;
    //Falta prescribedDrug organizar json

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    String getFirstNames() {
        return firstNames;
    }

    public void setFirstNames(String firstNames) {
        this.firstNames = firstNames;
    }

    String getMiddleNames() {
        return middleNames;
    }

    public void setMiddleNames(String middleNames) {
        this.middleNames = middleNames;
    }

    String getLastNames() {
        return lastNames;
    }

    public void setLastNames(String lastNames) {
        this.lastNames = lastNames;
    }

    String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    String getAlternativeCellphone() {
        return alternativeCellphone;
    }

    public void setAlternativeCellphone(String alternativeCellphone) {
        this.alternativeCellphone = alternativeCellphone;
    }

    String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    String getAddressReference() {
        return addressReference;
    }

    public void setAddressReference(String addressReference) {
        this.addressReference = addressReference;
    }

    public boolean isAccountstatus() {
        return accountstatus;
    }

    public void setAccountstatus(boolean accountstatus) {
        this.accountstatus = accountstatus;
    }

    String getHisUuid() {
        return hisUuid;
    }

    public void setHisUuid(String hisUuid) {
        this.hisUuid = hisUuid;
    }

    String getHisLocation() {
        return hisLocation;
    }

    public void setHisLocation(String hisLocation) {
        this.hisLocation = hisLocation;
    }

    String getHisLocationName() {
        return hisLocationName;
    }

    public void setHisLocationName(String hisLocationName) {
        this.hisLocationName = hisLocationName;
    }

    public HealthInformationSystem getHis() {
        return his;
    }

    public void setHis(HealthInformationSystem his) {
        this.his = his;
    }

    String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    String getBairroCode() {
        return bairroCode;
    }

    public void setBairroCode(String bairroCode) {
        this.bairroCode = bairroCode;
    }

    String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    String getPostoAdministrativoCode() {
        return postoAdministrativoCode;
    }

    public void setPostoAdministrativoCode(String postoAdministrativoCode) {
        this.postoAdministrativoCode = postoAdministrativoCode;
    }

    String getOriginClinicUuid() {
        return originClinicUuid;
    }

    public void setOriginClinicUuid(String originClinicUuid) {
        this.originClinicUuid = originClinicUuid;
    }

    String getDestinationClinicUuid() {
        return destinationClinicUuid;
    }

    public void setDestinationClinicUuid(String destinationClinicUuid) {
        this.destinationClinicUuid = destinationClinicUuid;
    }

    String getClinicalServiceCode() {
        return clinicalServiceCode;
    }

    public void setClinicalServiceCode(String clinicalServiceCode) {
        this.clinicalServiceCode = clinicalServiceCode;
    }

    String getPatientNid() {
        return patientNid;
    }

    public void setPatientNid(String patientNid) {
        this.patientNid = patientNid;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getReopenDate() {
        return reopenDate;
    }

    public void setReopenDate(Date reopenDate) {
        this.reopenDate = reopenDate;
    }

    String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isPrefered() {
        return prefered;
    }

    public void setPrefered(boolean prefered) {
        this.prefered = prefered;
    }

    String getIdentifierTypeCode() {
        return identifierTypeCode;
    }

    public void setIdentifierTypeCode(String identifierTypeCode) {
        this.identifierTypeCode = identifierTypeCode;
    }

    public Date getPatientVisitDate() {
        return patientVisitDate;
    }

    public void setPatientVisitDate(Date patientVisitDate) {
        this.patientVisitDate = patientVisitDate;
    }

    public Date getEpisodeDate() {
        return episodeDate;
    }

    public void setEpisodeDate(Date episodeDate) {
        this.episodeDate = episodeDate;
    }

    String getStartStopReasonCode() {
        return startStopReasonCode;
    }

    public void setStartStopReasonCode(String startStopReasonCode) {
        this.startStopReasonCode = startStopReasonCode;
    }

    String getEpisodeNotes() {
        return episodeNotes;
    }

    public void setEpisodeNotes(String episodeNotes) {
        this.episodeNotes = episodeNotes;
    }

    String getEpisodeTypeCode() {
        return episodeTypeCode;
    }

    public void setEpisodeTypeCode(String episodeTypeCode) {
        this.episodeTypeCode = episodeTypeCode;
    }

    String getClinicSectorCode() {
        return clinicSectorCode;
    }

    public void setClinicSectorCode(String clinicSectorCode) {
        this.clinicSectorCode = clinicSectorCode;
    }

    String getClinicSectorTypeCode() {
        return clinicSectorTypeCode;
    }

    public void setClinicSectorTypeCode(String clinicSectorTypeCode) {
        this.clinicSectorTypeCode = clinicSectorTypeCode;
    }

    public Date getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(Date prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    String getPrescriptionSeq() {
        return prescriptionSeq;
    }

    public void setPrescriptionSeq(String prescriptionSeq) {
        this.prescriptionSeq = prescriptionSeq;
    }

    String getPatientType() {
        return patientType;
    }

    public void setPatientType(String patientType) {
        this.patientType = patientType;
    }

    String getPatientStatus() {
        return patientStatus;
    }

    public void setPatientStatus(String patientStatus) {
        this.patientStatus = patientStatus;
    }

    public int getWeeksDuration() {
        return weeksDuration;
    }

    public void setWeeksDuration(int weeksDuration) {
        this.weeksDuration = weeksDuration;
    }

    String getReasonForUpdate() {
        return reasonForUpdate;
    }

    public void setReasonForUpdate(String reasonForUpdate) {
        this.reasonForUpdate = reasonForUpdate;
    }

    String getTherapeuticLineCode() {
        return therapeuticLineCode;
    }

    public void setTherapeuticLineCode(String therapeuticLineCode) {
        this.therapeuticLineCode = therapeuticLineCode;
    }

    String getTherapeuticRegimenCode() {
        return therapeuticRegimenCode;
    }

    public void setTherapeuticRegimenCode(String therapeuticRegimenCode) {
        this.therapeuticRegimenCode = therapeuticRegimenCode;
    }

    String getDispenseTypeCode() {
        return dispenseTypeCode;
    }

    public void setDispenseTypeCode(String dispenseTypeCode) {
        this.dispenseTypeCode = dispenseTypeCode;
    }

    public Date getDateLeft() {
        return dateLeft;
    }

    public void setDateLeft(Date dateLeft) {
        this.dateLeft = dateLeft;
    }

    public Date getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(Date dateReceived) {
        this.dateReceived = dateReceived;
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    public Date getPackDate() {
        return packDate;
    }

    public void setPackDate(Date packDate) {
        this.packDate = packDate;
    }

    public Date getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(Date pickupDate) {
        this.pickupDate = pickupDate;
    }

    public Date getNextPickUpDate() {
        return nextPickUpDate;
    }

    public void setNextPickUpDate(Date nextPickUpDate) {
        this.nextPickUpDate = nextPickUpDate;
    }

    public int getWeeksSupply() {
        return weeksSupply;
    }

    public void setWeeksSupply(int weeksSupply) {
        this.weeksSupply = weeksSupply;
    }

    public Date getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(Date dateReturned) {
        this.dateReturned = dateReturned;
    }

    public int getStockReturned() {
        return stockReturned;
    }

    public void setStockReturned(int stockReturned) {
        this.stockReturned = stockReturned;
    }

    public int getPackageReturned() {
        return packageReturned;
    }

    public void setPackageReturned(int packageReturned) {
        this.packageReturned = packageReturned;
    }

    String getReasonForPackageReturn() {
        return reasonForPackageReturn;
    }

    public void setReasonForPackageReturn(String reasonForPackageReturn) {
        this.reasonForPackageReturn = reasonForPackageReturn;
    }

    String getDispenseModeCode() {
        return dispenseModeCode;
    }

    public void setDispenseModeCode(String dispenseModeCode) {
        this.dispenseModeCode = dispenseModeCode;
    }

    public ArrayList<String> getJsonPrescribedDrug() {
        return jsonPrescribedDrug;
    }

    public void setJsonPrescribedDrug(ArrayList<String> jsonPrescribedDrug) {
        this.jsonPrescribedDrug = jsonPrescribedDrug;
    }

    public ArrayList<String> getJsonPackagedDrug() {
        return jsonPackagedDrug;
    }

    public void setJsonPackagedDrug(ArrayList<String> jsonPackagedDrug) {
        this.jsonPackagedDrug = jsonPackagedDrug;
    }
}
