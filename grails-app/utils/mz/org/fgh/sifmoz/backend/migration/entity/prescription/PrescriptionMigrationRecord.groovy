package mz.org.fgh.sifmoz.backend.migration.entity.prescription

import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.clinicSector.ClinicSector
import mz.org.fgh.sifmoz.backend.convertDateUtils.ConvertDateUtils
import mz.org.fgh.sifmoz.backend.dispenseMode.DispenseMode
import mz.org.fgh.sifmoz.backend.dispenseType.DispenseType
import mz.org.fgh.sifmoz.backend.doctor.Doctor
import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.duration.Duration
import mz.org.fgh.sifmoz.backend.episode.Episode
import mz.org.fgh.sifmoz.backend.episodeType.EpisodeType
import mz.org.fgh.sifmoz.backend.migration.base.record.AbstractMigrationRecord
import mz.org.fgh.sifmoz.backend.migration.base.record.MigratedRecord
import mz.org.fgh.sifmoz.backend.migration.common.MigrationError
import mz.org.fgh.sifmoz.backend.migrationLog.MigrationLog
import mz.org.fgh.sifmoz.backend.packagedDrug.PackagedDrug
import mz.org.fgh.sifmoz.backend.packagedDrug.PackagedDrugStock
import mz.org.fgh.sifmoz.backend.packaging.Pack
import mz.org.fgh.sifmoz.backend.patient.Patient
import mz.org.fgh.sifmoz.backend.patientIdentifier.PatientServiceIdentifier
import mz.org.fgh.sifmoz.backend.patientVisit.PatientVisit
import mz.org.fgh.sifmoz.backend.patientVisitDetails.PatientVisitDetails
import mz.org.fgh.sifmoz.backend.prescription.Prescription
import mz.org.fgh.sifmoz.backend.prescriptionDetail.PrescriptionDetail
import mz.org.fgh.sifmoz.backend.service.ClinicalService
import mz.org.fgh.sifmoz.backend.startStopReason.StartStopReason
import mz.org.fgh.sifmoz.backend.stock.Stock
import mz.org.fgh.sifmoz.backend.therapeuticLine.TherapeuticLine
import mz.org.fgh.sifmoz.backend.therapeuticRegimen.TherapeuticRegimen
import mz.org.fgh.sifmoz.backend.utilities.Utilities
import org.apache.commons.lang3.StringUtils


class PrescriptionMigrationRecord extends AbstractMigrationRecord {

    private Integer id
    private Date prescriptiondate
    private String firstnamedoctor
    private Integer patientid
    private String nid;
    private String lastnamedoctor
    private int durationprescription
    private char modifiedprescription
    private Date enddateprescription
    private int dispensaTrimestral
    private int dispensaSemestral
    private String tipodoenca
    private char tb
    private char saaj
    private char cpn
    private char ccr
    private String clinicuuid
    private String uuidopenmrs
    private Date startdate
    private Date stopdate
    private String startnotes
    private String startreason
    private String stopreason
    private String notesprescription
    private char currentprescrtiption
    private String therapeuticlinecode
    private String therapeuticregimencode
    //package
    private Integer packid
    private String reasonforpackagereturn
    private boolean packagereturnedpack
    private String modifiedpack
    private Date datereceivedpack
    private boolean stockreturnedpack
    private Date dateleftpack
    private Date pickupdatepack
    private Date packdate
    private Integer weekssupply
    private String nextpickupdate
    private String modedispenseuuid
    //PackageDrug
    private String qtyinhand
    private String drugname
    private Date dispensedate
    private Integer stockid
    private Integer episodeid


    @Override
    List<MigrationLog> migrate() {
        List<MigrationLog> logs = new ArrayList<>()
        Prescription.withTransaction {
            PatientServiceIdentifier psi = new PatientServiceIdentifier()
            psi.setStartDate(this.prescriptiondate)
            MigrationLog migrationLog = MigrationLog.findBySourceIdAndSourceEntity(this.patientid, "Patient")
            if (migrationLog != null) {
                psi.setPatient(Patient.findById(migrationLog.getiDMEDId()));
            } else {
                String[] msg = { String.format(MigrationError.PATIENT_NOT_FOUND.getDescription(), this.clinicuuid) }
                logs.add(new MigrationLog(MigrationError.PATIENT_NOT_FOUND.getCode(), msg, getId(), getEntityName()))
            }

            // psi.setPatient(Patient.findById(this.uuidopenmrs))
            ClinicalService clinicalService = ClinicalService.findByCode(this.tipodoenca)
            psi.setIdentifierType(clinicalService.getIdentifierType())
            Clinic clinic = Clinic.findByUuid(this.clinicuuid)
            psi.setClinic(clinic)
            psi.setService(clinicalService)
            List<PatientServiceIdentifier> listPSI = PatientServiceIdentifier.findAllWhere(patient: psi.getPatient())
            psi.setValue(this.nid)
            psi.setState("activo")
            if (!Utilities.listHasElements(listPSI)) {
                psi.setPrefered(true)
                psi.validate()
                if (!psi.hasErrors()) {
                    psi.save(flush: true)
                } else {
                    logs.addAll(generateUnknowMigrationLog(this, psi.getErrors().toString()))
                    return logs
                }
                // A lista de prescricoes vem ordenada por  dataPrescricao e paciente
                // primeira prescricao
                //Cria o episodio
            } else if ("TARV".equalsIgnoreCase(this.tipodoenca)) {
                List<PatientServiceIdentifier> listPSIs = PatientServiceIdentifier.findAllWhere(patient: Patient.findById(psi.getValue()))
                PatientServiceIdentifier.withTransaction {
                    for (PatientServiceIdentifier psiObj in listPSIs) {
                        psiObj.setPrefered(false)
                        psiObj.setValue(psi.getValue())
                        psiObj.setState("activo")
                        psiObj.validate()
                        if (!psiObj.hasErrors()) {
                            psiObj.save(flush: true)
                        } else {
                            logs.addAll(generateUnknowMigrationLog(this, psi.getErrors().toString()))
                            return logs
                        }
                    }
                    psi.setPrefered(true)
                    psi.setValue(this.nid)
                    psi.validate()
                    if (!psi.hasErrors()) {
                        psi.save(flush: true)
                    } else {
                        logs.addAll(generateUnknowMigrationLog(this, psi.getErrors().toString()))
                        return logs
                    }
                }

            }
            Episode episode = new Episode()
            episode.setClinic(clinic)
            episode.setEpisodeDate(this.prescriptiondate)
            String codeEpisodeType = this.stopdate == null ? "INICIO" : "FIM"
            episode.setEpisodeType(EpisodeType.findByCode(codeEpisodeType))
            //StartStopReason startStopReason = StartStopReason.findByReason(this.stopdate == null ? this.startreason : this.stopreason)
            StartStopReason startStopReason = StartStopReason.findByReason(this.stopdate == null ? this.startreason : this.stopreason)
            episode.setStartStopReason(startStopReason)
            episode.setCreationDate(this.prescriptiondate)
            ClinicSector clinicSector = ClinicSector.findByCode(getClinicSectorCode())
            episode.setClinicSector(clinicSector)
            episode.setNotes(this.stopdate == null ? this.startnotes : this.startnotes)
            episode.setReferralClinic(clinic)
            episode.setEpisodeDate(this.stopdate == null ? this.startdate : this.stopdate)
            episode.setPatientServiceIdentifier(psi)

            EpisodeMigrationRecord episodeM = new EpisodeMigrationRecord()
            episodeM.setId(this.episodeid)
            episodeM.setMigratedRecord(episode)

            episode.validate()
            if (!episode.hasErrors()) {
                episode.save(flush: true)
            } else {
                logs.addAll(generateUnknowMigrationLog(this, episode.getErrors().toString()))
                return logs
            }

            Prescription prescription = new Prescription()
            prescription.setClinic(clinic)
            prescription.setDoctor(Doctor.findByFirstnamesAndLastname("Generic", "Provider"))
            prescription.setModified(this.modifiedprescription == 'T' ? true : false)
            prescription.setExpiryDate(this.enddateprescription)
            prescription.setCurrent(this.currentprescrtiption == 'T' ? true : false)
            prescription.setDuration(Duration.findByWeeks(this.durationprescription))
            prescription.setNotes(this.notesprescription)
            prescription.setPrescriptionDate(this.prescriptiondate)
            prescription.setPatientType("")
            prescription.setPatientStatus("")


            PrescriptionDetail prescriptionDetail = new PrescriptionDetail()
            prescriptionDetail.setPrescription(prescription)
            TherapeuticLine therapeuticLine = getLinhaTerapeutica(this.therapeuticlinecode)
            prescriptionDetail.setTherapeuticLine(therapeuticLine)
            TherapeuticRegimen regimen = TherapeuticRegimen.findByCode(StringUtils.trim(therapeuticregimencode))
            prescriptionDetail.setTherapeuticRegimen(regimen)
            prescriptionDetail.setDispenseType(DispenseType.findByCode(getTipoDispensa()))
            prescriptionDetail.setPrescription(prescription)


            PatientVisit patientVisit = new PatientVisit()
            patientVisit.setClinic(clinic)
            patientVisit.setVisitDate(this.prescriptiondate)
            patientVisit.setPatient(psi.getPatient())


            Pack pack = new Pack()
            pack.setReasonForPackageReturn(this.reasonforpackagereturn)
            pack.setPickupDate(this.pickupdatepack)
            pack.setPackageReturned(this.packagereturnedpack ? 1 : 0)
            pack.setModified(this.modifiedpack == "T")
            pack.setDateReceived(this.datereceivedpack)
            pack.setStockReturned(this.stockreturnedpack ? 1 : 0)
            pack.setDateLeft(this.dateleftpack)
            pack.setWeeksSupply(this.weekssupply)
            pack.setPackDate(this.packdate)
            pack.setPickupDate(this.pickupdatepack)

            PackMigrationRecord packM = new PackMigrationRecord()
            packM.setId(this.packid)
            packM.setMigratedRecord(pack)


            PatientVisitDetails patientVisitDetails = new PatientVisitDetails()
            patientVisitDetails.setClinic(clinic)
            patientVisitDetails.setPrescription(prescription)
            patientVisitDetails.setEpisode(episode)
            Set<PatientVisitDetails> patientVisitDetailsSet = new HashSet<>();
            patientVisitDetailsSet.add(patientVisitDetails)
            patientVisitDetails.setPatientVisit(patientVisit)
            patientVisitDetails.setPack(pack)

            pack.setPatientVisitDetails(patientVisitDetailsSet)

            //TODO:rever
            //pack.setPatientVisitDetails(patientVisitDetailsList)
            Date nxtPickDt = ConvertDateUtils.createDate(StringUtils.replace(this.nextpickupdate, " ", "-"), "dd-MMM-yyyy")
            pack.setNextPickUpDate(nxtPickDt)
            pack.setPatientVisitDetails(patientVisitDetailsSet)
            DispenseMode dsmode = DispenseMode.findById(modedispenseuuid)
            pack.setDispenseMode(dsmode == null ? DispenseMode.findByCode("US_FP_HN") : dsmode)
            pack.setClinic(clinic)

            Integer quantity = Integer.parseInt(StringUtils.replace(StringUtils.replace(this.qtyinhand, "(", ""), ")", ""))
            //PackageDrug
            Drug drug = Drug.findByName(drugname)
            PackagedDrug packagedDrug = new PackagedDrug()
            packagedDrug.setQuantitySupplied(quantity)
            packagedDrug.setNextPickUpDate(nxtPickDt)
            packagedDrug.setDrug(drug)
            packagedDrug.setCreationDate(this.dispensedate)
            packagedDrug.setPack(pack)

            //PackageDrugStock
            PackagedDrugStock packagedDrugStock = new PackagedDrugStock()
            packagedDrugStock.setQuantitySupplied(quantity)
            packagedDrugStock.setDrug(drug)
            MigrationLog log = MigrationLog.findBySourceIdAndSourceEntity(stockid, "stock");

            packagedDrugStock.setStock(log != null ? Stock.findById(log.getiDMEDId()) : null)
            packagedDrugStock.setCreationDate(this.dispensedate)
            packagedDrugStock.setPackagedDrug(packagedDrug)
            //marcar migrationstatus prescricao e package

            prescription.validate()
            pack.validate()
            patientVisit.validate()
            packagedDrug.validate()
            packagedDrugStock.validate()
            if (!prescription.hasErrors()) {
                // 1-prescr ,pack,patientvisit
                prescription.save(flush: true)
            } else {
                logs.addAll(generateUnknowMigrationLog(this, prescription.getErrors().toString()))
                return logs
            }

            if (!patientVisit.hasErrors()) {
                // 1-prescr ,pack,patientvisit
                patientVisit.save(flush: true)
            } else {
                logs.addAll(generateUnknowMigrationLog(this, patientVisit.getErrors().toString()))
                return logs
            }
            if (!pack.hasErrors()) {
                // 1-prescr ,pack,patientvisit
                pack.save(flush: true)
            } else {
                logs.addAll(generateUnknowMigrationLog(this, pack.getErrors().toString()))
                return logs
            }
            if (!packagedDrug.hasErrors()) {
                // 1-prescr ,pack,patientvisit

                packagedDrug.save(flush: true)
            } else {
                logs.addAll(generateUnknowMigrationLog(this, packagedDrug.getErrors().toString()))
                return logs
            }
            PackagedDrugStock.withTransaction {
                if (!packagedDrugStock.hasErrors()) {
                    // 1-prescr ,pack,patientvisit
                    packagedDrugStock.save(flush: true)
                    episodeM.setAsMigratedSuccessfully(getRestService())
                    packM.setAsMigratedSuccessfully(getRestService())


                } else {
                    logs.addAll(generateUnknowMigrationLog(this, packagedDrugStock.getErrors().toString()))
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
        return "Prescription"
    }

    @Override
    MigratedRecord initMigratedRecord() {
        return new Prescription()
    }

    @Override
    Prescription getMigratedRecord() {
        return (Prescription) super.getMigratedRecord()
    }


    String getClinicSectorCode() {
        if (this.cpn == 'T') return "CPN"
        if (this.tb == 'T') return "TB"
        if (this.ccr == 'T') return "CCR"
        if (this.saaj == 'T') return "SAAJ"
        return "NORMAL"
    }

    String getTipoDispensa() {
        if (this.dispensaSemestral == 1 && this.dispensaTrimestral == 1) return "DM"
        else if (this.dispensaSemestral == 1) return "DS"
        else if (this.dispensaTrimestral == 1) return "DT"
        return "DA"
    }

    TherapeuticLine getLinhaTerapeutica(String linhaTerap) {

        if (linhaTerap.toString().contains("1")) {
            return TherapeuticLine.findByCode("1")
        } else if (linhaTerap.toString().contains("2")) {
            return TherapeuticLine.findByCode("2")
        } else if (linhaTerap.toString().contains("3")) {
            return TherapeuticLine.findByCode("3")
        } else if (linhaTerap.toString().contains("ALT")) {
            return TherapeuticLine.findByCode("1_ALT")
        }
    }
}