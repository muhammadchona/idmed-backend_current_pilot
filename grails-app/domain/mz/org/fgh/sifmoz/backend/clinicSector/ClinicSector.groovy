package mz.org.fgh.sifmoz.backend.clinicSector

import com.fasterxml.jackson.annotation.JsonIgnore
import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.clinicSectorType.ClinicSectorType
import mz.org.fgh.sifmoz.backend.protection.Menu
import mz.org.fgh.sifmoz.backend.protection.SecUser
import mz.org.fgh.sifmoz.backend.service.ClinicalService

class ClinicSector extends BaseEntity {
    String id
    String code
    String description
    String uuid = UUID.randomUUID().toString()
    boolean active
    ClinicSectorType clinicSectorType

    @JsonIgnore
    Clinic clinic
//    static belongsTo = [ClinicalService]
    static belongsTo = [ClinicalService, SecUser]

//    static  hasMany = [users: SecUser, clinicalService: ClinicalService]
    static  hasMany = [clinicalService: ClinicalService]
    static mapping = {
        id generator: "assigned"
id column: 'id', index: 'Pk_ClinicSector_Idx'
        clinicalService joinTable: [name:"clinical_service_clinic_sectors", key:"clinic_sector_id", column:"clinical_service_id"]
//        users  joinTable: [name: "clinic_sector_users", key: "clinic_sector_id", column: "sec_user_id"]
    }

    def beforeInsert() {
        if (!id) {
            id = UUID.randomUUID()
        }
    }

    static constraints = {
        code nullable: false, unique: true
        description nullable: false
        uuid unique: true
    }

    @Override
    List<Menu> hasMenus() {
        List<Menu> menus = new ArrayList<>()
        Menu.withTransaction {
            menus = Menu.findAllByCodeInList(Arrays.asList(patientMenuCode,groupsMenuCode,stockMenuCode,dashboardMenuCode,reportsMenuCode,administrationMenuCode,homeMenuCode))
        }
        return menus
    }
}
