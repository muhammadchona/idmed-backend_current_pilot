package mz.org.fgh.sifmoz.backend.migration.entity.dispense;

import mz.org.fgh.sifmoz.backend.migrationLog.MigrationLog;
import mz.org.fgh.sifmoz.backend.migration.base.record.AbstractMigrationRecord;
import mz.org.fgh.sifmoz.backend.migration.base.record.MigratedRecord;
import mz.org.fgh.sifmoz.backend.migration.entity.patient.ClinicMigrationRecord;
import mz.org.fgh.sifmoz.backend.migration.entity.prescription.PrescriptionMigrationRecord;

import java.util.Date;
import java.util.List;

public class PackageMigrationRecord extends AbstractMigrationRecord {

    private Integer id;

    private Date dateLeft;

    private Date dateReceived;

    private char modified;

    private String packageId;

    private String drugTypes;

    private ClinicMigrationRecord clinic;

    private Date packDate;

    private Date pickupDate;

    private PrescriptionMigrationRecord prescription;

    private int weekssupply;

    private Date dateReturned;

    private boolean stockReturned;

    private boolean packageReturned;

    private String reasonForPackageReturn;

    public void setId(Integer id) {
        this.id = id;
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

    public char getModified() {
        return modified;
    }

    public void setModified(char modified) {
        this.modified = modified;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getDrugTypes() {
        return drugTypes;
    }

    public void setDrugTypes(String drugTypes) {
        this.drugTypes = drugTypes;
    }

    public ClinicMigrationRecord getClinic() {
        return clinic;
    }

    public void setClinic(ClinicMigrationRecord clinic) {
        this.clinic = clinic;
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

    public PrescriptionMigrationRecord getPrescription() {
        return prescription;
    }

    public void setPrescription(PrescriptionMigrationRecord prescription) {
        this.prescription = prescription;
    }

    public int getWeekssupply() {
        return weekssupply;
    }

    public void setWeekssupply(int weekssupply) {
        this.weekssupply = weekssupply;
    }

    public Date getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(Date dateReturned) {
        this.dateReturned = dateReturned;
    }

    public boolean isStockReturned() {
        return stockReturned;
    }

    public void setStockReturned(boolean stockReturned) {
        this.stockReturned = stockReturned;
    }

    public boolean isPackageReturned() {
        return packageReturned;
    }

    public void setPackageReturned(boolean packageReturned) {
        this.packageReturned = packageReturned;
    }

    public String getReasonForPackageReturn() {
        return reasonForPackageReturn;
    }

    public void setReasonForPackageReturn(String reasonForPackageReturn) {
        this.reasonForPackageReturn = reasonForPackageReturn;
    }

    @Override
    public List<MigrationLog> migrate() {
        return null;
    }

    @Override
    public void updateIDMEDInfo() {

    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getEntityName() {
        return null;
    }


    @Override
    public MigratedRecord initMigratedRecord() {
        return null;
    }
}
