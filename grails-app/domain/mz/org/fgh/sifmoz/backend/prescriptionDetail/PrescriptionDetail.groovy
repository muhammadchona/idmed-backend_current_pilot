package mz.org.fgh.sifmoz.backend.prescriptionDetail

import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.dispenseType.DispenseType
import mz.org.fgh.sifmoz.backend.prescription.Prescription
import mz.org.fgh.sifmoz.backend.prescription.SpetialPrescriptionMotive
import mz.org.fgh.sifmoz.backend.protection.Menu
import mz.org.fgh.sifmoz.backend.therapeuticLine.TherapeuticLine
import mz.org.fgh.sifmoz.backend.therapeuticRegimen.TherapeuticRegimen

class PrescriptionDetail extends BaseEntity {
    String id
    String reasonForUpdate
    TherapeuticLine therapeuticLine
    TherapeuticRegimen therapeuticRegimen
    DispenseType dispenseType
    Prescription prescription
    SpetialPrescriptionMotive spetialPrescriptionMotive
    static belongsTo = [Prescription]

    static mapping = {
       id generator: "assigned"
       id column: 'id', index: 'Pk_PrescriptionDetail_Idx'
    }

    static constraints = {
        reasonForUpdate nullable: true
        therapeuticRegimen nullable: true
        therapeuticLine nullable: true
        spetialPrescriptionMotive nullable: true
    }

    def beforeInsert() {
        if (!id) {
            id = UUID.randomUUID()
        }
    }

    @Override
    List<Menu> hasMenus() {
        List<Menu> menus = new ArrayList<>()
        Menu.withTransaction {
            menus = Menu.findAllByCodeInList(Arrays.asList(patientMenuCode,groupsMenuCode,administrationMenuCode))
        }
        return menus
    }
}
