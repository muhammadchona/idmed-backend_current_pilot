package mz.org.fgh.sifmoz.backend.migration.entity.parameter.drug

import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.doctor.Doctor
import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.form.Form
import mz.org.fgh.sifmoz.backend.migration.base.record.AbstractMigrationRecord
import mz.org.fgh.sifmoz.backend.migration.base.record.MigratedRecord
import mz.org.fgh.sifmoz.backend.migrationLog.MigrationLog
import mz.org.fgh.sifmoz.backend.service.ClinicalService
import mz.org.fgh.sifmoz.backend.utilities.Utilities

class DrugMigrationRecord extends AbstractMigrationRecord {

     String form

     String atccode_id

     String dispensinginstructions1

     String dispensinginstructions2

     char modified

     String name

     boolean active

     int packsize

     char sidetreatment

     String tipodoenca

     double defaultamnt

     int defaulttimes

     String defaulttakeperiod

     String stockcode

     String uuidopenmrs

    String migration_status

    @Override
    List<MigrationLog> migrate() {
        List<MigrationLog> logs = new ArrayList<>()

        Drug drugAux = Drug.findByFnmCode(this.atccode_id)

        getMigratedRecord().setId(!drugAux ? UUID.randomUUID().toString() : drugAux.id)
        getMigratedRecord().setPackSize(this.packsize)
        getMigratedRecord().setName(this.name)
        getMigratedRecord().setDefaultTreatment(this.defaultamnt)
        getMigratedRecord().setDefaultTimes(this.defaulttimes)
        getMigratedRecord().setDefaultPeriodTreatment(this.defaulttakeperiod)
        if(this.atccode_id && !this.atccode_id?.trim()?.isEmpty()){
            getMigratedRecord().setFnmCode(this.atccode_id)
        }else {
            getMigratedRecord().setFnmCode(UUID.randomUUID().toString())
        }
        getMigratedRecord().setUuidOpenmrs(this.uuidopenmrs)
        getMigratedRecord().setClinicalService(ClinicalService.findByCode(this.tipodoenca))
        getMigratedRecord().setForm(Form.findByCode(this.form))
        getMigratedRecord().setActive(false)

        if (Utilities.listHasElements(logs)) return logs

        Drug.withTransaction {
            if(!drugAux) {
                getMigratedRecord().validate()
                if (!getMigratedRecord().hasErrors()) {
                    getMigratedRecord().save(flush: true)
                } else {
                    logs.addAll(generateUnknowMigrationLog(this, getMigratedRecord().getErrors().toString()))
                    return logs
                }
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
        return "drug"
    }

    @Override
    Drug initMigratedRecord() {
        return new Drug();
    }

    @Override
    Drug getMigratedRecord() {
        return (Drug) super.getMigratedRecord();
    }
}
