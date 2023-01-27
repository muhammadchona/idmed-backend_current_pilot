package mz.org.fgh.sifmoz.backend.tansreference

import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.patient.Patient
import mz.org.fgh.sifmoz.backend.patientIdentifier.PatientServiceIdentifier
import mz.org.fgh.sifmoz.backend.protection.Menu
import mz.org.fgh.sifmoz.backend.tansreference.PatientTransReferenceType

class PatientTransReference extends BaseEntity{

    String id
    Clinic origin
    String destination
    PatientServiceIdentifier identifier
    String syncStatus
    PatientTransReferenceType operationType
    Date operationDate
    Date creationDate
    Long matchId

    static belongsTo = [patient: Patient]
    static mapping = {
       id generator: "assigned"
    }

    static constraints = {
        matchId nullable: true
    }

    def beforeInsertId() {
        if (!id) {
            id = UUID.randomUUID()
        }
    }

    def beforeInsert() {
        if (matchId == null) {
         def  patientTransReference =  PatientTransReference.findAll( [sort: ['matchId': 'desc']])
            if (patientTransReference.size() == 0) {
                matchId = 1
            } else {
                matchId = patientTransReference.get(0).matchId++
            }

        }
    }

    @Override
    List<Menu> hasMenus() {
        List<Menu> menus = new ArrayList<>()
        Menu.withTransaction {
            menus = Menu.findAllByCodeInList(Arrays.asList(patientMenuCode))
        }
        return menus
    }
}
