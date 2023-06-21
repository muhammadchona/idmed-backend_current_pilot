package mz.org.fgh.sifmoz.backend.packaging

import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.dispenseMode.DispenseMode
import mz.org.fgh.sifmoz.backend.group.GroupPack
import mz.org.fgh.sifmoz.backend.packagedDrug.PackagedDrug
import mz.org.fgh.sifmoz.backend.patientVisitDetails.PatientVisitDetails
import mz.org.fgh.sifmoz.backend.protection.Menu

class Pack extends BaseEntity {
    String id
    Date dateLeft
    Date dateReceived
    boolean modified
    Date packDate
    Date pickupDate
    Date nextPickUpDate
    int weeksSupply
    Date dateReturned
    int stockReturned
    int packageReturned
    String reasonForPackageReturn
    Clinic clinic
    DispenseMode dispenseMode
    GroupPack groupPack
    char syncStatus
    String providerUuid

    static hasMany = [packagedDrugs: PackagedDrug]
//    static hasMany = [packagedDrugs: PackagedDrug, patientVisitDetails: PatientVisitDetails]
    static mapping = {
        id generator: "assigned"
        syncStatus defaultValue: 'N'
        id column: 'id', index: 'Pk_pack_Idx'
    }

    static constraints = {
        dateLeft(nullable: true)
        dateReceived(nullable: true, blank: true)
        packDate(nullable: true)
        pickupDate(nullable: false)
        weeksSupply(nullable: false)
        dateReturned(nullable: true)
        providerUuid(nullable: true)
        syncStatus(nullable: true)
        dispenseMode(nullable: false)
        groupPack nullable: true
        reasonForPackageReturn(nullable: true,maxSize: 500)
    }

    def beforeInsert() {
        if (!id) {
            id = UUID.randomUUID()
        }
        if (!clinic) {
            clinic = Clinic.findByMainClinic(true)
        }
    }

    @Override
    List<Menu> hasMenus() {
        List<Menu> menus = new ArrayList<>()
        Menu.withTransaction {
            menus = Menu.findAllByCodeInList(Arrays.asList(patientMenuCode,groupsMenuCode,stockMenuCode,dashboardMenuCode))
        }
        return menus
    }
}
