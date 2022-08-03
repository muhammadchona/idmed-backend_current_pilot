package sifmoz.backend


import mz.org.fgh.sifmoz.backend.clinicSectorType.ClinicSectorType
import mz.org.fgh.sifmoz.backend.dispenseMode.DispenseMode
import mz.org.fgh.sifmoz.backend.dispenseType.DispenseType
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.District
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.Province
import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.duration.Duration
import mz.org.fgh.sifmoz.backend.episodeType.EpisodeType
import mz.org.fgh.sifmoz.backend.facilityType.FacilityType
import mz.org.fgh.sifmoz.backend.form.Form
import mz.org.fgh.sifmoz.backend.groupType.GroupType
import mz.org.fgh.sifmoz.backend.healthInformationSystem.HealthInformationSystem
import mz.org.fgh.sifmoz.backend.identifierType.IdentifierType
import mz.org.fgh.sifmoz.backend.interoperabilityAttribute.InteroperabilityAttribute
import mz.org.fgh.sifmoz.backend.interoperabilityType.InteroperabilityType
import mz.org.fgh.sifmoz.backend.migration.stage.MigrationService
import mz.org.fgh.sifmoz.backend.migration.stage.MigrationStage
import mz.org.fgh.sifmoz.backend.prescription.SpetialPrescriptionMotive
import mz.org.fgh.sifmoz.backend.service.ClinicalService
import mz.org.fgh.sifmoz.backend.serviceattributetype.ClinicalServiceAttributeType
import mz.org.fgh.sifmoz.backend.startStopReason.StartStopReason
import mz.org.fgh.sifmoz.backend.therapeuticLine.TherapeuticLine
import mz.org.fgh.sifmoz.backend.therapeuticRegimen.TherapeuticRegimen

class BootStrap {

    def init = { servletContext ->

        MigrationStage.withTransaction {initMigration()}

        FacilityType.withTransaction { initFacilityType() }

        IdentifierType.withTransaction { initIdentifierType() }

        ClinicSectorType.withTransaction { initClinicSectorType() }

        // ClinicalServiceAttribute.withTransaction {initClinical}

        DispenseMode.withTransaction { initDispenseMode() }

        DispenseType.withTransaction { initDispenseType() }

        Form.withTransaction { initForm() }

        Duration.withTransaction { initDuration() }

        StartStopReason.withTransaction { initStartStopReason() }

        GroupType.withTransaction { initGroupType() }

        TherapeuticLine.withTransaction { initTherapeuticLine() }

        EpisodeType.withTransaction { initEpisodeType() }

        SpetialPrescriptionMotive.withTransaction { initSpetialPrescriptionMotive() }

        ClinicalService.withTransaction { initClinicalService() }

        HealthInformationSystem.withTransaction { initHealthInformationSystem() }

        InteroperabilityType.withTransaction { initInteroperabilityType() }

        InteroperabilityAttribute.withTransaction { initInteroperabilityAttribute() }

        Province.withTransaction { initProvince() }

        District.withTransaction { initDistrict() }

        TherapeuticRegimen.withTransaction { initTherapeuticRegimen() }

        Drug.withTransaction {
            initDrug()
            initRegimenDrugAssossiation()
        }

    }

    def destroy = {
    }

    // Methods
    void initMigration() {
        MigrationStage stage = MigrationStage.findByValue(MigrationStage.STAGE_IN_PROGRESS)
        if (stage != null) {
            MigrationService migrationService = new MigrationService()
            migrationService.execute()
        }
    }

     void initFacilityType() {
        for (facilityTypeObject in listFacilityType()) {
            if (!FacilityType.findById(facilityTypeObject.id)) {
                FacilityType facilityType = new FacilityType()
                facilityType.id = facilityTypeObject.id
                facilityType.code = facilityTypeObject.code
                facilityType.description = facilityTypeObject.description
                facilityType.save(flush: true, failOnError: true)
            }
        }
    }

    void initIdentifierType() {
        for (identifierTypeObject in listIdentifierType()) {
            if (!IdentifierType.findById(identifierTypeObject.id)) {
                IdentifierType identifierType = new IdentifierType()
                identifierType.id = identifierTypeObject.id
                identifierType.code = identifierTypeObject.code
                identifierType.description = identifierTypeObject.description
                identifierType.save(flush: true, failOnError: true)
            }
        }
    }

    void initClinicSectorType() {
        for (clinicSectorTypeObject in listClinicSectorType()) {
            if (!ClinicSectorType.findById(clinicSectorTypeObject.id)) {
                ClinicSectorType clinicSectorType = new ClinicSectorType()
                clinicSectorType.id = clinicSectorTypeObject.id
                clinicSectorType.code = clinicSectorTypeObject.code
                clinicSectorType.description = clinicSectorTypeObject.description
                clinicSectorType.save(flush: true, failOnError: true)
            }
        }
    }

    void initClinicalServiceAttributeType() {
        for (clinicalServiceAttributeTypeObject in listClinicalServiceAttributeType()) {
            if (!ClinicalServiceAttributeType.findById(clinicalServiceAttributeTypeObject.id)) {
                ClinicalServiceAttributeType clinicalServiceAttributeType = new ClinicalServiceAttributeType()
                clinicalServiceAttributeType.id = clinicalServiceAttributeTypeObject.id
                clinicalServiceAttributeType.code = clinicalServiceAttributeTypeObject.code
                clinicalServiceAttributeType.description = clinicalServiceAttributeTypeObject.description
                clinicalServiceAttributeType.save(flush: true, failOnError: true)
            }
        }
    }

    void initDispenseMode() {
        for (dispenseModeObject in listDispenseMode()) {
            if (!DispenseMode.findById(dispenseModeObject.id)) {
                DispenseMode dispenseMode = new DispenseMode()
                dispenseMode.id = dispenseModeObject.id
                dispenseMode.code = dispenseModeObject.code
                dispenseMode.description = dispenseModeObject.description
                dispenseMode.openmrsUuid = dispenseModeObject.openmrs_uuid
                dispenseMode.save(flush: true, failOnError: true)
            }
        }
    }

    void initDispenseType() {
        for (dispenseTypeObject in listDispenseType()) {
            if (!DispenseType.findById(dispenseTypeObject.id)) {
                DispenseType dispenseType = new DispenseType()
                dispenseType.id = dispenseTypeObject.id
                dispenseType.code = dispenseTypeObject.code
                dispenseType.description = dispenseTypeObject.description
                dispenseType.save(flush: true, failOnError: true)
            }
        }
    }

    void initForm() {
        for (formObject in listForm()) {
            if (!Form.findById(formObject.id)) {
                Form form = new Form()
                form.id = formObject.id
                form.code = formObject.code
                form.description = formObject.description
                form.save(flush: true, failOnError: true)
            }
        }
    }

    void initDuration() {
        for (durationObject in listDuration()) {
            if (!Duration.findById(durationObject.id)) {
                Duration duration = new Duration()
                duration.id = durationObject.id
                duration.weeks = durationObject.weeks
                duration.description = durationObject.description
                duration.save(flush: true, failOnError: true)
            }
        }
    }

    void initStartStopReason() {
        for (startStopReasonObject in listStartStopReason()) {
            if (!StartStopReason.findById(startStopReasonObject.id)) {
                StartStopReason startStopReason = new StartStopReason()
                startStopReason.id = startStopReasonObject.id
                startStopReason.isStartReason = startStopReasonObject.isStartReason
                startStopReason.code = startStopReasonObject.code
                startStopReason.reason = startStopReasonObject.reason
                startStopReason.save(flush: true, failOnError: true)
            }
        }
    }

    void initGroupType() {
        for (groupTypeObject in listGroupType()) {
            if (!GroupType.findById(groupTypeObject.id)) {
                GroupType groupType = new GroupType()
                groupType.id = groupTypeObject.id
                groupType.code = groupTypeObject.code
                groupType.description = groupTypeObject.description
                groupType.save(flush: true, failOnError: true)
            }
        }
    }

    void initTherapeuticLine() {
        for (therapeuticLineObject in listTherapeuticLine()) {
            if (!TherapeuticLine.findById(therapeuticLineObject.id)) {
                TherapeuticLine therapeuticLine = new TherapeuticLine()
                therapeuticLine.id = therapeuticLineObject.id
                therapeuticLine.code = therapeuticLineObject.code
                therapeuticLine.description = therapeuticLineObject.description
                therapeuticLine.uuid = therapeuticLineObject.uuid
                therapeuticLine.save(flush: true, failOnError: true)
            }
        }
    }

    void initEpisodeType() {
        for (episodeTypeObject in listEpisodeType()) {
            if (!EpisodeType.findById(episodeTypeObject.id)) {
                EpisodeType episodeType = new EpisodeType()
                episodeType.id = episodeTypeObject.id
                episodeType.code = episodeTypeObject.code
                episodeType.description = episodeTypeObject.description
                episodeType.save(flush: true, failOnError: true)
            }
        }
    }

    void initClinicalService() {
        for (clinicalServiceObject in listClinicalService()) {
            if (!ClinicalService.findById(clinicalServiceObject.id)) {
                ClinicalService clinicalService = new ClinicalService()
                clinicalService.id = clinicalServiceObject.id
                clinicalService.code = clinicalServiceObject.code
                clinicalService.description = clinicalServiceObject.description
                clinicalService.identifierType = clinicalServiceObject.identifierType
                clinicalService.save(flush: true, failOnError: true)
            }
        }
    }

    void initSpetialPrescriptionMotive() {
        for (spetialPrescriptionMotiveObject in listSpetialPrescriptionMotive()) {
            if (!SpetialPrescriptionMotive.findById(spetialPrescriptionMotiveObject.id)) {
                SpetialPrescriptionMotive spetialPrescriptionMotive = new SpetialPrescriptionMotive()
                spetialPrescriptionMotive.id = spetialPrescriptionMotiveObject.id
                spetialPrescriptionMotive.code = spetialPrescriptionMotiveObject.code
                spetialPrescriptionMotive.description = spetialPrescriptionMotiveObject.description
                spetialPrescriptionMotive.save(flush: true, failOnError: true)
            }
        }
    }

    void initHealthInformationSystem() {
        for (healthInformationSystemObject in listHealthInformationSystem()) {
            if (!HealthInformationSystem.findById(healthInformationSystemObject.id)) {
                HealthInformationSystem healthInformationSystem = new HealthInformationSystem()
                healthInformationSystem.id = healthInformationSystemObject.id
                healthInformationSystem.abbreviation = healthInformationSystemObject.abbreviation
                healthInformationSystem.description = healthInformationSystemObject.description
                healthInformationSystem.active = healthInformationSystemObject.active
                healthInformationSystem.save(flush: true, failOnError: true)
            }
        }
    }

    void initInteroperabilityType() {
        for (interoperabilityTypeObject in listInteroperabilityType()) {
            if (!InteroperabilityType.findById(interoperabilityTypeObject.id)) {
                InteroperabilityType interoperabilityType = new InteroperabilityType()
                interoperabilityType.id = interoperabilityTypeObject.id
                interoperabilityType.code = interoperabilityTypeObject.code
                interoperabilityType.description = interoperabilityTypeObject.description
                interoperabilityType.save(flush: true, failOnError: true)
            }
        }
    }


    void initInteroperabilityAttribute() {
        for (interoperabilityAttributeObject in listInteroperabilityAttribute()) {
            if (!InteroperabilityAttribute.findById(interoperabilityAttributeObject.id)) {
                InteroperabilityAttribute interoperabilityAttribute = new InteroperabilityAttribute()
                interoperabilityAttribute.id = interoperabilityAttributeObject.id
                interoperabilityAttribute.interoperabilityType = interoperabilityAttributeObject.interoperabilityType
                interoperabilityAttribute.value = interoperabilityAttributeObject.value
                interoperabilityAttribute.healthInformationSystem = interoperabilityAttributeObject.healthInformationSystem
                interoperabilityAttribute.save(flush: true, failOnError: true)
            }
        }
    }

    void initProvince() {
        for (provinceObject in listProvince()) {
            if (!Province.findById(provinceObject.id)) {
                Province province = new Province()
                province.id = provinceObject.id
                province.code = provinceObject.code
                province.description = provinceObject.description
                province.save(flush: true, failOnError: true)
            }
        }
    }

    void initDistrict() {
        for (districtObject in listDistrict()) {
            if (!District.findById(districtObject.id)) {
                District district = new District()
                district.id = districtObject.id
                district.code = districtObject.code
                district.description = districtObject.description
                district.province = districtObject.province
                district.save(flush: true, failOnError: true)
            }
        }
    }

    void initTherapeuticRegimen() {
        for (therapeuticRegimenObject in listTherapeuticRegimen()) {
            if (!TherapeuticRegimen.findById(therapeuticRegimenObject.id)) {
                TherapeuticRegimen therapeuticRegimen = new TherapeuticRegimen()
                therapeuticRegimen.id = therapeuticRegimenObject.id
                therapeuticRegimen.code = therapeuticRegimenObject.code
                therapeuticRegimen.regimenScheme = therapeuticRegimenObject.regimen_scheme
                therapeuticRegimen.active = therapeuticRegimenObject.active
                therapeuticRegimen.description = therapeuticRegimenObject.description
                therapeuticRegimen.openmrsUuid = therapeuticRegimenObject.openmrs_uuid
                therapeuticRegimen.clincalService = ClinicalService.findById(therapeuticRegimenObject.clincal_service_id)
                therapeuticRegimen.save(flush: true, failOnError: true)

            }
        }
    }

    void initDrug() {
        for (drugObject in listDrug()) {
            if (!Drug.findById(drugObject.id)) {
                Drug drug = new Drug()
                drug.id = drugObject.id
                drug.packSize = drugObject.pack_size
                drug.name = drugObject.name
                drug.active = drugObject.active
                drug.defaultTreatment = drugObject.default_treatment
                drug.defaultTimes = drugObject.default_times
                drug.defaultPeriodTreatment = drugObject.default_period_treatment
                drug.fnmCode = drugObject.fnm_code
                drug.uuidOpenmrs = drugObject.uuid_openmrs
                drug.active = drugObject.active
                drug.clinicalService = ClinicalService.findById(drugObject.clincal_service_id)
                drug.form = Form.findById(drugObject.form_id)
                drug.save(flush: true, failOnError: true)

            }
        }
    }

    void initRegimenDrugAssossiation() {
        for (drugRegimen in listRegimenDrugs()) {
            Drug drug = Drug.findByFnmCode(drugRegimen.drug_id)
            TherapeuticRegimen therapeuticRegimen = TherapeuticRegimen.findByCode(drugRegimen.regimen_id)

            Set<TherapeuticRegimen> therapeuticRegimenList = new ArrayList<>() as Set<TherapeuticRegimen>
            therapeuticRegimenList.add(therapeuticRegimen)

            if(drug && therapeuticRegimen){
                therapeuticRegimen.addToDrugs(drug)
                therapeuticRegimen.save(flush: true, failOnError: false)
            }
        }
    }

    List<Object> listFacilityType() {
        List<Object> facilityTypeList = new ArrayList<>()
        facilityTypeList.add(new LinkedHashMap(id: '8a8a823b81900fee0181901608890000', code: 'FP', description: 'Farmácia Privada'))
        facilityTypeList.add(new LinkedHashMap(id: '8a8a823b81900fee018190163e0c0001', code: 'FC', description: 'Farmácia Comunitária'))
        facilityTypeList.add(new LinkedHashMap(id: '8a8a823b81900fee0181901674b20002', code: 'US', description: 'Unidade Sanitária'))

        return facilityTypeList
    }


    List<Object> listIdentifierType() {
        List<Object> identifierTypeList = new ArrayList<>()
        identifierTypeList.add(new LinkedHashMap(id: '8BC2D0A9-9AC4-487B-B71F-F8088B1CB532', code: 'NID', description: 'NID', pattern: '########01/####/#####'))
        identifierTypeList.add(new LinkedHashMap(id: '50D0185F-A115-40D9-BF70-75E3F1F6DD91', code: 'NID_CCR', description: 'NID CCR', pattern: '##########/####/#####'))
        identifierTypeList.add(new LinkedHashMap(id: '9A502C09-5F57-4262-A3D5-CA6B62E0D58F', code: 'NID_PREP', description: 'NID PREP', pattern: '##########/####/#####'))

        return identifierTypeList
    }


    List<Object> listClinicSectorType() {
        List<Object> clinicSectorTypeList = new ArrayList<>()
        clinicSectorTypeList.add(new LinkedHashMap(id: '8a8a823b81c7fa9d0181c801ab120000', code: 'PARAGEM_UNICA', description: 'Paragem Única'))
        clinicSectorTypeList.add(new LinkedHashMap(id: '8a8a823b81c7fa9d0181c801fcac0001', code: 'PROVEDOR', description: 'Dispensa Comunitária pelo Provedor'))
        clinicSectorTypeList.add(new LinkedHashMap(id: '8a8a823b81c7fa9d0181c8025ea10002', code: 'APE', description: 'Agente Polivalente Elementar'))
        clinicSectorTypeList.add(new LinkedHashMap(id: '8a8a823b81c7fa9d0181c8029c890003', code: 'CLINICA_MOVEL', description: 'Clinica Móvel'))
        clinicSectorTypeList.add(new LinkedHashMap(id: '8a8a823b81c7fa9d0181c802d7ec0004', code: 'BRIGADA_MOVEL', description: 'Brigada Móvel'))
        clinicSectorTypeList.add(new LinkedHashMap(id: '8a8a823b81c7fa9d0181c802d7ec0006', code: 'NORMAL', description: 'Atendimento Normal'))

        return clinicSectorTypeList
    }

    List<Object> listClinicalServiceAttributeType() {
        List<Object> clinicalServiceAttributeList = new ArrayList<>()
        clinicalServiceAttributeList.add(new LinkedHashMap(id: 'ff8081817c99e33a017c9ef6f59d0000', code: 'THERAPEUTICAL_REGIMEN', description: 'Regime terapeutico'))
        clinicalServiceAttributeList.add(new LinkedHashMap(id: 'ff8081817c99e33a017c9ef853610003', code: 'PRESCRIPTION_CHANGE_MOTIVE', description: 'Motivo de alteracao de prescricao'))
        clinicalServiceAttributeList.add(new LinkedHashMap(id: 'ff8081817c99e33a017c9ef7ab3f0002', code: 'PATIENT_TYPE', description: 'Tipo Paciente'))
        clinicalServiceAttributeList.add(new LinkedHashMap(id: 'ff8081817c99e33a017c9ef760800001', code: 'THERAPEUTICAL_LINE', description: 'Linha terapeutica'))

        return clinicalServiceAttributeList
    }


    List<Object> listDispenseMode() {
        List<Object> dispenseModeList = new ArrayList<>()
        dispenseModeList.add(new LinkedHashMap(id: '091737af-d6bf-4830-8e87-82572ffac9ea', code: 'DC_CM-FHN', openmrs_uuid: '091737af-d6bf-4830-8e87-82572ffac9ea', description: 'Clínicas Móveis - Distribuição durante o final do dia'))
        dispenseModeList.add(new LinkedHashMap(id: '1309d08a-5c73-4429-8f4b-43a551952858', code: 'US_FP_FHN', openmrs_uuid: '1309d08a-5c73-4429-8f4b-43a551952858', description: 'Farmácia Pública - Fora Hora Normal'))
        dispenseModeList.add(new LinkedHashMap(id: '3ab58d0e-f831-4966-97bd-209738f5e4df', code: 'DC_BM_HN', openmrs_uuid: '3ab58d0e-f831-4966-97bd-209738f5e4df', description: 'Brigadas Móveis - Distribuição durante o dia'))
        dispenseModeList.add(new LinkedHashMap(id: '467718bc-1756-4b3f-b1ee-98d01910153a', code: 'DC_CM_HN', openmrs_uuid: '467718bc-1756-4b3f-b1ee-98d01910153a', description: 'Clínicas Móveis - Distribuição durante o dia'))
        dispenseModeList.add(new LinkedHashMap(id: '4b51ace2-f778-4f54-bdaa-be2b350b7499', code: 'US_FP_HN', openmrs_uuid: '4b51ace2-f778-4f54-bdaa-be2b350b7499', description: 'Farmácia Pública - Hora Normal'))
        dispenseModeList.add(new LinkedHashMap(id: '870e2d25-c5ef-4e36-89db-0a4a37af214e', code: 'DC_PS', openmrs_uuid: '870e2d25-c5ef-4e36-89db-0a4a37af214e', description: 'Distribuição Comunitária pelo Provedor de Saúde'))
        dispenseModeList.add(new LinkedHashMap(id: 'd6ad74a1-ff67-4b81-afa1-a0d906462623', code: 'DC_BM_FHN', openmrs_uuid: 'd6ad74a1-ff67-4b81-afa1-a0d906462623', description: 'Brigadas Móveis - Distribuição durante o final do dia'))
        dispenseModeList.add(new LinkedHashMap(id: '0843c71b-be47-4de2-ba16-a08db52c1136', code: 'DC_APE', openmrs_uuid: '0843c71b-be47-4de2-ba16-a08db52c1136', description: 'Distribuição Comunitária pelos APEs'))

        return dispenseModeList
    }


    List<Object> listDispenseType() {
        List<Object> dispenseTypeList = new ArrayList<>()
        dispenseTypeList.add(new LinkedHashMap(id: 'ff8081817cbbce66017cbbf78a8c0006', code: 'DM', description: 'Dispensa Mensal'))
        dispenseTypeList.add(new LinkedHashMap(id: 'ff8081817cbbce66017cbbf7ca4e0007', code: 'DT', description: 'Dispensa Trimestral'))
        dispenseTypeList.add(new LinkedHashMap(id: 'ff8081817cbbce66017cbbf8044f0008', code: 'DS', description: 'Dispensa Semestral'))
        dispenseTypeList.add(new LinkedHashMap(id: 'ff8081817cbbce66017cbbf823190009', code: 'DA', description: 'Dispensa Anual'))

        return dispenseTypeList

    }


    List<Object> listForm() {
        List<Object> formList = new ArrayList<>()
        formList.add(new LinkedHashMap(id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', code: 'Comp', description: 'Comprimido'))
        formList.add(new LinkedHashMap(id: 'B61168FC-0178-4DC3-A89E-46A169A7457D', code: 'Xarope', description: 'Xarope'))
        formList.add(new LinkedHashMap(id: '74C8F060-1EA4-45E9-94DB-2DE6775E6481', code: 'Capsula', description: 'Capsula'))
        formList.add(new LinkedHashMap(id: '4EA7EF2B-7F86-4DA7-9B74-AF9B614A8DA6', code: 'Sabonete', description: 'Sabonete'))
        formList.add(new LinkedHashMap(id: '024D260C-F3A3-4149-ACDF-97A68855D85A', code: 'Supositório', description: 'Supositório'))
        formList.add(new LinkedHashMap(id: '451906BE-792D-4506-98DB-408F69B97AB1', code: 'Descongestionante_nasal', description: 'Descongestionante Nasal'))
        formList.add(new LinkedHashMap(id: 'D213A686-782C-4AF3-BC31-BA04EEF0EDB0', code: 'Solução_Oftámilca', description: 'Solução Oftámilca'))
        formList.add(new LinkedHashMap(id: '70E72CB3-F66D-41D7-B2C9-8F545E880FC6', code: 'Gotas_Ouvidos', description: 'Gotas para os ouvidos'))
        formList.add(new LinkedHashMap(id: '742F4BC0-E0CC-4602-829B-BEC4EAFB0D2C', code: 'Suspensão', description: 'Suspensão'))
        formList.add(new LinkedHashMap(id: '17828974-026F-41CF-A906-E97ABFB2A0AC', code: 'Creme', description: 'Creme'))
        formList.add(new LinkedHashMap(id: '700FBB4F-C67C-4085-B05C-33259C29F90E', code: 'Pomada', description: 'Pomada'))
        formList.add(new LinkedHashMap(id: '935436AC-8CB2-4691-8FC6-99209CDB9906', code: 'Gel', description: 'Gel'))
        formList.add(new LinkedHashMap(id: '3B627885-00C6-4F49-97D0-11CECD6CABB4', code: 'Gel_Oral', description: 'Gel Oral'))
        formList.add(new LinkedHashMap(id: 'DB4162F8-A5B3-4656-ABEF-AC977E37A9EF', code: 'Loção', description: 'Loção'))
        formList.add(new LinkedHashMap(id: 'BA8E6254-0F88-43D7-8C39-500756AA7B2F', code: 'Pomada_olhos', description: 'Pomada para os olhos'))
        formList.add(new LinkedHashMap(id: 'A80409ED-89FC-40AF-BA34-5CD0BA886570', code: 'Creme_Vaginal', description: 'Creme Vaginal'))
        //   formList.add(new LinkedHashMap(id: 'EA2A097D-0D71-43C4-B230-8A927181C3A2', code: 'Granulos', description: 'Granulados')

        return formList

    }

    List<Object> listDuration() {
        List<Object> durationList = new ArrayList<>()
        durationList.add(new LinkedHashMap(id: 'ff8081817cbbce66017cbbcecfe30000', description: 'Uma Semana', weeks: 1))
        durationList.add(new LinkedHashMap(id: 'ff8081817cbbce66017cbbcf41280001', description: 'Duas Semanas', weeks: 2))
        durationList.add(new LinkedHashMap(id: 'ff8081817cbbce66017cbbcf9a550002', description: 'Um mês', weeks: 4))
        durationList.add(new LinkedHashMap(id: 'ff8081817cbbce66017cbbd02e620003', description: 'Dois meses', weeks: 8))
        durationList.add(new LinkedHashMap(id: 'ff8081817cbbce66017cbbd079e20004', description: 'Três meses', weeks: 12))
        durationList.add(new LinkedHashMap(id: 'ff8081817cbbce66017cbbd136bf0005', description: 'Seis meses', weeks: 24))

        return durationList
    }


    List<Object> listStartStopReason() {
        List<Object> startStopReasonList = new ArrayList<>()
        startStopReasonList.add(new LinkedHashMap(id: 'ff8081817c9791ee017c99bc4cdd0003', isStartReason: false, reason: 'Termino do tratamento', code: 'TERMINO_DO_TRATAMENTO'))
        startStopReasonList.add(new LinkedHashMap(id: 'ff8081817c9791ee017c99bc5cdd0004', isStartReason: true, reason: 'Transferido DE', code: 'TRANSFERIDO_DE'))
        startStopReasonList.add(new LinkedHashMap(id: 'ff8081817c9791ee017c99bc6cdd0005', isStartReason: true, reason: 'Reinicio ao Tratamento', code: 'REINICIO_TRATAMETO'))
        startStopReasonList.add(new LinkedHashMap(id: 'ff8081817c9791ee017c99bc9cdd0008', isStartReason: false, reason: 'Abandono', code: 'ABANDONO'))
        startStopReasonList.add(new LinkedHashMap(id: 'ff8081817c9791ee017c99bf1cdd0009', isStartReason: false, reason: 'Transferido PARA', code: 'TRANSFERIDO_PARA'))
        startStopReasonList.add(new LinkedHashMap(id: 'ff8081817c9791ee017c99bf1ldc0012', isStartReason: true, reason: 'Inicio na Maternidade', code: 'INICIO_MATERNIDADE'))
        startStopReasonList.add(new LinkedHashMap(id: 'ff8081817c9791ee017c99bc7cdd0006', isStartReason: true, reason: 'Voltou da Referência', code: 'VOLTOU_REFERENCIA'))
        startStopReasonList.add(new LinkedHashMap(id: 'ff8081817c9791ee017c99bf2cdd0010', isStartReason: false, reason: 'Referido para outra Farmácia', code: 'REFERIDO_PARA'))
        startStopReasonList.add(new LinkedHashMap(id: 'ff8081817c9791ee017c99bf3cdd0011', isStartReason: true, reason: 'Trânsito', code: 'TRANSITO'))
        startStopReasonList.add(new LinkedHashMap(id: 'ff8081817c9791ee017cj9bh1ldc0013', isStartReason: false, reason: 'Óbito', code: 'OBITO'))
        startStopReasonList.add(new LinkedHashMap(id: 'ff8081817c9791ee017cj9bh1ldc0014', isStartReason: true, reason: 'Em Manutenção', code: 'MANUNTENCAO'))
        startStopReasonList.add(new LinkedHashMap(id: 'ff8081817c9791ee017c99bbb2aa0002', isStartReason: true, reason: 'Inicio', code: 'INICIO_AO_TRATAMENTO'))

        return startStopReasonList

    }


    List<Object> listGroupType() {
        List<Object> groupTypeList = new ArrayList<>()
        groupTypeList.add(new LinkedHashMap(id: 'c586e280-8f23-11ec-b909-0242ac120002', code: '001', description: 'GAAC'))
        groupTypeList.add(new LinkedHashMap(id: 'c586e50a-8f23-11ec-b909-0242ac120002', code: '002', description: 'Abordagem Familiar'))
        groupTypeList.add(new LinkedHashMap(id: 'c586e8b6-8f23-11ec-b909-0242ac120002', code: '003', description: 'Clube de Adesão '))

        return groupTypeList
    }


    List<Object> listTherapeuticLine() {
        List<Object> therapeuticLineList = new ArrayList<>()
        therapeuticLineList.add(new LinkedHashMap(id: 'ff8081817cb69063017cbbaea6f30009', code: '1', uuid: '7323b36e-fedf-45bc-b866-083854c09f7b', description: 'Primeira Linha'))
        therapeuticLineList.add(new LinkedHashMap(id: 'ff8081817cb69063017cbbagb014av0c', code: '1_ALT', uuid: '6E117555-BB10-43C9-83B4-9171A1734BB7', description: 'Primeira Linha Alternativa'))
        therapeuticLineList.add(new LinkedHashMap(id: 'ff8081817cb69063017cbbaeef36000a', code: '2', uuid: '8112b34d-6695-48b2-975a-7fd7abb06a6e', description: 'Segunda Linha'))
        therapeuticLineList.add(new LinkedHashMap(id: 'ff8081817cb69063017cbbaf1701000b', code: '3', uuid: '843c7cff-f2ba-4134-a015-43370c614de6', description: 'Terceira Linha'))

        return therapeuticLineList

    }

    List<Object> listEpisodeType() {
        List<Object> episodeTypeList = new ArrayList<>()
        episodeTypeList.add(new LinkedHashMap(id: 'ff8081817c7ace37017c7ae8f0050000', code: 'INICIO', description: 'Episódio de Início'))
        episodeTypeList.add(new LinkedHashMap(id: 'ff8081817c7ace37017c7ae917be0001', code: 'FIM', description: 'Episódio de Fim'))

        return episodeTypeList

    }


    List<Object> listSpetialPrescriptionMotive() {
        List<Object> spetialPrescriptionMotiveList = new ArrayList<>()
        spetialPrescriptionMotiveList.add(new LinkedHashMap(id: '8a8a823b81dd5c600181dd5db9820000', code: 'OUTRO', description: 'Outro'))
        spetialPrescriptionMotiveList.add(new LinkedHashMap(id: '8a8a823b8200e213018210d5a2ee0027', code: 'PERDA_MEDICAMENTO', description: 'Perda de Medicamento'))
        spetialPrescriptionMotiveList.add(new LinkedHashMap(id: '8a8a823b8200e213018210d5e4900028', code: 'AUSENCIA_CLINICO', description: 'Ausência do Clínico'))
        spetialPrescriptionMotiveList.add(new LinkedHashMap(id: '8a8a823b8200e213018210d619ba0029', code: 'LABORATORIO', description: 'Laboratório'))
        spetialPrescriptionMotiveList.add(new LinkedHashMap(id: '8a8a823b8200e213018210d6715b002a', code: 'ROTURA_STOCK', description: 'Rotura de Stock'))

        return spetialPrescriptionMotiveList
    }


    List<Object> listClinicalService() {
        List<Object> clinicalServiceList = new ArrayList<>()
        clinicalServiceList.add(new LinkedHashMap(id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F', code: 'TARV', description: 'Tratamento Anti-RetroViral', identifierType: IdentifierType.findById('8BC2D0A9-9AC4-487B-B71F-F8088B1CB532'), active: true))
        clinicalServiceList.add(new LinkedHashMap(id: '6D12193B-7D5D-4665-8FC6-A03855986FBD', code: 'TPT', description: 'Tratamento Preventivo da Tuberculose', identifierType: IdentifierType.findById('50D0185F-A115-40D9-BF70-75E3F1F6DD91'), active: true))
        clinicalServiceList.add(new LinkedHashMap(id: '165C876C-F850-436F-B0BB-80D519056BC3', code: 'PREP', description: 'Tratamento Anti-RetroViral', identifierType: IdentifierType.findById('9A502C09-5F57-4262-A3D5-CA6B62E0D58F'), active: true))

        return clinicalServiceList
    }


    List<Object> listHealthInformationSystem() {
        List<Object> healthInformationSystemList = new ArrayList<>()
        healthInformationSystemList.add(new LinkedHashMap(id: 'ff8080817d9aa854017d9e2809b50008', abbreviation: 'OpenMRS', description: 'OpenMRS', active: true))

        return healthInformationSystemList
    }


    List<Object> listInteroperabilityType() {
        List<Object> interoperabilityTypeList = new ArrayList<>()
        interoperabilityTypeList.add(new LinkedHashMap(id: 'D3B4942C-514B-47A5-BAED-F3A47A466E09', code: 'URL_BASE', description: 'URL BASE'))
        interoperabilityTypeList.add(new LinkedHashMap(id: 'CFCA8326-0AB9-46D0-B5CE-1E277E564823', code: 'URL_BASE_REPORTING_REST', description: 'URL BASE REPORTING REST'))
        interoperabilityTypeList.add(new LinkedHashMap(id: '0718F9D9-39A8-405A-8092-5BDDE4204B25', code: 'OPENMRS_VERSION', description: 'OPENMRS VERSION'))
        interoperabilityTypeList.add(new LinkedHashMap(id: 'CEBEF157-57B9-4442-A1E3-59FA92E818D6', code: 'FORM_FILA_UUID', description: 'FILA FORM UUID'))
        interoperabilityTypeList.add(new LinkedHashMap(id: '73715EBA-8BDD-4C55-8895-C01FB0D5AF57', code: 'FILA_REGIMEN_CONCEPT_UUID', description: 'FILA REGIMEN CONCEPT UUID'))
        interoperabilityTypeList.add(new LinkedHashMap(id: 'EE650283-C62F-43E7-8835-A7B7A9706119', code: 'FILA_VISIT_CONCEPT_UUID', description: 'FILA VISIT CONCEPT UUID'))
        interoperabilityTypeList.add(new LinkedHashMap(id: '416001E8-2F6E-46D4-A448-88E3188160E5', code: 'FILA_DISPENSED_TYPE_CONCEPT_UUID', description: 'FILA DISPENSED TYPE CONCEPT UUID'))
        interoperabilityTypeList.add(new LinkedHashMap(id: 'BD2B4C58-4CEE-49D9-9D70-ED2B3B777064', code: 'FILA_PATIENT_TYPE_UUID', description: 'FILA PATIENT TYPE UUID'))
        interoperabilityTypeList.add(new LinkedHashMap(id: '7C7C893F-D22F-46A7-8CE4-953A9853B6A8', code: 'FILA_NEXT_VISIT_CONCEPT_UUID', description: 'FILA NEXT VISIT CONCEPT UUID'))
        interoperabilityTypeList.add(new LinkedHashMap(id: 'FA1E03AA-EEB0-446E-89F0-BB966C9FC65C', code: 'FORM_FILT_UUID', description: 'FILT FORM UUID'))
        interoperabilityTypeList.add(new LinkedHashMap(id: '44A81300-7B59-4196-8BF6-B15CBFD11667', code: 'FILT_REGIMEN_CONCEPT_UUID', description: 'FILT REGIMEN CONCEPT UUID'))
        interoperabilityTypeList.add(new LinkedHashMap(id: 'BCD2F582-6737-48B6-B56E-FF80EE37DD22', code: 'FILT_DISPENSED_TYPE_CONCEPT_UUID', description: 'FILT DISPENSED TYPE CONCEPT UUID'))
        interoperabilityTypeList.add(new LinkedHashMap(id: 'DE6B0CA7-5481-407C-B542-D0E56EF43342', code: 'FILT_TPT_PATIENT_TYPE_UUID', description: 'FILT TPT PATIENT TYPE UUID'))
        interoperabilityTypeList.add(new LinkedHashMap(id: '8B0D0D86-C6C1-4327-8206-78DC4DFC6EDA', code: 'FILT_VISIT_CONCEPT_UUID', description: 'FILT VISIT CONCEPT UUID'))
        interoperabilityTypeList.add(new LinkedHashMap(id: '22AD8E9F-1A09-4794-A393-4D4C7F4676F8', code: 'FILT_NEXT_VISIT_CONCEPT_UUID', description: 'FILT NEXT VISIT CONCEPT UUID'))
        interoperabilityTypeList.add(new LinkedHashMap(id: 'A7D2F52E-8409-492B-8ECD-A7093E83224E', code: 'DISPENSED_AMOUNT_CONCEPT', description: 'DISPENSED AMOUNT CONCEPT'))
        interoperabilityTypeList.add(new LinkedHashMap(id: '585F5AAC-A014-4F6A-84C3-428C9C68EFD8', code: 'DOSAGE_CONCEPT_UUID', description: 'DOSAGE CONCEPT UUID'))
        interoperabilityTypeList.add(new LinkedHashMap(id: 'DD0FCFE6-94DB-4A8B-9760-C8EEB512044A', code: 'DISPENSE_MODE_CONCEPT_UUID', description: 'DISPENSE MODE CONCEPT UUID'))
        interoperabilityTypeList.add(new LinkedHashMap(id: '3D7530B4-C5DE-4073-8A0D-4137CBA6297D', code: 'MONTHLY_DISPENSED_TYPE_CONCEPT_UUID', description: 'MONTHLY DISPENSED TYPE CONCEPT UUID'))
        interoperabilityTypeList.add(new LinkedHashMap(id: '517C1996-CE00-4159-87F2-3F9DA2D24962', code: 'QUARTERLY_DISPENSED_TYPE_CONCEPT_UUID', description: 'QUARTERLY DISPENSED TYPE CONCEPT UUID'))
        interoperabilityTypeList.add(new LinkedHashMap(id: '4794A14A-EF6E-4AD7-A7FD-D3E291AC2BDD', code: 'SEMESTRAL_DISPENSED_TYPE_CONCEPT_UUID', description: 'SEMESTRAL DISPENSED TYPE CONCEPT UUID'))
        interoperabilityTypeList.add(new LinkedHashMap(id: 'B361B735-26D0-4B58-9BB1-35C317778698', code: 'PATIENT_TYPE_INITIAL_UUID', description: 'PATIENT TYPE INITIAL UUID'))
        interoperabilityTypeList.add(new LinkedHashMap(id: '05C533A6-0BC1-495C-95EF-80C0B3C063A7', code: 'PATIENT_TYPE_CONTINUE_UUID', description: 'PATIENT TYPE CONTINUE UUID'))
        interoperabilityTypeList.add(new LinkedHashMap(id: '30C65546-FACA-489F-809B-60EFCDFB5BF2', code: 'PATIENT_TYPE_RESTART_UUID', description: 'PATIENT TYPE RESTART UUID'))
        interoperabilityTypeList.add(new LinkedHashMap(id: '80B52D48-0946-4267-9535-EC01484914E8', code: 'PATIENT_TYPE_END_UUID', description: 'PATIENT TYPE END UUID'))
        interoperabilityTypeList.add(new LinkedHashMap(id: '329B13FD-82BE-429F-ABD3-A0B72051332C', code: 'FILA_ENCOUNTER_TYPE_CONCEPT_UUID', description: 'FILA ENCOUNTER TYPE CONCEPT UUID'))
        interoperabilityTypeList.add(new LinkedHashMap(id: 'B2AAF0F5-D23B-4DD4-95BA-0ACCC0656A9C', code: 'FILT_ENCOUNTER_TYPE_CONCEPT_UUID', description: 'FILT ENCOUNTER TYPE CONCEPT UUID'))
        interoperabilityTypeList.add(new LinkedHashMap(id: '94094FC8-99F1-4A23-8E2A-1E823E30B6D7', code: 'UNIVERSAL_PROVIDER_UUID', description: 'UNIVERSAL PROVIDER UUID'))

        return interoperabilityTypeList
    }


    List<Object> listInteroperabilityAttribute() {
        List<Object> interoperabilityAttributeList = new ArrayList<>()
        interoperabilityAttributeList.add(new LinkedHashMap(id: 'CA5B9D8A-F0B4-4957-B249-63A75214CD0C', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: 'http://localhost/openmrs/ws/rest/v1/', interoperabilityType: InteroperabilityType.findById('D3B4942C-514B-47A5-BAED-F3A47A466E09')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: '34C5A7BB-E7DE-4CFB-B349-2BC36FD4FFCA', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: 'http://localhost/openmrs/ws/rest/v1/reportingrest/cohort/ba36b483-c17c-454d-9a3a-f060a933c6da', interoperabilityType: InteroperabilityType.findById('CFCA8326-0AB9-46D0-B5CE-1E277E564823')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: 'CE34FBAD-D1F1-4C8E-A823-CBCF3137A0BA', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: '2.x', interoperabilityType: InteroperabilityType.findById('0718F9D9-39A8-405A-8092-5BDDE4204B25')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: '876FA63C-8F5F-42FB-A2D5-24F1BFEDD3ED', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: '49857ace-1a92-4980-8313-1067714df151', interoperabilityType: InteroperabilityType.findById('CEBEF157-57B9-4442-A1E3-59FA92E818D6')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: 'AF6051CD-7AB6-4841-A47D-2D6608095822', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: 'e1d83e4e-1d5f-11e0-b929-000c29ad1d07', interoperabilityType: InteroperabilityType.findById('73715EBA-8BDD-4C55-8895-C01FB0D5AF57')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: 'FFB2BE0D-2191-41A1-88A4-78ACADE251D4', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: 'e1e2efd8-1d5f-11e0-b929-000c29ad1d07', interoperabilityType: InteroperabilityType.findById('EE650283-C62F-43E7-8835-A7B7A9706119')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: '9CC90263-5A21-47D9-AAC0-33FE9FB60C09', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: 'd5c15047-58f3-4eb2-9f98-af82e3531cb5', interoperabilityType: InteroperabilityType.findById('416001E8-2F6E-46D4-A448-88E3188160E5')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: '7439C298-97EE-486D-9711-D9D17BA161F9', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: '93603742-1cae-4970-9077-e2b27e46bd7e', interoperabilityType: InteroperabilityType.findById('BD2B4C58-4CEE-49D9-9D70-ED2B3B777064')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: 'F9AE9B98-CE83-4010-85E5-B9F569555054', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: 'e1e2efd8-1d5f-11e0-b929-000c29ad1d07', interoperabilityType: InteroperabilityType.findById('7C7C893F-D22F-46A7-8CE4-953A9853B6A8')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: 'A542DCF1-99AB-4DC2-AAE2-80279D34B0A9', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: '4ce83895-5c0e-4170-b0cc-d3974b54131f', interoperabilityType: InteroperabilityType.findById('FA1E03AA-EEB0-446E-89F0-BB966C9FC65C')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: '4F3C8B49-20F1-4BBB-83C8-7F217A35D505', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: '9db4ce3b-4c1c-45dd-905f-c984a052f26e', interoperabilityType: InteroperabilityType.findById('44A81300-7B59-4196-8BF6-B15CBFD11667')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: '6D5E1BBE-CC76-439C-A11A-8CD804B044AF', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: 'd5c15047-58f3-4eb2-9f98-af82e3531cb5', interoperabilityType: InteroperabilityType.findById('BCD2F582-6737-48B6-B56E-FF80EE37DD22')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: '531A21E7-2123-4B72-803B-4C6FADE0D0BD', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: '93603742-1cae-4970-9077-e2b27e46bd7e', interoperabilityType: InteroperabilityType.findById('DE6B0CA7-5481-407C-B542-D0E56EF43342')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: 'CAFC31D5-8617-47A0-A286-C63971772337', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: 'd9911494-b231-4b3c-9246-1fe5f269476c', interoperabilityType: InteroperabilityType.findById('8B0D0D86-C6C1-4327-8206-78DC4DFC6EDA')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: 'AB2F5969-E6C9-4F37-94F1-992DF8788DF2', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: 'b7c246bc-f2b6-49e5-9325-911cdca7a8b3', interoperabilityType: InteroperabilityType.findById('22AD8E9F-1A09-4794-A393-4D4C7F4676F8')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: 'A77D1618-1321-44CF-85FA-9BF1D6099C90', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: 'e1de2ca0-1d5f-11e0-b929-000c29ad1d07', interoperabilityType: InteroperabilityType.findById('A7D2F52E-8409-492B-8ECD-A7093E83224E')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: '4E165FE8-7A33-4349-B8A7-10FF204A7DBD', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: 'e1de28ae-1d5f-11e0-b929-000c29ad1d07', interoperabilityType: InteroperabilityType.findById('585F5AAC-A014-4F6A-84C3-428C9C68EFD8')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: '73E51E2B-4DEB-450B-8F0E-E355BC988D34', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: '40a9a12b-1205-4a55-bb93-caf15452bf61', interoperabilityType: InteroperabilityType.findById('DD0FCFE6-94DB-4A8B-9760-C8EEB512044A')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: 'A34A1593-2E3A-4371-8B35-BAAFF3FA058B', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: '1098AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA', interoperabilityType: InteroperabilityType.findById('3D7530B4-C5DE-4073-8A0D-4137CBA6297D')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: '82BDF33C-42FB-4226-B0CF-52D4B793049D', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: 'f53848d5-3521-4cc8-ac72-d63adef281a1', interoperabilityType: InteroperabilityType.findById('517C1996-CE00-4159-87F2-3F9DA2D24962')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: 'A4B2E452-1565-48BD-B866-2EB7DE823BA0', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: '3069be5c-cd02-4ddb-aa1f-bdd71d3dd6be', interoperabilityType: InteroperabilityType.findById('4794A14A-EF6E-4AD7-A7FD-D3E291AC2BDD')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: 'F6EFA41C-4308-4782-835C-8B2A9E2B3B61', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: 'e1d9ef28-1d5f-11e0-b929-000c29ad1d07', interoperabilityType: InteroperabilityType.findById('B361B735-26D0-4B58-9BB1-35C317778698')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: '22E9B370-EA14-4B8D-98BC-1E3ADC84F72E', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: 'e1d9f036-1d5f-11e0-b929-000c29ad1d07', interoperabilityType: InteroperabilityType.findById('05C533A6-0BC1-495C-95EF-80C0B3C063A7')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: '4FE4D425-5514-4CDA-9467-0AD33A5CA9CF', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: 'e1de1bfc-1d5f-11e0-b929-000c29ad1d07', interoperabilityType: InteroperabilityType.findById('30C65546-FACA-489F-809B-60EFCDFB5BF2')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: '4107FCCF-4B40-4B58-BFDD-0B9BF83D25BC', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: 'e1d9facc-1d5f-11e0-b929-000c29ad1d07', interoperabilityType: InteroperabilityType.findById('80B52D48-0946-4267-9535-EC01484914E8')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: '9881585A-79F3-4756-AD05-7968775BF98C', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: 'e279133c-1d5f-11e0-b929-000c29ad1d07', interoperabilityType: InteroperabilityType.findById('329B13FD-82BE-429F-ABD3-A0B72051332C')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: '5143CEA0-EAD7-4A1C-8D0D-DC1697B23AA3', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: '24bd60e2-a1c9-4159-a24f-12af15b77510', interoperabilityType: InteroperabilityType.findById('B2AAF0F5-D23B-4DD4-95BA-0ACCC0656A9C')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: 'C60F7C25-7198-4605-BDD4-FF6DF3309AB2', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: '7013d271-1bc2-4a50-bed6-8932044bc18f', interoperabilityType: InteroperabilityType.findById('94094FC8-99F1-4A23-8E2A-1E823E30B6D7')))

        return interoperabilityAttributeList
    }

    List<Object> listProvince() {
        List<Object> provinceList = new ArrayList<>()

        provinceList.add(new LinkedHashMap(id: '4A4A8DA4-9985-49AC-87DE-DCC076809BDA', code: '11', description: 'Maputo Cidade'))
        provinceList.add(new LinkedHashMap(id: '7D89070B-5F18-4C5D-9C12-639EC471999B', code: '10', description: 'Maputo Província'))
        provinceList.add(new LinkedHashMap(id: 'EB26B875-7649-47DB-B500-594AF91E676E', code: '08', description: 'Inhambane'))
        provinceList.add(new LinkedHashMap(id: '5209FFE9-4D41-41B7-A36E-DBF8E718FD74', code: '09', description: 'Gaza'))
        provinceList.add(new LinkedHashMap(id: '471989A9-C5A4-4289-84B8-34A2738A1AB3', code: '07', description: 'Sofala'))
        provinceList.add(new LinkedHashMap(id: 'E6BC4B22-3E0A-4292-98D9-8F6D590D5A05', code: '06', description: 'Manica'))
        provinceList.add(new LinkedHashMap(id: '3E97A1E4-56CC-4903-8DDC-F0B256E56BC1', code: '05', description: 'Tete'))
        provinceList.add(new LinkedHashMap(id: 'D0F5AFC9-AA22-41C6-A2B1-53E6C4765DDF', code: '04', description: 'Zambézia'))
        provinceList.add(new LinkedHashMap(id: 'B120B693-BA3F-4B40-820E-6D13AAE07BB1', code: '03', description: 'Nampula'))
        provinceList.add(new LinkedHashMap(id: 'A5679E05-6853-410C-A2BF-CDC7B721DA01', code: '02', description: 'Cabo Delgado'))
        provinceList.add(new LinkedHashMap(id: '81808B08-CC4E-42D5-B8D5-F43AFC055FDD', code: '01', description: 'Niassa'))

        return provinceList
    }


    List<Object> listDistrict() {
        List<Object> districtList = new ArrayList<>()
        districtList.add(new LinkedHashMap(id: 'F27A05FB-0D32-43BD-ABAC-7B75ECF47C75', code: '01', description: 'Distrito Urbano de KaMpfumo', province: Province.findByCode('11')))
        districtList.add(new LinkedHashMap(id: 'C4CFDDA6-5330-4FC6-AE47-26E6DD1C5CEA', code: '02', description: 'Distrito Urbano de Nlhamankulu', province: Province.findByCode('11')))
        districtList.add(new LinkedHashMap(id: '9C310822-97F5-49AE-ABFB-D367DC4358C6', code: '03', description: 'Distrito Urbano de KaMaxaquene', province: Province.findByCode('11')))
        districtList.add(new LinkedHashMap(id: '0C4F2D4C-47B3-449B-A977-F2E7F67D6CA6', code: '04', description: 'Distrito Urbano de KaMavota', province: Province.findByCode('11')))
        districtList.add(new LinkedHashMap(id: 'FA1B526D-4DC8-4371-A18E-CF1E087CC018', code: '05', description: 'Distrito Urbano de KaMubukwana', province: Province.findByCode('11')))
        districtList.add(new LinkedHashMap(id: '61AA3652-D63A-4EE2-B3B5-34A54476B65C', code: '06', description: 'Distrito Municipal de KaTembe', province: Province.findByCode('11')))
        districtList.add(new LinkedHashMap(id: 'BACF1638-6E1C-423A-B298-384523CC2268', code: '07', description: 'Distrito Municipal de KaNyaka', province: Province.findByCode('11')))

        districtList.add(new LinkedHashMap(id: '44EFAD60-D00D-4DF5-AE1C-CB9DAB25EB0E', code: '01', description: 'Boane', province: Province.findByCode('10')))
        districtList.add(new LinkedHashMap(id: 'EC988413-8C1A-4192-AA77-9D2A85A62239', code: '02', description: 'Magude', province: Province.findByCode('10')))
        districtList.add(new LinkedHashMap(id: 'B9188B68-5222-4FDC-AA45-93949EBD706E', code: '03', description: 'Manhiça', province: Province.findByCode('10')))
        districtList.add(new LinkedHashMap(id: 'B7A67D5D-7F96-47CF-8BB4-E8EEB66850CF', code: '04', description: 'Marracuene', province: Province.findByCode('10')))
        districtList.add(new LinkedHashMap(id: '48192177-F5D9-43BC-A675-63CDCEF90883', code: '05', description: 'Matola', province: Province.findByCode('10')))
        districtList.add(new LinkedHashMap(id: 'F649B099-5376-4A57-A480-73B75C8A103E', code: '06', description: 'Matutuine', province: Province.findByCode('10')))
        districtList.add(new LinkedHashMap(id: '67E54803-0E43-40DB-869E-92ADBD03299C', code: '07', description: 'Moamba', province: Province.findByCode('10')))
        districtList.add(new LinkedHashMap(id: '9332C8CA-ABEB-4ADC-9C85-EB15D2EED69F', code: '08', description: 'Namaacha', province: Province.findByCode('10')))

        districtList.add(new LinkedHashMap(id: '5E19363B-A942-4781-AEE7-F6A43FC8412F', code: '01', description: 'Funhalouro', province: Province.findByCode('09')))
        districtList.add(new LinkedHashMap(id: '82FFDCCA-D2E3-46F4-AB19-060E51A80951', code: '02', description: 'Govuro', province: Province.findByCode('09')))
        districtList.add(new LinkedHashMap(id: '872A8930-77C5-4650-AC38-EC9779B998FF', code: '03', description: 'Homoíne', province: Province.findByCode('09')))
        districtList.add(new LinkedHashMap(id: '99D77119-8D2C-4C77-95E4-24C1CA7ACC1B', code: '04', description: 'Cidade de Inhambane', province: Province.findByCode('09')))
        districtList.add(new LinkedHashMap(id: 'DEAB4A98-22F3-474F-91A0-7E7FAFACA2EF', code: '05', description: 'Inharrime', province: Province.findByCode('09')))
        districtList.add(new LinkedHashMap(id: 'F03BA714-0F59-4782-B824-B8FEC060937E', code: '06', description: 'Inhassoro', province: Province.findByCode('09')))
        districtList.add(new LinkedHashMap(id: '5ABDCD4E-8909-40EE-93F5-CC1E38308894', code: '07', description: 'Jangamo', province: Province.findByCode('09')))
        districtList.add(new LinkedHashMap(id: '3C02C8AA-1AA9-43E9-AC45-14EBB994C7A1', code: '08', description: 'Mabote', province: Province.findByCode('09')))
        districtList.add(new LinkedHashMap(id: 'F7371071-73FA-43E9-A56A-19AB6150D567', code: '09', description: 'Massinga', province: Province.findByCode('09')))
        districtList.add(new LinkedHashMap(id: '99DD2959-C737-4279-82F2-1A1065D75009', code: '10', description: 'Maxixe', province: Province.findByCode('09')))
        districtList.add(new LinkedHashMap(id: '12825CF6-6BE5-49B4-AC01-9DD0554A729C', code: '11', description: 'Morrumbene', province: Province.findByCode('09')))
        districtList.add(new LinkedHashMap(id: '9EE1E655-3AFB-459B-A424-EAF2490123F6', code: '12', description: 'Panda', province: Province.findByCode('09')))
        districtList.add(new LinkedHashMap(id: 'B7934A06-34ED-49FC-AC14-12BF36E186C8', code: '13', description: 'Vilanculo', province: Province.findByCode('09')))
        districtList.add(new LinkedHashMap(id: '34D9143E-F1CB-4A7E-9840-D8ADD105458F', code: '14', description: 'Zavala', province: Province.findByCode('09')))

        districtList.add(new LinkedHashMap(id: '8AD3230C-4FAA-410C-8457-E2286BA8BC8B', code: '01', description: 'Bilene', province: Province.findByCode('08')))
        districtList.add(new LinkedHashMap(id: '4C3BE6CE-E0FC-4F3F-B22A-273D6234840A', code: '02', description: 'Chibuto', province: Province.findByCode('08')))
        districtList.add(new LinkedHashMap(id: '06D559FA-AF23-4518-BEED-0746A71BF57D', code: '03', description: 'Chicualacuala', province: Province.findByCode('08')))
        districtList.add(new LinkedHashMap(id: '7CAE8FEB-58C7-41D7-9269-7A63FA22DFF8', code: '04', description: 'Chigubo', province: Province.findByCode('08')))
        districtList.add(new LinkedHashMap(id: '2BE867EA-B518-4747-84A7-4A12840B9D58', code: '05', description: 'Chókwè', province: Province.findByCode('08')))
        districtList.add(new LinkedHashMap(id: 'FD58DBCA-2014-444F-8EFC-1B9B82F1CF0C', code: '06', description: 'Chongoene', province: Province.findByCode('08')))
        districtList.add(new LinkedHashMap(id: '9EFF0997-F938-41AA-8C17-6D3FB2256CB7', code: '07', description: 'Guijá', province: Province.findByCode('08')))
        districtList.add(new LinkedHashMap(id: '1AD71364-8871-4A65-937A-50D1FB4C8EF1', code: '08', description: 'Limpopo', province: Province.findByCode('08')))
        districtList.add(new LinkedHashMap(id: 'E8B17360-3630-4DFE-B5F9-5EA538018A81', code: '09', description: 'Mabalane', province: Province.findByCode('08')))
        districtList.add(new LinkedHashMap(id: 'BFBA9E9E-0EC2-4251-AE7E-D07379E22D65', code: '10', description: 'Manjacaze', province: Province.findByCode('08')))
        districtList.add(new LinkedHashMap(id: 'A1C8E515-F988-444B-AD34-369827955964', code: '11', description: 'Mapai', province: Province.findByCode('08')))
        districtList.add(new LinkedHashMap(id: '3BEEFC01-A920-4475-9719-D119783D2AC7', code: '12', description: 'Massangena', province: Province.findByCode('08')))
        districtList.add(new LinkedHashMap(id: '629D983C-B917-40EC-86CA-4227A75FC0DA', code: '13', description: 'Massingir', province: Province.findByCode('08')))
        districtList.add(new LinkedHashMap(id: '67E405F3-3AAB-4145-9F0C-0223177EDAD1', code: '14', description: 'Xai-Xai', province: Province.findByCode('08')))

        districtList.add(new LinkedHashMap(id: 'DA5DDE0F-6A53-471B-95AD-210F266B5059', code: '01', description: 'Beira', province: Province.findByCode('07')))
        districtList.add(new LinkedHashMap(id: '3DAACADD-BA24-41E9-9536-B8F6283C3330', code: '02', description: 'Búzi', province: Province.findByCode('07')))
        districtList.add(new LinkedHashMap(id: 'DAF8EF28-8F0E-4070-B898-A1C54DF309BE', code: '03', description: 'Caia', province: Province.findByCode('07')))
        districtList.add(new LinkedHashMap(id: 'D82F161F-95ED-4E2B-9775-5B796A3CEC8E', code: '04', description: 'Chemba', province: Province.findByCode('07')))
        districtList.add(new LinkedHashMap(id: '3700F059-6BDC-4492-A31B-092771D60B75', code: '05', description: 'Cheringoma', province: Province.findByCode('07')))
        districtList.add(new LinkedHashMap(id: 'D6FBEA62-88D7-4555-ABD6-83CE632C6209', code: '06', description: 'Chibabava', province: Province.findByCode('07')))
        districtList.add(new LinkedHashMap(id: 'A3071861-A577-422D-81DF-7FC4E6C1965C', code: '08', description: 'Dondo', province: Province.findByCode('07')))
        districtList.add(new LinkedHashMap(id: '7429F24A-6ADB-4F5D-8108-D68882CBE834', code: '09', description: 'Gorongosa', province: Province.findByCode('07')))
        districtList.add(new LinkedHashMap(id: '87AFED5C-228E-4AEA-ABB6-C346743C539D', code: '10', description: 'Machanga', province: Province.findByCode('07')))
        districtList.add(new LinkedHashMap(id: 'B783DFA8-D402-4487-A6D2-2F237D85D4CC', code: '11', description: 'Maringué', province: Province.findByCode('07')))
        districtList.add(new LinkedHashMap(id: '5A4FA212-445E-46F3-BFA2-A757D3A9E696', code: '12', description: 'Marromeu', province: Province.findByCode('07')))
        districtList.add(new LinkedHashMap(id: '7E25D960-4E2C-4BA0-93F2-D614BE9812B9', code: '13', description: 'Muanza', province: Province.findByCode('07')))
        districtList.add(new LinkedHashMap(id: '9849837B-410A-4F31-B906-8CF697CE90B5', code: '14', description: 'Nhamatanda', province: Province.findByCode('07')))

        districtList.add(new LinkedHashMap(id: '8A58BA9C-A7FA-4D7E-8358-1D90EE9ED534', code: '01', description: 'Bárue', province: Province.findByCode('06')))
        districtList.add(new LinkedHashMap(id: '6E776FFE-9157-4C79-99DE-659D6C09DCD2', code: '02', description: 'Chimoio', province: Province.findByCode('06')))
        districtList.add(new LinkedHashMap(id: '0F97B760-C6F2-41E5-A53B-D3BBD83378E6', code: '03', description: 'Gondola', province: Province.findByCode('06')))
        districtList.add(new LinkedHashMap(id: '0ADC481B-1CD9-414F-9EDA-995B9C08F75E', code: '04', description: 'Guro', province: Province.findByCode('06')))
        districtList.add(new LinkedHashMap(id: 'F3E6AE45-CC3D-44F0-85F0-C359EBB8DCA5', code: '05', description: 'Macate', province: Province.findByCode('06')))
        districtList.add(new LinkedHashMap(id: '878EDB8B-DB6E-4CD0-8D23-CF1D3AE3D890', code: '06', description: 'Machaze', province: Province.findByCode('06')))
        districtList.add(new LinkedHashMap(id: '1A36A770-C744-4093-8422-DC0DE3220606', code: '07', description: 'Macossa', province: Province.findByCode('06')))
        districtList.add(new LinkedHashMap(id: 'B928B3B3-778C-4291-A998-74752B582E0E', code: '08', description: 'Gorongosa', province: Province.findByCode('06')))
        districtList.add(new LinkedHashMap(id: '71817140-5050-41CA-93DC-B714CFE96834', code: '09', description: 'Manica', province: Province.findByCode('06')))
        districtList.add(new LinkedHashMap(id: '1D567CBB-00B2-48B4-A9E8-282E88ACEF1B', code: '10', description: 'Mossurize', province: Province.findByCode('06')))
        districtList.add(new LinkedHashMap(id: 'D2BD6130-34B8-44F2-86A5-310FBBC03D19', code: '11', description: 'Sussundenga', province: Province.findByCode('06')))
        districtList.add(new LinkedHashMap(id: 'E0097B22-830C-4334-A087-7FD7C100E4F8', code: '12', description: 'Tambara', province: Province.findByCode('06')))
        districtList.add(new LinkedHashMap(id: '27A10EC6-B368-4F67-80D4-03847DCDE3F4', code: '13', description: 'Vanduzi', province: Province.findByCode('06')))

        districtList.add(new LinkedHashMap(id: 'EF759149-553D-47C0-A7B1-FAA2A527C1F0', code: '01', description: 'Angónia', province: Province.findByCode('05')))
        districtList.add(new LinkedHashMap(id: '05E5944A-4896-402F-BA80-EE74EAEF29C7', code: '02', description: 'Cahora-Bassa', province: Province.findByCode('05')))
        districtList.add(new LinkedHashMap(id: '81A60580-3D64-491E-AFD0-C877BC376EC0', code: '03', description: 'Changara', province: Province.findByCode('05')))
        districtList.add(new LinkedHashMap(id: '58994801-885E-4F45-A469-029BA4710AA5', code: '04', description: 'Chifunde', province: Province.findByCode('05')))
        districtList.add(new LinkedHashMap(id: 'A1049E32-2078-4071-9FC6-C3EC6F0F6FE0', code: '05', description: 'Chiuta', province: Province.findByCode('05')))
        districtList.add(new LinkedHashMap(id: 'D5692FB8-E46B-4213-B253-58F8C6E169E7', code: '06', description: 'Dôa', province: Province.findByCode('05')))
        districtList.add(new LinkedHashMap(id: '7970B01A-85DF-43EC-B552-B7ADAB3528C5', code: '08', description: 'Macanga', province: Province.findByCode('05')))
        districtList.add(new LinkedHashMap(id: '7E8AABD1-2520-4454-B300-4BB8C3205D88', code: '09', description: 'Magoé', province: Province.findByCode('05')))
        districtList.add(new LinkedHashMap(id: '3FA9BFBD-956C-4EF2-A30C-87300B5397CD', code: '10', description: 'Marara', province: Province.findByCode('05')))
        districtList.add(new LinkedHashMap(id: '61AA946C-2FEB-4095-AE87-A3998D2B27BD', code: '11', description: 'Marávia', province: Province.findByCode('05')))
        districtList.add(new LinkedHashMap(id: 'A467C575-28E8-46CD-95CC-B92EDA764BB2', code: '12', description: 'Moatize', province: Province.findByCode('05')))
        districtList.add(new LinkedHashMap(id: '6E02D15A-17C3-4FC8-BBB6-8AD84AE3EFC5', code: '13', description: 'Mutarara', province: Province.findByCode('05')))
        districtList.add(new LinkedHashMap(id: 'ED232E24-3ABE-4FA1-993E-7A736929A75C', code: '14', description: 'Tete', province: Province.findByCode('05')))
        districtList.add(new LinkedHashMap(id: 'FA92B99A-7D50-43C5-99D3-2C12FB8A4FBE', code: '15', description: 'Tsangano', province: Province.findByCode('05')))
        districtList.add(new LinkedHashMap(id: 'F4A1EC10-929D-4C53-8264-C239EEC6646A', code: '16', description: 'Zumbo', province: Province.findByCode('05')))

        districtList.add(new LinkedHashMap(id: '263AE10E-6B3C-4683-B7CD-1132ECA6EB18', code: '01', description: 'Alto Molócue', province: Province.findByCode('04')))
        districtList.add(new LinkedHashMap(id: 'F0A7DC31-DA32-4926-9177-1D9FF6AFF780', code: '02', description: 'Chinde', province: Province.findByCode('04')))
        districtList.add(new LinkedHashMap(id: '172B498C-3AFD-4942-AF43-F4CC18AA76E7', code: '03', description: 'Derre', province: Province.findByCode('04')))
        districtList.add(new LinkedHashMap(id: 'B24481C7-1ECA-4A5B-84EA-55F063788504', code: '04', description: 'Gilé', province: Province.findByCode('04')))
        districtList.add(new LinkedHashMap(id: '9CCC9F86-914A-493D-8E8C-CB3126F7FD54', code: '05', description: 'Gurué', province: Province.findByCode('04')))
        districtList.add(new LinkedHashMap(id: '970769BC-B374-4740-BDD2-185723AB4814', code: '06', description: 'Ile', province: Province.findByCode('04')))
        districtList.add(new LinkedHashMap(id: '1236E974-679A-4EB8-823B-48B222EBEFC7', code: '07', description: 'Inhassunge', province: Province.findByCode('04')))
        districtList.add(new LinkedHashMap(id: '0747E091-F4FA-4629-B9D5-30842ED77AED', code: '08', description: 'Luabo', province: Province.findByCode('04')))
        districtList.add(new LinkedHashMap(id: '9F695C84-4B12-4D16-BAD2-E02B5246A38D', code: '09', description: 'Lugela', province: Province.findByCode('04')))
        districtList.add(new LinkedHashMap(id: '3416D3B7-7CEE-4696-AE3C-6B9B67F63167', code: '10', description: 'Maganja da Costa', province: Province.findByCode('04')))
        districtList.add(new LinkedHashMap(id: '2ABB1BF2-57A6-49C4-8E66-5A2ED3B892C5', code: '11', description: 'Milange', province: Province.findByCode('04')))
        districtList.add(new LinkedHashMap(id: '42553F11-F80A-4E3E-BE02-EFFC18C85A7D', code: '12', description: 'Mocuba', province: Province.findByCode('04')))
        districtList.add(new LinkedHashMap(id: '8592A3F3-D406-4C82-8C0C-6F09E8820667', code: '13', description: 'Mocubela', province: Province.findByCode('04')))
        districtList.add(new LinkedHashMap(id: '6B766999-2550-499B-AA53-4ED8574830E2', code: '14', description: 'Molumbo', province: Province.findByCode('04')))
        districtList.add(new LinkedHashMap(id: 'EC4487FD-D851-4BA0-8D0F-85290559F44A', code: '15', description: 'Mopeia', province: Province.findByCode('04')))
        districtList.add(new LinkedHashMap(id: 'FAD587E8-D834-4862-8BB2-29F2B1812083', code: '16', description: 'Morrumbala', province: Province.findByCode('04')))
        districtList.add(new LinkedHashMap(id: '084EAAB6-0200-4C6E-924C-50B1D08B75AF', code: '17', description: 'Mulevala', province: Province.findByCode('04')))
        districtList.add(new LinkedHashMap(id: '882EEDED-3BF2-4CA2-BC3E-6A08666AC23D', code: '18', description: 'Namacurra', province: Province.findByCode('04')))
        districtList.add(new LinkedHashMap(id: '687109F8-EB77-43FC-915A-AB5034376187', code: '19', description: 'Namarroi', province: Province.findByCode('04')))
        districtList.add(new LinkedHashMap(id: '31870979-A39C-4F87-AF1C-285A3F2B72FB', code: '20', description: 'Nicoadala', province: Province.findByCode('04')))
        districtList.add(new LinkedHashMap(id: '53923876-2CDF-4EB1-96AF-A11F794C481B', code: '21', description: 'Pebane', province: Province.findByCode('04')))
        districtList.add(new LinkedHashMap(id: '61986E16-BD21-47A2-A101-B296C7FCAB5E', code: '22', description: 'Quelimane', province: Province.findByCode('04')))

        districtList.add(new LinkedHashMap(id: 'FC203647-3210-4457-9682-F5B6251E47E8', code: '01', description: 'Angoche', province: Province.findByCode('03')))
        districtList.add(new LinkedHashMap(id: 'CBF9625A-231B-439F-B4B1-C39C94AB6FDD', code: '02', description: 'Eráti', province: Province.findByCode('03')))
        districtList.add(new LinkedHashMap(id: '0D9F070C-A368-4EEE-AA11-91A36D6ACEB9', code: '03', description: 'Ilha de Moçambique', province: Province.findByCode('03')))
        districtList.add(new LinkedHashMap(id: '467E0DC2-EAD4-4FF9-A958-95D1BE238758', code: '04', description: 'Lalaua', province: Province.findByCode('03')))
        districtList.add(new LinkedHashMap(id: '299ADC47-F523-4180-B4D8-05F5DDAEC3A7', code: '05', description: 'Larde', province: Province.findByCode('03')))
        districtList.add(new LinkedHashMap(id: '09617379-E7A0-47BB-8B54-7AAC2DB54D6A', code: '06', description: 'Liúpo', province: Province.findByCode('03')))
        districtList.add(new LinkedHashMap(id: '7A0951BF-7FD5-4472-A661-0FC14DF75C0E', code: '07', description: 'Malema', province: Province.findByCode('03')))
        districtList.add(new LinkedHashMap(id: '0A30E69C-D522-41D2-8D8F-5F8868BE28CA', code: '08', description: 'Meconta', province: Province.findByCode('03')))
        districtList.add(new LinkedHashMap(id: '8FFED27F-377C-41AF-8DDB-0F4059C12C2E', code: '09', description: 'Mecubúri', province: Province.findByCode('03')))
        districtList.add(new LinkedHashMap(id: 'D9199556-BAA3-43D2-B155-272F7260C171', code: '10', description: 'Memba', province: Province.findByCode('03')))
        districtList.add(new LinkedHashMap(id: '7187B364-EFAE-44DF-8DB9-97766BC4FF6D', code: '11', description: 'Mogincual', province: Province.findByCode('03')))
        districtList.add(new LinkedHashMap(id: '97323234-C56F-4BC3-BA31-7B1AD89CA10B', code: '12', description: 'Mogovolas', province: Province.findByCode('03')))
        districtList.add(new LinkedHashMap(id: '9D527BEF-10D3-4569-84BF-838FC95D99CA', code: '13', description: 'Moma', province: Province.findByCode('03')))
        districtList.add(new LinkedHashMap(id: '71B6D5F9-FDA2-4F9C-A3BA-5769DF657D48', code: '14', description: 'Monapo', province: Province.findByCode('03')))
        districtList.add(new LinkedHashMap(id: '43FDC475-29E6-4217-925F-E51BD451CE47', code: '15', description: 'Mossuril', province: Province.findByCode('03')))
        districtList.add(new LinkedHashMap(id: 'D8F7B630-E192-4753-ADFB-86CE813F5C27', code: '16', description: 'Muecate', province: Province.findByCode('03')))
        districtList.add(new LinkedHashMap(id: 'D7ACB655-9A27-4852-811A-A1F873C37150', code: '17', description: 'Murrupula', province: Province.findByCode('03')))
        districtList.add(new LinkedHashMap(id: '06AAE98C-6C54-4CC5-8AD8-6E2BC9FEBAC8', code: '18', description: 'Nacala-a-Velha', province: Province.findByCode('03')))
        districtList.add(new LinkedHashMap(id: '1121410A-37D1-47E8-8B27-701E39C9501C', code: '19', description: 'Nacala Porto', province: Province.findByCode('03')))
        districtList.add(new LinkedHashMap(id: '8E08B0DB-301E-4716-AC64-0D1F73CCEA3C', code: '20', description: 'Nacarôa', province: Province.findByCode('03')))
        districtList.add(new LinkedHashMap(id: 'F369DD0F-88DC-4433-8C9E-5038471296C4', code: '21', description: 'Nampula', province: Province.findByCode('03')))
        districtList.add(new LinkedHashMap(id: 'D3CD3D1C-1159-48BC-9430-EC14437404FB', code: '22', description: 'Rapale', province: Province.findByCode('03')))
        districtList.add(new LinkedHashMap(id: '176A9C07-B40A-4073-BE8C-4FFC2708FD0F', code: '23', description: 'Ribaué', province: Province.findByCode('03')))

        districtList.add(new LinkedHashMap(id: 'A49BDD42-20FB-4EB3-90D6-0D787FFDB532', code: '01', description: 'Ancuabe', province: Province.findByCode('02')))
        districtList.add(new LinkedHashMap(id: '34A31F6E-4955-4A2E-83C6-93B8A239745C', code: '02', description: 'Balama', province: Province.findByCode('02')))
        districtList.add(new LinkedHashMap(id: '5D2A1183-7C03-48A6-B736-70D08B697B5E', code: '03', description: 'Chiúre', province: Province.findByCode('02')))
        districtList.add(new LinkedHashMap(id: 'FB6276F8-9366-4330-A580-2A59A7264D22', code: '04', description: 'Ibo', province: Province.findByCode('02')))
        districtList.add(new LinkedHashMap(id: '3F472154-5A88-4F71-B81D-9F1401ECAC6F', code: '05', description: 'Macomia', province: Province.findByCode('02')))
        districtList.add(new LinkedHashMap(id: '41161745-44A2-4196-B756-F7B039578D5A', code: '06', description: 'Mecúfi', province: Province.findByCode('02')))
        districtList.add(new LinkedHashMap(id: 'B81C1F81-BFA8-4374-9A55-91CEAB9E1E30', code: '07', description: 'Meluco', province: Province.findByCode('02')))
        districtList.add(new LinkedHashMap(id: 'D57443AB-E315-4F95-8EE7-69288FC85264', code: '08', description: 'Metuge', province: Province.findByCode('02')))
        districtList.add(new LinkedHashMap(id: '578E2815-F3C8-476D-B38C-084F0F3D5702', code: '09', description: 'Mocímboa da Praia', province: Province.findByCode('02')))
        districtList.add(new LinkedHashMap(id: '09167338-40F0-4B04-BF7A-65C90932FCA9', code: '10', description: 'Montepuez', province: Province.findByCode('02')))
        districtList.add(new LinkedHashMap(id: '807DB077-6944-4C14-9262-DDD5B58DC268', code: '11', description: 'Mueda', province: Province.findByCode('02')))
        districtList.add(new LinkedHashMap(id: '732870EE-CBF7-4FCB-8754-2B52BF7E5A9D', code: '12', description: 'Muidumbe', province: Province.findByCode('02')))
        districtList.add(new LinkedHashMap(id: 'D3CA9983-19E9-4C40-BD2E-98AE5988EF00', code: '13', description: 'Namuno', province: Province.findByCode('02')))
        districtList.add(new LinkedHashMap(id: '01373605-49B5-45A2-A1C7-8C8D410E8EAA', code: '14', description: 'Nangade', province: Province.findByCode('02')))
        districtList.add(new LinkedHashMap(id: '9AC2FC42-C2DF-4899-85F2-275F96AB10D0', code: '15', description: 'Palma', province: Province.findByCode('02')))
        districtList.add(new LinkedHashMap(id: '98D53582-726A-4E3E-863D-33EC6DFECCE0', code: '16', description: 'Pemba', province: Province.findByCode('02')))
        districtList.add(new LinkedHashMap(id: '9BBE7D7E-D15E-441B-972B-42B69D089EFB', code: '17', description: 'Quissanga', province: Province.findByCode('02')))

        districtList.add(new LinkedHashMap(id: 'EB31F967-2129-4EBD-9F53-2DE17DFCBE77', code: '01', description: 'Chimbonila', province: Province.findByCode('01')))
        districtList.add(new LinkedHashMap(id: '03D67DAA-2A0A-4BD6-8FAD-3874D5A8C97D', code: '02', description: 'Cuamba', province: Province.findByCode('01')))
        districtList.add(new LinkedHashMap(id: 'E0B1D8F1-7B66-4E1D-BBBF-1DCA0C87CB19', code: '03', description: 'Lago', province: Province.findByCode('01')))
        districtList.add(new LinkedHashMap(id: '37F31525-C269-4DDC-AB25-AC66087C0260', code: '04', description: 'Lichinga', province: Province.findByCode('01')))
        districtList.add(new LinkedHashMap(id: '31500FB8-5BEF-4233-99A2-942BE425E6AF', code: '05', description: 'Majune', province: Province.findByCode('01')))
        districtList.add(new LinkedHashMap(id: '097D9707-F88D-465B-A523-487E1FA01B72', code: '06', description: 'Mandimba', province: Province.findByCode('01')))
        districtList.add(new LinkedHashMap(id: '7B965708-30DB-4083-A946-7D23974F785E', code: '07', description: 'Marrupa', province: Province.findByCode('01')))
        districtList.add(new LinkedHashMap(id: 'F21A8B83-3095-4E78-8E52-CB20C6C4F4D6', code: '08', description: 'Maúa', province: Province.findByCode('01')))
        districtList.add(new LinkedHashMap(id: 'A5B280AA-9E45-4C38-849A-FE5A5D5E565C', code: '09', description: 'Mavago', province: Province.findByCode('01')))
        districtList.add(new LinkedHashMap(id: '03F5B47E-2817-430D-8371-1B525E4DC885', code: '10', description: 'Mecanhelas', province: Province.findByCode('01')))
        districtList.add(new LinkedHashMap(id: 'FE46BFCB-E641-4385-9880-149A6F055591', code: '11', description: 'Mecula', province: Province.findByCode('01')))
        districtList.add(new LinkedHashMap(id: 'C0AB18B6-3EBC-49D8-95DE-226619187A2D', code: '12', description: 'Metarica', province: Province.findByCode('01')))
        districtList.add(new LinkedHashMap(id: '359C944B-4C9C-4300-B254-6BC7A608BA08', code: '13', description: 'Muembe', province: Province.findByCode('01')))
        districtList.add(new LinkedHashMap(id: '17262FF6-D6A7-4D29-9EA6-3157FF27C972', code: '14', description: 'Ngauma', province: Province.findByCode('01')))
        districtList.add(new LinkedHashMap(id: 'F8FD7B86-4F15-40BD-82F6-F038B0293F0A', code: '15', description: 'Nipepe', province: Province.findByCode('01')))
        districtList.add(new LinkedHashMap(id: '9D45427F-4546-4386-8DC3-5E2ABCC5B426', code: '16', description: 'Sanga', province: Province.findByCode('01')))

        return districtList
    }

    List<Object> listTherapeuticRegimen() {

        List<Object> therapeuticRegimenList = new ArrayList<>()

        therapeuticRegimenList.add(new LinkedHashMap(id: '90118ad6-999e-4f4d-8aee-8bde6e3d4f6d', regimen_scheme: 'TDF+3TC+ATV/r', code: '2alt1', openmrs_uuid: '7bf5a88d-6db6-4899-a01a-bfd14ce77b53', active: false, description: 'TDF+3TC+ATV/r', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'c2b8e086-6f82-4a7e-9615-3c95fea396b1', regimen_scheme: 'AZT+3TC+LPV/r(2DFC+LPV/r80/20)', code: 'A2Fped Xarope', openmrs_uuid: '28b28521-b6cd-454e-9ec5-f2c6c9c58468', active: true, description: 'AZT+3TC+LPV/r(2DFC+LPV/r80/20)', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '8552a6d1-9e75-4c33-84fb-8a35ac0a1f2c', regimen_scheme: 'ABC+3TC+LPV/r(2DFC+LPV/r100/25', code: 'ABCPedCpts', openmrs_uuid: 'cf05347e-063c-4896-91a4-097741cf6be6', active: true, description: 'ABC+3TC+LPV/r(2DFC+LPV/r100/25', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '66359dea-94c3-4de7-b7ef-d858b2f33eb8', regimen_scheme: 'AZT+3TC+ATV/r', code: '2alt3', openmrs_uuid: 'ba25f2b5-4216-4605-9e6b-1f591033dc3e', active: true, description: 'AZT+3TC+ATV/r', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '3c6e19e6-ba06-4c33-9970-62e89b62582d', regimen_scheme: 'ABC+3TC+LPV/r(2DFC+LPV/r80/20)', code: 'ABCPedXarope', openmrs_uuid: 'cf05347e-063c-4896-91a4-097741cf6be6', active: true, description: 'ABC+3TC+LPV/r(2DFC+LPV/r80/20)', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'a3f0aa60-c296-48bc-aee9-bca43f3d00bb', regimen_scheme: 'ABC+3TC+EFV', code: 'X5A', openmrs_uuid: '78419317-cdda-42e9-92a3-13cb0cbf0020', active: true, description: 'ABC+3TC+EFV', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'de4a3f40-d20c-42c9-b8ee-3c646bc3e85b', regimen_scheme: 'AZT+3TC+NVP', code: 'A2A', openmrs_uuid: 'e1dd2f44-1d5f-11e0-b929-000c29ad1d07', active: false, description: 'AZT+3TC+NVP', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '172a3ed8-5738-4e39-bd63-62467c2068f8', regimen_scheme: 'TDF+AZT+3TC+LPV/r', code: 'C1A', openmrs_uuid: 'e1e59e0e-1d5f-11e0-b929-000c29ad1d07', active: false, description: 'TDF+AZT+3TC+LPV/r', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '84581535-1c71-4e7d-9da3-852fdb8df744', regimen_scheme: 'ABC+3TC+LPV/r(2DFC+LPV/r40/10)', code: 'ABCPedGranulos', openmrs_uuid: 'cf05347e-063c-4896-91a4-097741cf6be6', active: false, description: 'ABC+3TC+LPV/r(2DFC+LPV/r40/10)', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'c9bddf65-1400-4d76-813f-908efc0350c4', regimen_scheme: 'ABC+3TC+EFV Ped(2DFC+EFV200)', code: 'X5APed', openmrs_uuid: 'e1e59e0e-1d5f-11e0-b929-000c29ad1d07', active: false, description: 'ABC+3TC+EFV Ped(2DFC+EFV200)', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'b3a3b2c4-6832-4b9e-bad0-79b9e967ec74', regimen_scheme: 'TDF+3TC+NVP', code: 'TDF12', openmrs_uuid: '2e44e77e-eac4-4f64-84d2-73d32abf94d5', active: true, description: 'TDF+3TC+NVP', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'd513cd89-7b92-433a-82f0-16b9f1b74ee7', regimen_scheme: 'TDF+ABC+LPV/r', code: 'C6A', openmrs_uuid: 'e1e59e0e-1d5f-11e0-b929-000c29ad1d07', active: true, description: 'TDF+ABC+LPV/r', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '81670de2-6dae-4da7-84f6-f6e2c42025cd', regimen_scheme: 'AZT+3TC+LPV/r(2DFC+LPV/r40/10)', code: 'A2Fped Granulos', openmrs_uuid: '28b28521-b6cd-454e-9ec5-f2c6c9c58468', active: true, description: 'AZT+3TC+LPV/r(2DFC+LPV/r40/10)', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '0f347ba1-cba7-4637-978b-1b920e7f0437', regimen_scheme: 'ABC+3TC+RAL', code: '1TB2', openmrs_uuid: 'e1e59e0e-1d5f-11e0-b929-000c29ad1d07', active: true, description: 'ABC+3TC+RAL', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '5c3ca182-b55c-4409-ba6e-9a2586f0e7d1', regimen_scheme: '3TC+RAL+DRV/r', code: '3L_3TC', openmrs_uuid: '', active: false, description: '3TC+RAL+DRV/r', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '0104ded8-054e-41ed-a669-6c11e1d5fec2', regimen_scheme: 'TDF+3TC+DTG', code: '1aLTLD', openmrs_uuid: 'e3f6bb60-e2cf-46cb-a9da-27d634ba8607', active: true, description: 'TDF+3TC+DTG', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '1e4fbb43-ecd9-4d45-9ab7-2232baf47110', regimen_scheme: 'ABC+3TC+DTG', code: '1alt1', openmrs_uuid: 'af15246d-30b8-4aff-8391-ca2b58e2c88b', active: true, description: 'ABC+3TC+DTG', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'ca77f024-b568-4ee5-8fa9-946e5e38a9c5', regimen_scheme: 'd4T30+3TC+dDI400+LPV/r', code: 'dDI123', openmrs_uuid: 'e1e59e0e-1d5f-11e0-b929-000c29ad1d07', active: false, description: 'd4T30+3TC+dDI400+LPV/r', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '5a96f4e2-6ad5-417a-b891-d32998db6ffc', regimen_scheme: 'ABC+3TC+NVP (2FDC+NVP50)', code: 'X5CPed', openmrs_uuid: 'e1e59e0e-1d5f-11e0-b929-000c29ad1d07', active: true, description: 'ABC+3TC+NVP (2FDC+NVP50)', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '238d6085-89bd-4f0d-8055-1f98602b04e5', regimen_scheme: 'AZT+3TC+ABC', code: 'A2C', openmrs_uuid: '3e7f46c7-a971-4c0c-82aa-a65589fd518e', active: true, description: 'AZT+3TC+ABC', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'ea929911-25f9-4687-a867-e8ed126a1755', regimen_scheme: 'AZT+3TC+DTG', code: '1alt2', openmrs_uuid: '9cb63f72-4c08-4543-878a-537dcabe5670', active: false, description: 'AZT+3TC+DTG', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '6f01220d-adbf-43a8-a446-8304816db868', regimen_scheme: 'ABC+AZT+LPV/r', code: 'X3N', openmrs_uuid: 'e1e59e0e-1d5f-11e0-b929-000c29ad1d07', active: false, description: 'ABC+AZT+LPV/r', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '7d010064-2a47-4d62-bf5c-b2de9fea2188', regimen_scheme: 'TDF+AZT+LPV/r', code: 'TDF13', openmrs_uuid: 'e1e59e0e-1d5f-11e0-b929-000c29ad1d07', active: false, description: 'TDF+AZT+LPV/r', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'df826005-f486-40ac-be77-5fd8760b15a3', regimen_scheme: 'TDF+ABC+3TC+LPV/r', code: 'C4A', openmrs_uuid: 'e1e59e0e-1d5f-11e0-b929-000c29ad1d07', active: false, description: 'TDF+ABC+3TC+LPV/r', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'cc844796-789c-4418-a12c-48f14ef4b879', regimen_scheme: 'ABC+3TC+ATV/r', code: '2alt2', openmrs_uuid: 'e8b741b3-463c-46b1-8423-a16f736af8d4', active: true, description: 'ABC+3TC+ATV/r', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'f03621ab-358a-4143-943b-f0df34e70ffa', regimen_scheme: 'TDF+3TC+LPV/r', code: 'C7A', openmrs_uuid: 'f8c5d365-7636-4449-9acd-c83c4fd2ea01', active: true, description: 'TDF+3TC+LPV/r', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '83f720f6-c11b-447f-8149-faaead605104', regimen_scheme: 'AZT+3TC+EFV', code: 'A2B', openmrs_uuid: 'e1de19fe-1d5f-11e0-b929-000c29ad1d07', active: true, description: 'AZT+3TC+EFV', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'c8bfba60-97ce-4a38-8f53-71b1b758c347', regimen_scheme: 'AZT+3TC+ABC (2FDC+ABC Baby)', code: 'A2Cped', openmrs_uuid: '3e7f46c7-a971-4c0c-82aa-a65589fd518e', active: true, description: 'AZT+3TC+ABC (2FDC+ABC Baby)', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '74937b5a-440a-4844-921c-62609ea78725', regimen_scheme: 'AZT+3TC+NVP (3FDC Baby)', code: 'A2Aped', openmrs_uuid: 'e1dd2f44-1d5f-11e0-b929-000c29ad1d07', active: true, description: 'AZT+3TC+NVP (3FDC Baby)', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '93b15d9b-ea38-4438-80f8-dad2162c9c65', regimen_scheme: 'TDF+3TC+EFV', code: 'A4A', openmrs_uuid: '9dc17c1b-7b6d-488e-a38d-505a7b65ec82', active: true, description: 'TDF+3TC+EFV', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'e11e5b38-c18b-4546-bd80-98df9906957a', regimen_scheme: 'AZT+3TC+RAL+DRV/r', code: '3Lb', openmrs_uuid: '', active: false, description: 'AZT+3TC+RAL+DRV/r', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'f415d3be-67f3-4801-863f-b5a245e918fe', regimen_scheme: 'TDF+3TC+RAL+DRV/r', code: '3La', openmrs_uuid: '', active: false, description: 'TDF+3TC+RAL+DRV/r', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '3d080342-990e-47aa-8195-81e472cc5fbc', regimen_scheme: 'ABC+3TC+RAL+DRV/r', code: '3Lbb', openmrs_uuid: '', active: true, description: 'ABC+3TC+RAL+DRV/r', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '0cba491b-afd9-4f15-bad1-1a78f94ab40d', regimen_scheme: 'd4T+3TC+EFV (2DFC Baby + EFV)', code: 'D4T123X', openmrs_uuid: '', active: false, description: 'd4T+3TC+EFV (2DFC Baby + EFV)', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'dbd4d793-5d1e-40d8-b242-1153e7977438', regimen_scheme: 'TDF+3TC+RAL', code: '1TB1', openmrs_uuid: 'e1e59e0e-1d5f-11e0-b929-000c29ad1d07', active: false, description: 'TDF+3TC+RAL', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '85cf6677-186b-40e8-a3a5-52bc493a82b0', regimen_scheme: 'AZT+3TC+RAL', code: '1TB3', openmrs_uuid: 'c4a56680-ac6e-4538-8126-e3097b7b4789', active: true, description: 'AZT+3TC+RAL', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'b0da927c-c1e4-4df7-8e5c-634d3a43bcc1', regimen_scheme: 'PTV Pediatrico', code: 'PTV Pediatri', openmrs_uuid: 'e1e59e0e-1d5f-11e0-b929-000c29ad1d07', active: false, description: 'PTV Pediatrico', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '6c87963f-d603-4a62-a0c1-df6645f74f3c', regimen_scheme: 'TDF+FTC PreEP', code: 'PreEP', openmrs_uuid: 'e1e59e0e-1d5f-11e0-b929-000c29ad1d07', active: false, description: 'TDF+FTC PreEP', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'f4b4703e-49aa-4e86-8aee-5a22f3c4bde4', regimen_scheme: 'ABC+3TC120/60+DTG', code: 'X6APed', openmrs_uuid: 'e1e59e0e-1d5f-11e0-b929-000c29ad1d07', active: false, description: 'ABC+3TC120/60+DTG', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '80f03d2f-b7b6-4922-a79d-344959517911', regimen_scheme: 'AZT+3TC+LPV/r', code: 'A2F', openmrs_uuid: 'daf60844-9002-403f-bd93-3838149a9a5e', active: false, description: 'AZT+3TC+LPV/r', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '1d2b832f-8ef9-4a32-8fdd-8774aa579533', regimen_scheme: 'ABC+3TC+NVP', code: 'X5C', openmrs_uuid: 'e11be52e-0da1-4d32-ab5c-e0feb9b6abd6', active: false, description: 'ABC+3TC+NVP', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'ef22088a-30d4-4981-8628-3ae6b7026ea5', regimen_scheme: 'ABC+AZT+3TC+LPV/r', code: 'X3N1', openmrs_uuid: 'e1e59e0e-1d5f-11e0-b929-000c29ad1d07', active: false, description: 'ABC+AZT+3TC+LPV/r', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '4f630e5d-f35c-4bc4-acb6-3d19b1b420f6', regimen_scheme: 'AZT+3TC+EFV (2FDC+EFV 200)', code: 'A2Bped', openmrs_uuid: 'e1de19fe-1d5f-11e0-b929-000c29ad1d07', active: true, description: 'AZT+3TC+EFV (2FDC+EFV 200)', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'a54eed82-3789-4863-ae37-ff43e19bab90', regimen_scheme: 'AZT+3TC+LPV/r(2DFC+LPV/r100/25', code: 'A2Fped Cpts', openmrs_uuid: '28b28521-b6cd-454e-9ec5-f2c6c9c58468', active: true, description: 'AZT+3TC+LPV/r(2DFC+LPV/r100/25', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '3f892be3-f5d7-404b-b2ca-dfcea9edcc80', regimen_scheme: 'ABC+3TC120/60+LPV/R100/25', code: '2ALT3', openmrs_uuid: 'ABC+3TC120/60+LPV/R100/25', active: false, description: 'ABC+3TC120/60+LPV/R100/25', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'd68c8585-1d4b-411c-94f9-45bedca6894c', regimen_scheme: 'ABC+3TC+LPV/r', code: 'ABC12', openmrs_uuid: 'cf05347e-063c-4896-91a4-097741cf6be6', active: true, description: 'ABC+3TC+LPV/r', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'fb27fd40-c739-407d-b9e0-37bac8561bff', regimen_scheme: 'd4T+3TC+LPV/r(2DFC Baby+LPV/r)', code: 'D4T124', openmrs_uuid: '', active: false, description: 'd4T+3TC+LPV/r(2DFC Baby+LPV/r)', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'a647afcb-4c90-48de-a647-39ec87aca13d', regimen_scheme: 'd4T+3TC+NVP (3DFC Baby )', code: 'A1Aped', openmrs_uuid: '', active: false, description: 'd4T+3TC+NVP (3DFC Baby )', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'cb5b73dd-deda-4363-a8da-6859b34f79e4', regimen_scheme: 'd4T+3TC+ABC (2DFC Baby + ABC )', code: 'A1Eped', openmrs_uuid: '', active: false, description: 'd4T+3TC+ABC (2DFC Baby + ABC )', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '8A049D25-8804-439D-84D3-A730E0F0699F', regimen_scheme: '2as Optimizadas ATV/r', code: '2Op1', openmrs_uuid: '', active: true, description: '2as Optimizadas ATV/r', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '9FC3ABE1-925E-43F2-A2F4-D01C9B67BA89', regimen_scheme: '2as Optimizadas ATV/r+RAL', code: '2Op2', openmrs_uuid: '', active: true, description: '2as Optimizadas ATV/r+RAL', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '1F7B72CB-DF9D-4FFB-BA00-7D6B847223AA', regimen_scheme: '2as Optimizadas DRV+RTV', code: '2Op3', openmrs_uuid: '', active: true, description: '2as Optimizadas DRV+RTV', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'B34FEE8D-52B8-40B2-819C-66CA29FC5ADC', regimen_scheme: '3a Linha adaptada DRV+RAL+RTV', code: '3op1', openmrs_uuid: '', active: true, description: '3a Linha adaptada DRV+RAL+RTV', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '05D2C1BF-A824-410B-B627-211302400213', regimen_scheme: 'ABC+3TC+DTG (2DFC ped + DTG10)', code: 'X7APed', openmrs_uuid: '', active: true, description: 'ABC+3TC+DTG (2DFC ped + DTG10)', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '7D18A417-7CCE-47C2-A1EF-6212356FA7FE', regimen_scheme: 'AZT+3TC+LPV/r (2FDC+LPV/r Baby)', code: 'A2Fped', openmrs_uuid: '', active: true, description: 'AZT+3TC+LPV/r (2FDC+LPV/r Baby)', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '678A48E0-6B7B-438D-B5CD-EFFA4557F5E9', regimen_scheme: 'ABC+3TC+LPV/r (2FC+LPV/r)', code: 'ABCPedNew', openmrs_uuid: '', active: false, description: 'ABC+3TC+LPV/r (2FC+LPV/r)', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'E10520F0-E38C-4FD6-A1D8-F4D9DE637F7C', regimen_scheme: 'ABC+dDI250+LPV/r', code: 'C2A2', openmrs_uuid: '', active: false, description: 'ABC+dDI250+LPV/r', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'B657EB26-005E-4F95-A069-DE08D346820A', regimen_scheme: 'ABC+dDI400+LPV/r', code: 'C2A1', openmrs_uuid: '', active: false, description: 'ABC+dDI400+LPV/r', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'F8120855-2653-4C74-8D1D-E00517477012', regimen_scheme: 'AZT+3TC+ddI 250+LPV/r', code: 'B5C', openmrs_uuid: '', active: false, description: 'AZT+3TC+ddI 250+LPV/r', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '91C6F86A-DC93-488F-B490-0359C18AFD76', regimen_scheme: 'AZT+3TC+ddI 400+LPV/r', code: 'B5D', openmrs_uuid: '', active: false, description: 'AZT+3TC+ddI 400+LPV/r', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '4BA93343-3129-404A-B82C-0FFBFB3F4006', regimen_scheme: 'AZT+ddI 250+LPV/r', code: 'B1C', openmrs_uuid: '', active: false, description: 'AZT+ddI 250+LPV/r', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'D04B75BE-2B57-48FE-9044-976C1D01D4E6', regimen_scheme: 'AZT+ddI 400+EFV', code: 'A3M', openmrs_uuid: '', active: false, description: 'AZT+ddI 400+EFV', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '63E0973A-E072-430A-AC28-0696F907F21E', regimen_scheme: 'AZT+ddI 400+LPV/r', code: 'B1D', openmrs_uuid: '', active: false, description: 'AZT+ddI 400+LPV/r', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'A343AD70-87E0-49E5-9408-A4E7148AFEC8', regimen_scheme: 'd4T 30+3TC+ABC', code: 'A1E', openmrs_uuid: '', active: false, description: 'd4T 30+3TC+ABC', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '16A80420-067A-4E6F-8881-44779B89BAAD', regimen_scheme: 'd4T 30+3TC+ddI 250+LPV/r', code: 'B2F', openmrs_uuid: '', active: false, description: 'd4T 30+3TC+ddI 250+LPV/r', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'F0AC82D1-3AFF-4A5B-8E20-6102C011660E', regimen_scheme: 'd4T 30+3TC+EFV', code: 'A1C', openmrs_uuid: '', active: false, description: 'd4T 30+3TC+EFV', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'BDE352BF-7E1E-4AFE-B85C-B8DE54381D5F', regimen_scheme: 'd4T 30+3TC+NVP', code: 'A1A', openmrs_uuid: '', active: false, description: 'd4T 30+3TC+NVP', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '352FEF64-1ACB-45E2-806A-9026B132F785', regimen_scheme: 'd4T 30+ddI 250+EFV', code: 'A3L', openmrs_uuid: '', active: false, description: 'd4T 30+ddI 250+EFV', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '6EC60A57-B191-4C38-B82A-FF0820DCBA8E', regimen_scheme: 'd4T 30+ddI 250+NVP', code: 'A1K', openmrs_uuid: '', active: false, description: 'd4T 30+ddI 250+NVP', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '7DF4F49A-DA4C-498A-9879-0C63276ADF8E', regimen_scheme: 'd4T+3TC+EFZ200 (Ped)', code: 'D4T123', openmrs_uuid: '', active: false, description: 'd4T+3TC+EFZ200 (Ped)', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'F7901F59-C9B0-46BC-AA8D-2B906ACDFF8B', regimen_scheme: 'd4T30+3TC+ABC+LPV/r', code: 'A1O', openmrs_uuid: '', active: false, description: 'd4T30+3TC+ABC+LPV/r', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'DE077C18-BFDC-453E-BA70-544B59517EB2', regimen_scheme: 'd4T30+3TC+LPV/r', code: 'A1M', openmrs_uuid: '', active: false, description: 'd4T30+3TC+LPV/r', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'D67DBB95-46EF-4074-9D87-284FD7A7F5CF', regimen_scheme: 'd4T30+3TC+SQV+RTV', code: 'A1P', openmrs_uuid: '', active: false, description: 'd4T30+3TC+SQV+RTV', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'B2A9928E-C66B-47AE-AC9A-037066B6A8A1', regimen_scheme: 'd4T30+ABC+LPV/r', code: 'A1N', openmrs_uuid: '', active: false, description: 'd4T30+ABC+LPV/r', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '6245CDD5-000F-4EBF-AF53-4B0B36591661', regimen_scheme: 'd4T30+dDI250+LPV/r', code: 'C2X2', openmrs_uuid: '', active: false, description: 'd4T30+dDI250+LPV/r', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'E3BA4694-3315-4B52-803F-1189C2A873BD', regimen_scheme: 'd4T30+dDI400+LPV/r', code: 'C2X1', openmrs_uuid: '', active: false, description: 'd4T30+dDI400+LPV/r', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'F69C1484-5872-47D5-8240-024F3557A433', regimen_scheme: 'ddI 250+3TC+EFV', code: 'A3C', openmrs_uuid: '', active: false, description: 'ddI 250+3TC+EFV', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'B207065A-CFE7-4986-BE91-D8EE6913887F', regimen_scheme: 'ddI 250+3TC+LPV/r', code: 'A3N', openmrs_uuid: '', active: false, description: 'ddI 250+3TC+LPV/r', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '1A2D5659-CB3C-46C5-AF7F-21581A168999', regimen_scheme: 'ddI 250+3TC+NVP', code: 'A3A', openmrs_uuid: '', active: false, description: 'ddI 250+3TC+NVP', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'F7803CDE-3005-43B8-AF50-B6827A2D074C', regimen_scheme: 'ddI 400+3TC+EFV', code: 'A3D', openmrs_uuid: '', active: false, description: 'ddI 400+3TC+EFV', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'DFF036F8-CE2A-4901-9ECD-39EFAC3D4E18', regimen_scheme: 'ddI 400+3TC+LPV/r', code: 'A3O', openmrs_uuid: '', active: false, description: 'ddI 400+3TC+LPV/r', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '4841FD1F-A01C-4EB1-8565-BDD3D9AAA1AC', regimen_scheme: 'ddI 400+3TC+NVP', code: 'A3B', openmrs_uuid: '', active: false, description: 'ddI 400+3TC+NVP', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '18BD5C82-EDD7-4497-9132-7064E0BFCB12', regimen_scheme: 'Regime Genérico TARV', code: 'RG TARV', openmrs_uuid: '', active: false, description: 'Regime Genérico TARV', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'FAA2F4FC-40B0-4A1A-B1D4-E8D49616B64B', regimen_scheme: 'SQV+RTV+ddI 250+ABC', code: 'C8A', openmrs_uuid: '', active: false, description: 'SQV+RTV+ddI 250+ABC', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '9C970473-2C41-4411-A4B0-8FBCCA062919', regimen_scheme: 'SQV+RTV+ddi 400+ABC', code: 'C8B', openmrs_uuid: '', active: false, description: 'SQV+RTV+ddi 400+ABC', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: '0765552C-7369-4281-BC8A-A8DD4E297A23', regimen_scheme: 'TDF+3TC+d4T+LPV/r', code: 'TDF14', openmrs_uuid: '', active: false, description: 'TDF+3TC+d4T+LPV/r', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        therapeuticRegimenList.add(new LinkedHashMap(id: 'B9509EF0-D01D-47AF-961C-CED32A841E8D', regimen_scheme: 'TDF+AZT+3TC+SQV+RTV', code: 'C3A1', openmrs_uuid: '', active: false, description: 'TDF+AZT+3TC+SQV+RTV', clincal_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))

        return therapeuticRegimenList

    }


    List<Object> listDrug() {

        List<Object> listDrug = new ArrayList<>()

        listDrug.add(new LinkedHashMap(id: '8fa7539a-4452-44b2-9b0f-c1d5229746b4', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 30, name: '[TDF/3TC/EFV] Tenofovir 300mg/Lamivudina 300mg/Efavirenze 400mg TLE30', uuid_openmrs: '08S18X-b0-de4a-4233-86fa-c91f9e606be5', fnm_code: '08S18X', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: 'f0196993-bca0-4284-9474-c7fc5f0f5000', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 1, name: '[Vit B6 25mg cp] Piridoxina (Vit B6) 25mg', uuid_openmrs: '', fnm_code: '12D14Z', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '6D12193B-7D5D-4665-8FC6-A03855986FBD'))
        listDrug.add(new LinkedHashMap(id: '06fd53a0-5201-4bf8-8e46-f4e0b0809963', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 1, name: '[RPT/INH 300/300mg cp] Rifapentina 300mg/ Isoniazida 300mg', uuid_openmrs: '', fnm_code: '08L06X', default_treatment: 3, default_period_treatment: 'Semana', active: true, clinical_service_id: '6D12193B-7D5D-4665-8FC6-A03855986FBD'))
        listDrug.add(new LinkedHashMap(id: '0de13637-deb3-42d1-8870-b5f4102dc641', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 1, name: '[RPT 150mg cp] Rifapentina 150mg', uuid_openmrs: '', fnm_code: '08L06XZ', default_treatment: 1, default_period_treatment: 'Semana', active: true, clinical_service_id: '6D12193B-7D5D-4665-8FC6-A03855986FBD'))
        listDrug.add(new LinkedHashMap(id: '6cba5522-48f6-4f3e-9ece-e4f2495f79ab', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 1, name: '[LFX 250mg cp] Levofloxacina 250mg ', uuid_openmrs: '', fnm_code: '08H07', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '6D12193B-7D5D-4665-8FC6-A03855986FBD'))
        listDrug.add(new LinkedHashMap(id: 'ebaec112-1b91-4aaa-a865-d5b770b5bebd', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 1, name: '[LFX 100mg cp] Levofloxacina 100 mg Disp', uuid_openmrs: '', fnm_code: '08H07Y', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '6D12193B-7D5D-4665-8FC6-A03855986FBD'))
        listDrug.add(new LinkedHashMap(id: 'b40c9f35-16c1-48da-af00-c45096fbf88c', form_id: '74C8F060-1EA4-45E9-94DB-2DE6775E6481', default_times: 2, pack_size: 120, name: '[LPV/RTV] Lopinavir/Ritornavir 40mg/10mg Pellets/Granulos', uuid_openmrs: '08S38Y-0c-0932-4b37-ab53-4aae60820544', fnm_code: '08S38Y', default_treatment: 2, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: 'ef000f5c-2e4c-4843-b2fb-0a265dc60a6a', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 2, pack_size: 60, name: '[RAL] Raltegravir 400mg', uuid_openmrs: '08S30ZZ-b2-edb5-4815-a4bf-8f0618f029be', fnm_code: '08S30ZZ', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: 'd95d079e-a83c-4ca5-86c6-23b99e6fa6a7', form_id: '742F4BC0-E0CC-4602-829B-BEC4EAFB0D2C', default_times: 1, pack_size: 80, name: '[LPV/RTV] Lopinavir/Ritonavir 400mg/100mg 5ml 80ml', uuid_openmrs: '08S39-054-ad23-4d26-8ca1-88308070d08e', fnm_code: '08S39', default_treatment: 0, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '53b9d0ca-ce34-42d7-b9b1-0e9de4f47222', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 2, pack_size: 120, name: '[LPV/RTV] Lopinavir/Ritonavir 200mg/50mg', uuid_openmrs: '08S39Z-7a-93f6-4e05-baa3-8c4684c8013e', fnm_code: '08S39Z', default_treatment: 2, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: 'bc7fe62f-a72f-4935-84a8-1d47a60db24f', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 2, pack_size: 60, name: '[NVP] Nevirapine 200mg', uuid_openmrs: '08S22-d18-2655-4426-868a-b291a5adff38', fnm_code: '08S22', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '8aa8e4c1-3d9c-4d67-9d28-a27e018e0676', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 60, name: '[NVP]  Nevirapina 50mg', uuid_openmrs: '08S23Z-cd-22ff-4993-9378-335cf43bd5c8', fnm_code: '08S23Z', default_treatment: 0, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '6ede4a9f-8da4-4581-b8d8-52a88e01f8b2', form_id: '742F4BC0-E0CC-4602-829B-BEC4EAFB0D2C', default_times: 2, pack_size: 240, name: '[AZT] Zidovudine 50mg/5ml', uuid_openmrs: '08S17Y-b6-6e76-451d-8892-a6d407e0e0d6', fnm_code: '08S17Y', default_treatment: 0, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: 'dcd3ce1c-5b97-4248-ba51-c1b98ebbc376', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 2, pack_size: 60, name: '[AZT] Zidovudine 300mg', uuid_openmrs: '08S15-f3b-d54c-420d-ad96-698f71c003c5', fnm_code: '08S15', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '6d0462de-c438-4099-af6a-c1ea8ff2e224', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 2, pack_size: 60, name: '[ABC] Abacavir 300mg', uuid_openmrs: '08S01-ab-d6e9-4de6-beb8-441daf8d00a3', fnm_code: '08S01', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '59c796e2-89e2-4473-b827-45aae3cac43d', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 2, pack_size: 60, name: '[ABC] Abacavir 60mg', uuid_openmrs: '08S01Z-02-843e-4dd0-af6f-2eff173d4b30', fnm_code: '08S01Z', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: 'a037bad8-1e21-4f37-be3c-120c6751d3ac', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 30, name: '[EFV] Efavirenz 600mg', uuid_openmrs: '08S21-918-6273-4f5c-be72-755da371e731', fnm_code: '08S21', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '69600a88-15c0-436c-a6ed-9d14b97c7586', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 90, name: '[EFV] Efavirenz 200mg', uuid_openmrs: '08S20-9db-3bef-4de8-af61-4610d4ead1ba', fnm_code: '08S20', default_treatment: 0, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: 'd012e46e-5aec-4993-bed4-5f062f3b63a6', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 30, name: '[TDF] Tenofovir 300mg', uuid_openmrs: '08S18-6cb-4b62-4cc0-98af-8053e1396419', fnm_code: '08S18', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: 'a46c022d-0d74-46ec-9932-7b02788afea5', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 90, name: '[TDF/3TC/EFV] Tenofovir 300mg/Lamivudina 300mg/Efavirenze 400mg TLE90', uuid_openmrs: '08S18XI-6-0715-4389-a4b2-d20de330e26c', fnm_code: '08S18XI', default_treatment: 0, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '03e69b01-f5d7-49e9-9fd4-bc582c24af2b', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 2, pack_size: 60, name: '[3TC/AZT/NVP] Lamivudina 150mg/Zidovudina 300mg/Nevirapina 200mg', uuid_openmrs: '08S42-d98-6b3e-485f-b969-376fca8b7789', fnm_code: '08S42', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '45a61698-cc56-4f7f-8081-8b8c17df3622', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 60, name: '[3TC/AZT/NVP] Lamivudina 30mg/Zidovudina 60mg/Nevirapina 50mg', uuid_openmrs: '08S42B-d89-7e03-4f25-975e-b66a5e28e063', fnm_code: '08S42B', default_treatment: 0, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '4d2c441c-4393-489e-bd4c-bab5c7456a78', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 60, name: '[3TC/AZT/ABC] Lamivudina 150mg/Zidovudina 300mg/Abacavir 300mg', uuid_openmrs: '08S41-ec0-c1cf-42e1-9a37-9f23b4f07bba', fnm_code: '08S41', default_treatment: 0, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: 'b8620a31-e203-4b1d-9f22-7e13d5c44746', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 30, name: '[TDF/FTC] Tenofovir 300mg/Emtricitabina 200mg', uuid_openmrs: '08S31-9e9-49f9-4cd6-8268-8df7315e3d09', fnm_code: '08S31', default_treatment: 0, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: 'cfe9ea7f-7833-47e9-9049-f3309446e984', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 60, name: '[ABC/3TC] Abacavir 120mg/Lamivudina 60mg', uuid_openmrs: '08S01ZW-ec-ec31-45aa-a74e-7238872483e8', fnm_code: '08S01ZW', default_treatment: 0, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '135b1a6f-0791-47d4-8e8e-6f8b75bab054', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 2, pack_size: 60, name: '[3TC/AZT] Lamivudina 30mg/ Zidovudina 60mg', uuid_openmrs: '08S40Z-fc-6563-49e4-bf81-a456bf79ec88', fnm_code: '08S40Z', default_treatment: 0, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: 'f8a6a5be-9737-474b-ade2-b2789610d7ee', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 2, pack_size: 120, name: '[LPV/RTV] Lopinavir/Ritonavir -Aluvia 200mg/50mg', uuid_openmrs: '08S38Z-99-3fe6-48b7-9b25-3052660f3d8b', fnm_code: '08S38Z', default_treatment: 2, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '909a8ee5-30cd-45ef-8540-0a44f26a1a09', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 60, name: '[ABC/3TC] Abacavir 60 and Lamivudina 30mg', uuid_openmrs: '08S01ZZ-2e-29dd-40aa-94b4-0d4fe65e081c', fnm_code: '08S01ZZ', default_treatment: 0, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '3c6518d4-ad5b-445e-b3d4-8812363e056c', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 2, pack_size: 60, name: '[3TC/D4T/NVP] Lamivudina 150mg/Stavudina 30mg/Nevirapina 200mg', uuid_openmrs: '08S4X-833-b26a-4996-8066-48847431404a', fnm_code: '08S4X', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '10a8ead6-f561-4440-b483-4261e27be295', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 30, name: '[ABC/3TC] Abacavir 600mg/Lamivudina 300mg', uuid_openmrs: '08S01ZY-d7-4218-4032-aa8c-615aec71a218', fnm_code: '08S01ZY', default_treatment: 0, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '8ba0bfde-b1b1-408a-806f-4683f2bb17c1', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 2, pack_size: 60, name: '[3TC/AZT] Lamivudina 150mg/ Zidovudina 300mg', uuid_openmrs: '08S40-833-b26a-4996-8066-48847431404a', fnm_code: '08S40', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: 'df7f5db0-cd09-44fd-a1cd-d9398482dffc', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 60, name: '[LPV/RTV] Lopinavir/Ritonavir -Aluvia 100mg/25mg', uuid_openmrs: '08S39B-b2-e392-425a-b824-f33f745733e6', fnm_code: '08S39B', default_treatment: 0, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '0596bddc-63cb-4805-80ae-2cc693adf66f', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 90, name: '[TDF/3TC/DTG] Tenofovir 300mg/Lamivudina 300mg/Dolutegravir 50mg TLD90', uuid_openmrs: '08S18WI-4-70b1-4732-af8b-be24cb04aaa6', fnm_code: '08S18WI', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '67034ec5-f1b7-4832-b187-d7e621b84262', form_id: '742F4BC0-E0CC-4602-829B-BEC4EAFB0D2C', default_times: 2, pack_size: 240, name: '[NVP] Nevirapine 50mg/5ml', uuid_openmrs: '08S23-316-d7e9-4757-9466-d0e6c1d8b1db', fnm_code: '08S23', default_treatment: 0, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: 'd985d045-c98f-4cec-83c7-6f461e5d36a2', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 1, name: '[INH 100 cp] Isoniazida 100mg', uuid_openmrs: '', fnm_code: '08L04', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '6D12193B-7D5D-4665-8FC6-A03855986FBD'))
        listDrug.add(new LinkedHashMap(id: '1892f3ff-d77d-4d46-b8df-496695eac891', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 30, name: '[TDF/3TC] Tenofovir 300mg/Lamivudina 300mg', uuid_openmrs: '08S18Z-da-b787-4fa1-a2d6-2fda22da6564', fnm_code: '08S18Z', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: 'f8f1d700-5556-4baf-be6d-ba293b38cee4', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 1, name: '[INH 300mg cp] Isoniazida 300mg', uuid_openmrs: '', fnm_code: '08L03', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '6D12193B-7D5D-4665-8FC6-A03855986FBD'))
        listDrug.add(new LinkedHashMap(id: '238ea424-9f4e-4b6e-8cec-b39c3e4e3345', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 1, name: '[Vit B6 50mg cp] Piridoxina (Vitamina B6) 50mg', uuid_openmrs: '', fnm_code: '12D14', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '6D12193B-7D5D-4665-8FC6-A03855986FBD'))
        listDrug.add(new LinkedHashMap(id: '3cbfb612-cc39-4001-82f0-451568d43a8c', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 30, name: '[ATV/RTV] Atazanavir 300mg/Ritonavir 100mg', uuid_openmrs: '08S30WZ-48-cf6b-47e2-a101-0cc9faa8fbce', fnm_code: '08S30WZ', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: 'd5d428c0-6219-459c-8837-b4069694b45a', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 2, pack_size: 60, name: '[3TC] Lamivudine 150mg', uuid_openmrs: '08S13-ddb-e5bd-4b96-ae5d-11ada78c8a35', fnm_code: '08S13', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: 'c8e78317-ee4d-415f-9674-d3e0fd543d12', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 180, name: '[TDF/3TC/EFV] Tenofovir 300mg/Lamivudina 300mg/Efavirenze 400mg TLE180', uuid_openmrs: '08S18XII-3a07-4e5f-9d00-0016dc840a84', fnm_code: '08S18XII', default_treatment: 0, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: 'da4377a3-f9ab-4bb3-aa33-7aa1aee56db5', form_id: '742F4BC0-E0CC-4602-829B-BEC4EAFB0D2C', default_times: 2, pack_size: 60, name: '[LPV/RTV]  Lopinavir/Ritonavir-Kaletra 80/20 mg/ml', uuid_openmrs: '08S39Y-3a-20c8-4a16-aaea-f2d4537202e4', fnm_code: '08S39Y', default_treatment: 0, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '215B0404-E745-40C8-9F53-4C1BB510D4DF', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 30, name: '[EFV] Efavirenz 50mg', uuid_openmrs: '08S20-9db-3bef-4de8-af61-4610d4ead1ba', fnm_code: 'TSW80', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '1c607f0f-311a-43a4-ac8c-2e205d53ad0e', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 180, name: '[TDF/3TC/DTG] Tenofovir 300mg/Lamivudina 300mg/Dolutegravir 50mg TLD180', uuid_openmrs: '08S18WII-5863-4423-9e97-bc1b480df134', fnm_code: '08S18WII', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '228bfc9b-2260-407b-9ef5-db5c6c4f0523', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 30, name: '[TDF/3TC/DTG] Tenofovir 300mg/Lamivudina 300mg/Dolutegravir 50mg TLD30', uuid_openmrs: '08S18W-7f-c2a7-4d27-95dc-564791951b5f', fnm_code: '08S18W', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '30daa641-eefa-4dd7-a924-2f49e9f127b1', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 30, name: '[DTG] Dolutegravir 50mg', uuid_openmrs: '08S30ZY-ae-3c79-46bd-9970-2d02b8788fdf', fnm_code: '08S30ZY', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '8AAA4C79-A149-459C-BE95-2934DE80F93F', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 30, name: '[DTG] Dolutegravir 10mg', uuid_openmrs: '', fnm_code: '08S30ZXi', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '797715FF-3272-4E30-87D7-A45DB9DE05FE', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 90, name: '[DTG] Dolutegravir 10mg', uuid_openmrs: '', fnm_code: '08S30ZX', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: 'DE655468-9D3F-45A0-92D3-42E44685A576', form_id: '74C8F060-1EA4-45E9-94DB-2DE6775E6481', default_times: 1, pack_size: 30, name: '[DDI] Didanosina libertacao lenta 250mg', uuid_openmrs: '', fnm_code: '08S08', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: 'CE60B194-AC32-481D-8965-937E63964584', form_id: '74C8F060-1EA4-45E9-94DB-2DE6775E6481', default_times: 1, pack_size: 30, name: '[DDI] Didanosina libertacao lenta 400mg', uuid_openmrs: '', fnm_code: '08S09', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '1F0BCF33-07BC-4CAF-B01E-9BFFC558A25F', form_id: '74C8F060-1EA4-45E9-94DB-2DE6775E6481', default_times: 1, pack_size: 30, name: '[EFV] Efavirenz 50mg', uuid_openmrs: '', fnm_code: '08S19', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '9FE7AE23-0ABD-4423-AEF0-B7900BCF6AD3', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 30, name: '[RTV] Ritonavir 100mg', uuid_openmrs: '', fnm_code: '08S29Z', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '71C04DAA-CA94-4B6D-B828-30E197402B63', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 60, name: '[D4T/3TC] Estavudina 30mg/Lamivudina150mg', uuid_openmrs: '', fnm_code: '08S32', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: 'EE02A3FE-F6BE-4078-9E9C-94CB36DB0ADA', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 60, name: '[D4T/3TC] Estavudina 200mg/Lamivudina 150mg/Nevirapina 30mg ', uuid_openmrs: '', fnm_code: '08S36', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: 'F1048116-51BC-4FB4-81F4-0CE611C2F44A', form_id: '74C8F060-1EA4-45E9-94DB-2DE6775E6481', default_times: 1, pack_size: 60, name: '[D4T/3TC] Estavudina 6mg/Lamivudina 30mg/Nevirapina 50mg ', uuid_openmrs: '', fnm_code: '08S34B', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: 'F7988AA1-ADBC-4BCF-BBF1-0CFF93F8CFCD', form_id: '74C8F060-1EA4-45E9-94DB-2DE6775E6481', default_times: 1, pack_size: 270, name: '[SQV] Saquinavir 200mg', uuid_openmrs: '', fnm_code: '08S30', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '5BE410A2-F63C-4FA5-A31E-5CD623569604', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 60, name: '[DDI] Didanosina 25mg', uuid_openmrs: '', fnm_code: '08S03Z', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '10296AA7-B361-4D11-B61F-C84B1C9EB460', form_id: '742F4BC0-E0CC-4602-829B-BEC4EAFB0D2C', default_times: 1, pack_size: 240, name: '[3TC] Lamivudina 50mg/5mL', uuid_openmrs: '', fnm_code: '08S14', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: 'CF46123E-6BE9-4D7E-85DC-75DF88472E82', form_id: '742F4BC0-E0CC-4602-829B-BEC4EAFB0D2C', default_times: 1, pack_size: 240, name: '[AZT] Zidovudina 50mg/5mL', uuid_openmrs: '', fnm_code: '08S17', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: 'F0E0B162-64F7-466A-8B21-C3B2A9E8838B', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 30, name: '[TDF/3TC/EFV] Tenofovir 300mg/Lamivudina 300mg/Efavirenz 600mg', uuid_openmrs: '', fnm_code: '08S18Y', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '44A0216C-48BD-406F-9C41-60DA9DCB05D9', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 30, name: '[DRV] Darunavir 300mg ', uuid_openmrs: '', fnm_code: '08S30Z', default_treatment: 1, default_period_treatment: 'Dia', active: true, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: 'D7C3CDFE-A012-4B96-9595-8170FACDCBBD', form_id: '742F4BC0-E0CC-4602-829B-BEC4EAFB0D2C', default_times: 1, pack_size: 300, name: '[ABC] Abacavir sulfato 10mg/mL', uuid_openmrs: '', fnm_code: '08S02', default_treatment: 1, default_period_treatment: 'Dia', active: false, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '98D80A5B-2C0A-4EB0-B7AD-01B3B03FB1AD', form_id: '742F4BC0-E0CC-4602-829B-BEC4EAFB0D2C', default_times: 1, pack_size: 600, name: '[ABC] Abacavir sulfato 20mg/mL', uuid_openmrs: '', fnm_code: '08S02Z', default_treatment: 1, default_period_treatment: 'Dia', active: false, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '9C60B7A7-17F0-45F7-B410-BAFB5B580FDA', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 30, name: '[DDI] Didanosina 100mg', uuid_openmrs: '', fnm_code: '08S04', default_treatment: 1, default_period_treatment: 'Dia', active: false, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '10BDC114-7DB7-477F-AC4A-812AC9C74ACB', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 30, name: '[DDI] Didanosina 150mg', uuid_openmrs: '', fnm_code: '08S05', default_treatment: 1, default_period_treatment: 'Dia', active: false, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '09DD20BB-ACF0-42C4-8456-AA3E20D5244F', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 30, name: '[DDI] Didanosina 200mg;', uuid_openmrs: '', fnm_code: '08S06', default_treatment: 1, default_period_treatment: 'Dia', active: false, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '14F63C61-5334-4B2D-B3E5-FA7F752B46F8', form_id: '74C8F060-1EA4-45E9-94DB-2DE6775E6481', default_times: 1, pack_size: 30, name: '[DDI] Didanosina libertacao lenta 125mg', uuid_openmrs: '', fnm_code: '08S07', default_treatment: 1, default_period_treatment: 'Dia', active: false, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '3A8336DB-0B51-4718-9BCF-5C1FB6F5892C', form_id: '74C8F060-1EA4-45E9-94DB-2DE6775E6481', default_times: 1, pack_size: 60, name: '[D4T] Estavudina 30mg', uuid_openmrs: '', fnm_code: '08S10', default_treatment: 1, default_period_treatment: 'Dia', active: false, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '0B2B431C-D5E5-4CC7-89BE-F50D03470E05', form_id: '74C8F060-1EA4-45E9-94DB-2DE6775E6481', default_times: 1, pack_size: 30, name: '[D4T] Estavudina 20mg', uuid_openmrs: '', fnm_code: '08S10Y', default_treatment: 1, default_period_treatment: 'Dia', active: false, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '3D535485-3F5D-4FF9-B18E-285842F11328', form_id: '74C8F060-1EA4-45E9-94DB-2DE6775E6481', default_times: 1, pack_size: 30, name: '[D4T] Estavudina 15mg', uuid_openmrs: '', fnm_code: '08S10Z', default_treatment: 1, default_period_treatment: 'Dia', active: false, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '1F49A249-32E4-49B0-B89F-92ADA0823B60', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 30, name: '[D4T] Estavudina 40mg', uuid_openmrs: '', fnm_code: '08S11', default_treatment: 1, default_period_treatment: 'Dia', active: false, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '82C08307-8878-459B-8267-A3BE76364D99', form_id: '742F4BC0-E0CC-4602-829B-BEC4EAFB0D2C', default_times: 1, pack_size: 30, name: '[D4T] Estavudine solução; 1mg/mL', uuid_openmrs: '', fnm_code: '08S12', default_treatment: 1, default_period_treatment: 'Dia', active: false, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: 'F49C62A3-D676-49E6-A2ED-87439D433D7B', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 30, name: '[APV] Amprenavir 150mg', uuid_openmrs: '', fnm_code: '08S24', default_treatment: 1, default_period_treatment: 'Dia', active: false, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '9AB55DB5-22FF-44A4-A39D-39343A169A01', form_id: '742F4BC0-E0CC-4602-829B-BEC4EAFB0D2C', default_times: 1, pack_size: 30, name: '[APV] Amprenavir 15mg/mL', uuid_openmrs: '', fnm_code: '08S25', default_treatment: 1, default_period_treatment: 'Dia', active: false, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '6641A2EE-8AAD-4E35-AEDE-A42E3DCBFC7A', form_id: '74C8F060-1EA4-45E9-94DB-2DE6775E6481', default_times: 1, pack_size: 180, name: '[IDV] Indinavir 400mg,', uuid_openmrs: '', fnm_code: '08S26', default_treatment: 1, default_period_treatment: 'Dia', active: false, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: 'A487066D-31F1-43C2-AA40-7720071F8310', form_id: 'AB6442FF-6DA0-46F2-81E1-F28B1A44A31C', default_times: 1, pack_size: 60, name: '[D4T/3TC] Estavudina 40mg/Lamivudina 150mg ', uuid_openmrs: '', fnm_code: '08S33', default_treatment: 1, default_period_treatment: 'Dia', active: false, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))
        listDrug.add(new LinkedHashMap(id: '0064E589-AB56-4D06-B52C-AFF6D8BDE4D5', form_id: '74C8F060-1EA4-45E9-94DB-2DE6775E6481', default_times: 1, pack_size: 30, name: '[LPV/RTV] Lopinavir 133,3mg/Ritonavir 33,3mg Gelatinosas', uuid_openmrs: '', fnm_code: '08S38', default_treatment: 1, default_period_treatment: 'Dia', active: false, clinical_service_id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F'))

        return listDrug


    }

    List<Object> listRegimenDrugs() {

        List<Object> listDrugRegimen = new ArrayList<>()

        listDrugRegimen.add(new LinkedHashMap(regimen_id: '2Op1', drug_id: '08S30WZ'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '2Op2', drug_id: '08S30WZ'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '2Op2', drug_id: '08S30ZZ'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '2Op3', drug_id: '08S29'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '2Op3', drug_id: '08S30Y'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '2Op3', drug_id: '08S30Y'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '2Op3', drug_id: '08S30Y'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '2Op3', drug_id: '08S30YX'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3op1', drug_id: '08S29'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3op1', drug_id: '08S30Y'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3op1', drug_id: '08S30Y'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3op1', drug_id: '08S30Y'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3op1', drug_id: '08S30YX'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3op1', drug_id: '08S30ZZ'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3L_3TC', drug_id: '08S01ZY'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3L_3TC', drug_id: '08S13'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3L_3TC', drug_id: '08S29'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3L_3TC', drug_id: '08S30Y'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3L_3TC', drug_id: '08S30Y'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3L_3TC', drug_id: '08S30Y'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3L_3TC', drug_id: '08S30YX'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3L_3TC', drug_id: '08S30ZZ'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '2alt2', drug_id: '08S01ZY'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '2alt2', drug_id: '08S30WZ'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '1alt1', drug_id: '08S01ZY'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '1alt1', drug_id: '08S30ZY'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'X7APed', drug_id: '08S01ZWi'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'X7APed', drug_id: '08S30ZXi'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'X7APed', drug_id: '08S30ZX'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'X6APed', drug_id: '08S01ZZ'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'X6APed', drug_id: '08S30ZY'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'X5A', drug_id: '08S01'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'X5A', drug_id: '08S01ZY'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'X5A', drug_id: '08S13'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'X5A', drug_id: '08S21'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'X5APed', drug_id: '08S01ZZ'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'X5APed', drug_id: '08S20'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'ABC12', drug_id: '08S01'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'ABC12', drug_id: '08S01ZY'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'ABC12', drug_id: '08S13'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'ABC12', drug_id: '08S38Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'ABC12', drug_id: '08S39Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'ABCPedCpts', drug_id: '08S01ZW'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'ABCPedCpts', drug_id: '08S01ZWi'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'ABCPedCpts', drug_id: '08S01ZZ'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'ABCPedCpts', drug_id: '08S39B'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'ABCPedGranulos', drug_id: '08S01ZW'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'ABCPedGranulos', drug_id: '08S01ZWi'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'ABCPedGranulos', drug_id: '08S01ZZ'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'ABCPedGranulos', drug_id: '08S38Y'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'ABCPedXarope', drug_id: '08S01ZW'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'ABCPedXarope', drug_id: '08S01ZWi'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'ABCPedXarope', drug_id: '08S01ZZ'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'ABCPedXarope', drug_id: '08S39Y'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'ABCPedNew', drug_id: '08S01ZZ'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'ABCPedNew', drug_id: '08S38Y'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'ABCPedNew', drug_id: '08S39'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'ABCPedNew', drug_id: '08S39B'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'ABCPedNew', drug_id: '08S39Y'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'X5C', drug_id: '08S01'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'X5C', drug_id: '08S01ZY'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'X5C', drug_id: '08S13'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'X5C', drug_id: '08S22'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'X5CPed', drug_id: '08S01ZZ'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'X5CPed', drug_id: '08S23Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'X5CPed', drug_id: '08S01ZZ'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'X5CPed', drug_id: '08S23Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '1TB2', drug_id: '08S01ZY'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '1TB2', drug_id: '08S01ZY'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '1TB2', drug_id: '08S30ZZ'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '1TB2', drug_id: '08S30ZZ'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3Lbb', drug_id: '08S01ZY'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3Lbb', drug_id: '08S29'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3Lbb', drug_id: '08S30Y'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3Lbb', drug_id: '08S30Y'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3Lbb', drug_id: '08S30Y'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3Lbb', drug_id: '08S30YX'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3Lbb', drug_id: '08S30ZZ'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'X3N1', drug_id: '08S01'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'X3N1', drug_id: '08S38Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'X3N1', drug_id: '08S39Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'X3N1', drug_id: '08S40'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'X3N', drug_id: '08S01'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'X3N', drug_id: '08S15'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'X3N', drug_id: '08S39Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'C2A2', drug_id: '08S01'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'C2A2', drug_id: '08S08'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'C2A2', drug_id: '08S39Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'C2A1', drug_id: '08S01'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'C2A1', drug_id: '08S09'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'C2A1', drug_id: '08S39Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A2C', drug_id: '08S01'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A2C', drug_id: '08S40'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A2C', drug_id: '08S41'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A2Cped', drug_id: '08S01Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A2Cped', drug_id: '08S40Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '2alt3', drug_id: '08S30WZ'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '2alt3', drug_id: '08S40'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '1alt2', drug_id: '08S30ZY'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '1alt2', drug_id: '08S40'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A2B', drug_id: '08S21'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A2B', drug_id: '08S40'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A2Bped', drug_id: '08S20'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A2Bped', drug_id: '08S40Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A2Bped', drug_id: '08S19'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A2Bped', drug_id: '08S20'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A2Bped', drug_id: '08S40Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A2F', drug_id: '08S38Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A2F', drug_id: '08S39Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A2F', drug_id: '08S40'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A2Fped Cpts', drug_id: '08S39B'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A2Fped Cpts', drug_id: '08S40Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A2Fped Granulos', drug_id: '08S38Y'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A2Fped Granulos', drug_id: '08S40Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A2Fped Xarope', drug_id: '08S39Y'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A2Fped Xarope', drug_id: '08S40Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A2Fped', drug_id: '08S38Y'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A2Fped', drug_id: '08S39'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A2Fped', drug_id: '08S39B'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A2Fped', drug_id: '08S39Y'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A2Fped', drug_id: '08S40Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A2A', drug_id: '08S22'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A2A', drug_id: '08S40'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A2A', drug_id: '08S42'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A2Aped', drug_id: '08S23Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A2Aped', drug_id: '08S40Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A2Aped', drug_id: '08S42B'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '1TB3', drug_id: '08S30ZZ'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '1TB3', drug_id: '08S40'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3Lb', drug_id: '08S29'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3Lb', drug_id: '08S29Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3Lb', drug_id: '08S30Y'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3Lb', drug_id: '08S30Y'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3Lb', drug_id: '08S30Y'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3Lb', drug_id: '08S30ZZ'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3Lb', drug_id: '08S40'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3La.', drug_id: '08S29'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3La.', drug_id: '08S30YX'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3La.', drug_id: '08S30ZZ'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3La.', drug_id: '08S40'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'B1C', drug_id: '08S08'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'B1C', drug_id: '08S15'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'B1C', drug_id: '08S39Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A3M', drug_id: '08S09'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A3M', drug_id: '08S15'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A3M', drug_id: '08S21'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'B1D', drug_id: '08S09'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'B1D', drug_id: '08S15'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'B1D', drug_id: '08S39Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A1E', drug_id: '08S01'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A1E', drug_id: '08S32'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'dDI123', drug_id: '08S09'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'dDI123', drug_id: '08S32'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'dDI123', drug_id: '08S39Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A1C', drug_id: '08S21'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A1C', drug_id: '08S32'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A1A', drug_id: '08S22'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A1A', drug_id: '08S32'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A1A', drug_id: '08S36'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A1Eped', drug_id: '08S01Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A1Eped', drug_id: '08S32Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'D4T124', drug_id: '08S32Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'D4T124', drug_id: '08S39'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'D4T124', drug_id: '08S39B'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'D4T124', drug_id: '08S39Y'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A1Aped', drug_id: '08S23Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A1Aped', drug_id: '08S32Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A1Aped', drug_id: '08S34B'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A1O', drug_id: '08S01'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A1O', drug_id: '08S32'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A1O', drug_id: '08S39Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A1M', drug_id: '08S32'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A1M', drug_id: '08S39Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A1P', drug_id: '08S29'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A1P', drug_id: '08S30'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A1P', drug_id: '08S32'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A3C', drug_id: '08S09'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A3C', drug_id: '08S13'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A3C', drug_id: '08S21'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A3A', drug_id: '08S08'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A3A', drug_id: '08S13'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A3A', drug_id: '08S22'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A3D', drug_id: '08S09'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A3D', drug_id: '08S13'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A3D', drug_id: '08S21'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A3B', drug_id: '08S09'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A3B', drug_id: '08S13'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A3B', drug_id: '08S22'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'DUMMY TARV', drug_id: '08S02'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'DUMMY TARV', drug_id: '08S02Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'DUMMY TARV', drug_id: '08S03Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'DUMMY TARV', drug_id: '08S04'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'DUMMY TARV', drug_id: '08S05'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'DUMMY TARV', drug_id: '08S06'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'DUMMY TARV', drug_id: '08S07'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'DUMMY TARV', drug_id: '08S10'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'DUMMY TARV', drug_id: '08S10Y'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'DUMMY TARV', drug_id: '08S10Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'DUMMY TARV', drug_id: '08S11'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'DUMMY TARV', drug_id: '08S12'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'DUMMY TARV', drug_id: '08S14'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'DUMMY TARV', drug_id: '08S24'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'DUMMY TARV', drug_id: '08S25'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'DUMMY TARV', drug_id: '08S26'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'DUMMY TARV', drug_id: '08S30WZ'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'DUMMY TARV', drug_id: '08S31'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'DUMMY TARV', drug_id: '08S33'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'DUMMY TARV', drug_id: '08S38'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'PTV Pediatri', drug_id: '08S17Y'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'PTV Pediatri', drug_id: '08S23'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'RG TARV', drug_id: '08S17'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'RG TARV', drug_id: '08S23'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'C8A', drug_id: '08S01'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'C8A', drug_id: '08S08'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'C8A', drug_id: '08S29'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'C8A', drug_id: '08S30'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '2alt1', drug_id: '08S18Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '2alt1', drug_id: '08S30WZ'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '1aLTLD', drug_id: '08S18W'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '1aLTLD', drug_id: '08S18WI'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '1aLTLD', drug_id: '08S18WII'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A4A', drug_id: '08S13'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A4A', drug_id: '08S18'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A4A', drug_id: '08S18X'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A4A', drug_id: '08S18XI'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A4A', drug_id: '08S18XII'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A4A', drug_id: '08S18Y'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'A4A', drug_id: '08S21'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'C7A', drug_id: '08S13'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'C7A', drug_id: '08S18'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'C7A', drug_id: '08S18Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'C7A', drug_id: '08S38Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'C7A', drug_id: '08S39Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'TDF12', drug_id: '08S13'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'TDF12', drug_id: '08S18'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'TDF12', drug_id: '08S18Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'TDF12', drug_id: '08S22'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '1TB1', drug_id: '08S18Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '1TB1', drug_id: '08S30ZZ'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3La', drug_id: '08S18Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3La', drug_id: '08S29'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3La', drug_id: '08S30Y'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3La', drug_id: '08S30Y'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3La', drug_id: '08S30Y'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3La', drug_id: '08S30YX'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3La', drug_id: '08S30ZZ'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3La.', drug_id: '08S18Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3La.', drug_id: '08S29'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3La.', drug_id: '08S29Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3La.', drug_id: '08S30Y'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3La.', drug_id: '08S30Y'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3La.', drug_id: '08S30Y'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3La.', drug_id: '08S30Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3La.', drug_id: '08S30Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: '3La.', drug_id: '08S30ZZ'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'C4A', drug_id: '08S01'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'C4A', drug_id: '08S13'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'C4A', drug_id: '08S18'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'C4A', drug_id: '08S18Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'C4A', drug_id: '08S39Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'C6A', drug_id: '08S38Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'C1A', drug_id: '08S18'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'C1A', drug_id: '08S39Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'C1A', drug_id: '08S40'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'C3A1', drug_id: '08S18'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'C3A1', drug_id: '08S29'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'C3A1', drug_id: '08S29Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'C3A1', drug_id: '08S30'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'C3A1', drug_id: '08S40'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'TDF13', drug_id: '08S15'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'TDF13', drug_id: '08S18'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'TDF13', drug_id: '08S38Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'TDF13', drug_id: '08S39Z'))
        listDrugRegimen.add(new LinkedHashMap(regimen_id: 'PreEP', drug_id: '08S31'))

        return listDrugRegimen

    }

}
