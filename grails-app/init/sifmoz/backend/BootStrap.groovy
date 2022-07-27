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
import mz.org.fgh.sifmoz.backend.prescription.SpetialPrescriptionMotive
import mz.org.fgh.sifmoz.backend.service.ClinicalService
import mz.org.fgh.sifmoz.backend.serviceattributetype.ClinicalServiceAttributeType
import mz.org.fgh.sifmoz.backend.startStopReason.StartStopReason
import mz.org.fgh.sifmoz.backend.therapeuticLine.TherapeuticLine
import mz.org.fgh.sifmoz.backend.therapeuticRegimen.TherapeuticRegimen

class BootStrap {

    def init = { servletContext ->

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

        TherapeuticRegimen.withTransaction {}

        Drug.withTransaction {}


    }

    def destroy = {
    }

    // Methods
    void initFacilityType() {
        for (facilityTypeObject in listFacilityType()) {
            if (!FacilityType.findById(facilityTypeObject.id)) {
                FacilityType facilityType = new FacilityType()
                facilityType.id = facilityTypeObject.id
                facilityType.code = facilityTypeObject.code
                facilityType.description = facilityTypeObject.description
                facilityType.save(flush: true, failOnError: false)
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
                identifierType.save(flush: true, failOnError: false)
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
                clinicSectorType.save(flush: true, failOnError: false)
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
                clinicalServiceAttributeType.save(flush: true, failOnError: false)
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
                dispenseMode.save(flush: true, failOnError: false)
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
                dispenseType.save(flush: true, failOnError: false)
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
                form.save(flush: true, failOnError: false)
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
                duration.save(flush: true, failOnError: false)
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
                startStopReason.save(flush: true, failOnError: false)
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
                groupType.save(flush: true, failOnError: false)
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
                therapeuticLine.save(flush: true, failOnError: false)
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
                episodeType.save(flush: true, failOnError: false)
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
                clinicalService.save(flush: true, failOnError: false)
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
                spetialPrescriptionMotive.save(flush: true, failOnError: false)
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
                healthInformationSystem.save(flush: true, failOnError: false)
            }
        }
    }

    void initInteroperabilityType() {
        for (interoperabilityTypeObject in listFacilityType()) {
            if (!InteroperabilityType.findById(interoperabilityTypeObject.id)) {
                InteroperabilityType interoperabilityType = new InteroperabilityType()
                interoperabilityType.id = interoperabilityTypeObject.id
                interoperabilityType.code = interoperabilityTypeObject.code
                interoperabilityType.description = interoperabilityTypeObject.description
                interoperabilityType.save(flush: true, failOnError: false)
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
                interoperabilityAttribute.save(flush: true, failOnError: false)
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
                province.save(flush: true, failOnError: false)
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
                district.save(flush: true, failOnError: false)
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
        formList.add(new LinkedHashMap(id: 'D32F4BC0-E0CC-4602-829B-BEC4EAFB0D2C', code: 'Suspensão', description: 'Suspensão'))
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
        therapeuticLineList.add(new LinkedHashMap(id: 'ff8081817cb69063017cbbagb014av0c', code: '1', uuid: '6E117555-BB10-43C9-83B4-9171A1734BB7', description: 'Primeira Linha Alternativa'))
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
        interoperabilityAttributeList.add(new LinkedHashMap(id: '34C5A7BB-E7DE-4CFB-B349-2BC36FD4FFCA', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: 'http://localhost/openmrs/ws/rest/v1/reportingrest/cohort/ba36b483-c17c-454d-9a3a-f060a933c6da', districtListinteroperabilityType: InteroperabilityType.findById('CFCA8326-0AB9-46D0-B5CE-1E277E564823')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: 'CE34FBAD-D1F1-4C8E-A823-CBCF3137A0BA', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: '2.x', districtListinteroperabilityType: InteroperabilityType.findById('0718F9D9-39A8-405A-8092-5BDDE4204B25')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: '876FA63C-8F5F-42FB-A2D5-24F1BFEDD3ED', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: '49857ace-1a92-4980-8313-1067714df151', districtListinteroperabilityType: InteroperabilityType.findById('CEBEF157-57B9-4442-A1E3-59FA92E818D6')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: 'AF6051CD-7AB6-4841-A47D-2D6608095822', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: 'e1d83e4e-1d5f-11e0-b929-000c29ad1d07', districtListinteroperabilityType: InteroperabilityType.findById('73715EBA-8BDD-4C55-8895-C01FB0D5AF57')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: 'FFB2BE0D-2191-41A1-88A4-78ACADE251D4', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: 'e1e2efd8-1d5f-11e0-b929-000c29ad1d07', districtListinteroperabilityType: InteroperabilityType.findById('EE650283-C62F-43E7-8835-A7B7A9706119')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: '9CC90263-5A21-47D9-AAC0-33FE9FB60C09', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: 'd5c15047-58f3-4eb2-9f98-af82e3531cb5', districtListinteroperabilityType: InteroperabilityType.findById('416001E8-2F6E-46D4-A448-88E3188160E5')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: '7439C298-97EE-486D-9711-D9D17BA161F9', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: '93603742-1cae-4970-9077-e2b27e46bd7e', districtListinteroperabilityType: InteroperabilityType.findById('BD2B4C58-4CEE-49D9-9D70-ED2B3B777064')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: 'F9AE9B98-CE83-4010-85E5-B9F569555054', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: 'e1e2efd8-1d5f-11e0-b929-000c29ad1d07', districtListinteroperabilityType: InteroperabilityType.findById('7C7C893F-D22F-46A7-8CE4-953A9853B6A8')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: 'A542DCF1-99AB-4DC2-AAE2-80279D34B0A9', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: '4ce83895-5c0e-4170-b0cc-d3974b54131f', districtListinteroperabilityType: InteroperabilityType.findById('FA1E03AA-EEB0-446E-89F0-BB966C9FC65C')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: '4F3C8B49-20F1-4BBB-83C8-7F217A35D505', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: '9db4ce3b-4c1c-45dd-905f-c984a052f26e', districtListinteroperabilityType: InteroperabilityType.findById('44A81300-7B59-4196-8BF6-B15CBFD11667')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: '6D5E1BBE-CC76-439C-A11A-8CD804B044AF', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: 'd5c15047-58f3-4eb2-9f98-af82e3531cb5', districtListinteroperabilityType: InteroperabilityType.findById('BCD2F582-6737-48B6-B56E-FF80EE37DD22')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: '531A21E7-2123-4B72-803B-4C6FADE0D0BD', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: '93603742-1cae-4970-9077-e2b27e46bd7e', districtListinteroperabilityType: InteroperabilityType.findById('DE6B0CA7-5481-407C-B542-D0E56EF43342')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: 'CAFC31D5-8617-47A0-A286-C63971772337', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: 'd9911494-b231-4b3c-9246-1fe5f269476c', districtListinteroperabilityType: InteroperabilityType.findById('8B0D0D86-C6C1-4327-8206-78DC4DFC6EDA')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: 'AB2F5969-E6C9-4F37-94F1-992DF8788DF2', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: 'b7c246bc-f2b6-49e5-9325-911cdca7a8b3', districtListinteroperabilityType: InteroperabilityType.findById('22AD8E9F-1A09-4794-A393-4D4C7F4676F8')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: 'A77D1618-1321-44CF-85FA-9BF1D6099C90', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: 'e1de2ca0-1d5f-11e0-b929-000c29ad1d07', districtListinteroperabilityType: InteroperabilityType.findById('A7D2F52E-8409-492B-8ECD-A7093E83224E')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: '4E165FE8-7A33-4349-B8A7-10FF204A7DBD', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: 'e1de28ae-1d5f-11e0-b929-000c29ad1d07', districtListinteroperabilityType: InteroperabilityType.findById('585F5AAC-A014-4F6A-84C3-428C9C68EFD8')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: '73E51E2B-4DEB-450B-8F0E-E355BC988D34', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: '40a9a12b-1205-4a55-bb93-caf15452bf61', districtListinteroperabilityType: InteroperabilityType.findById('DD0FCFE6-94DB-4A8B-9760-C8EEB512044A')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: 'A34A1593-2E3A-4371-8B35-BAAFF3FA058B', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: '1098AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA', districtListinteroperabilityType: InteroperabilityType.findById('3D7530B4-C5DE-4073-8A0D-4137CBA6297D')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: '82BDF33C-42FB-4226-B0CF-52D4B793049D', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: 'f53848d5-3521-4cc8-ac72-d63adef281a1', districtListinteroperabilityType: InteroperabilityType.findById('517C1996-CE00-4159-87F2-3F9DA2D24962')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: 'A4B2E452-1565-48BD-B866-2EB7DE823BA0', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: '3069be5c-cd02-4ddb-aa1f-bdd71d3dd6be', districtListinteroperabilityType: InteroperabilityType.findById('4794A14A-EF6E-4AD7-A7FD-D3E291AC2BDD')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: 'F6EFA41C-4308-4782-835C-8B2A9E2B3B61', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: 'e1d9ef28-1d5f-11e0-b929-000c29ad1d07', districtListinteroperabilityType: InteroperabilityType.findById('B361B735-26D0-4B58-9BB1-35C317778698')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: '22E9B370-EA14-4B8D-98BC-1E3ADC84F72E', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: 'e1d9f036-1d5f-11e0-b929-000c29ad1d07', districtListinteroperabilityType: InteroperabilityType.findById('05C533A6-0BC1-495C-95EF-80C0B3C063A7')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: '4FE4D425-5514-4CDA-9467-0AD33A5CA9CF', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: 'e1de1bfc-1d5f-11e0-b929-000c29ad1d07', districtListinteroperabilityType: InteroperabilityType.findById('30C65546-FACA-489F-809B-60EFCDFB5BF2')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: '4107FCCF-4B40-4B58-BFDD-0B9BF83D25BC', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: 'e1d9facc-1d5f-11e0-b929-000c29ad1d07', districtListinteroperabilityType: InteroperabilityType.findById('80B52D48-0946-4267-9535-EC01484914E8')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: '9881585A-79F3-4756-AD05-7968775BF98C', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: 'e279133c-1d5f-11e0-b929-000c29ad1d07', districtListinteroperabilityType: InteroperabilityType.findById('329B13FD-82BE-429F-ABD3-A0B72051332C')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: '5143CEA0-EAD7-4A1C-8D0D-DC1697B23AA3', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: '24bd60e2-a1c9-4159-a24f-12af15b77510', districtListinteroperabilityType: InteroperabilityType.findById('B2AAF0F5-D23B-4DD4-95BA-0ACCC0656A9C')))
        interoperabilityAttributeList.add(new LinkedHashMap(id: 'C60F7C25-7198-4605-BDD4-FF6DF3309AB2', healthInformationSystem: HealthInformationSystem.findById('ff8080817d9aa854017d9e2809b50008'), value: '7013d271-1bc2-4a50-bed6-8932044bc18f', districtListinteroperabilityType: InteroperabilityType.findById('94094FC8-99F1-4A23-8E2A-1E823E30B6D7')))

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

}
