package mz.org.fgh.sifmoz.backend.patientVisit

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.patient.Patient
import mz.org.fgh.sifmoz.backend.patientVisitDetails.PatientVisitDetails
import mz.org.fgh.sifmoz.backend.screening.*

class PatientVisit extends BaseEntity {
    String id
    Date visitDate
    @JsonIgnore
    Clinic clinic

    @JsonIgnore
    Patient patient
    static belongsTo = [Patient]

    @JsonManagedReference
    static hasMany = [patientVisitDetails: PatientVisitDetails, adherenceScreening: AdherenceScreening, vitalSigns: VitalSignsScreening,
                      pregnancyScreening : PregnancyScreening, tbScreening: TBScreening, ramScreening: RAMScreening]


    static mapping = {
        id generator: "assigned"
    }



    static constraints = {
        patientVisitDetails nullable: true
        adherenceScreening nullable: true
        vitalSigns nullable: true
        pregnancyScreening nullable: true
        tbScreening nullable: true
        ramScreening nullable: true
    }
}
