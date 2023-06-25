package mz.org.fgh.sifmoz.backend.service

import com.fasterxml.jackson.annotation.JsonManagedReference
import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.clinicSector.ClinicSector
import mz.org.fgh.sifmoz.backend.episode.Episode
import mz.org.fgh.sifmoz.backend.identifierType.IdentifierType
import mz.org.fgh.sifmoz.backend.protection.Menu
import mz.org.fgh.sifmoz.backend.serviceattribute.ClinicalServiceAttribute
import mz.org.fgh.sifmoz.backend.therapeuticRegimen.TherapeuticRegimen

class ClinicalService extends BaseEntity {

    String id
    String code
    String description
    @JsonManagedReference
    IdentifierType identifierType
    boolean active
    List<TherapeuticRegimen> therapeuticRegimens

    static transients = ['therapeuticRegimens']

    @JsonManagedReference
    static hasMany = [attributes: ClinicalServiceAttribute,
                       clinicSectors: ClinicSector]

//    static fetchMode = [clinicSectors: 'eager', attributes: 'eager'] // Se um service for carregado, vira com seus clinicSectors e seus attributes

    static mapping = {
        id generator: "assigned"
        id column: 'id', index: 'Pk_ClinicalService_Idx'
        clinicSectors joinTable: [name:"clinical_service_clinic_sectors", key:"clinical_service_id", column:"clinic_sector_id"]
        clinicSectors cascade :"all-delete-orphan"
//        attributes cascade :"all-delete-orphan"
    }

    def beforeInsert() {
        if (!id) {
            id = UUID.randomUUID()
        }
    }

    static constraints = {
        code nullable: false, unique: true
        description nullable: false
        attributes nullable: true
        clinicSectors nullable: true
    }

    boolean isPrep() {
        return this.code == "PREP"
    }

    boolean isTarv() {
        return this.code == "TARV"
    }

    @Override
    List<Menu> hasMenus() {
        List<Menu> menus = new ArrayList<>()
        Menu.withTransaction {
            menus = Menu.findAllByCodeInList(Arrays.asList(patientMenuCode,groupsMenuCode,dashboardMenuCode,administrationMenuCode,reportsMenuCode,homeMenuCode))
        }
        return menus
    }
}
