package mz.org.fgh.sifmoz.backend.patient

import grails.converters.JSON
import grails.rest.RestfulController
import grails.validation.ValidationException
import groovy.json.JsonSlurper
import mz.org.fgh.sifmoz.backend.appointment.Appointment
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.clinicSector.ClinicSector
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.District
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.Localidade
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.LocalidadeService
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.PostoAdministrativo
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.Province
import mz.org.fgh.sifmoz.backend.healthInformationSystem.HealthInformationSystem
import mz.org.fgh.sifmoz.backend.interoperabilityAttribute.InteroperabilityAttribute
import mz.org.fgh.sifmoz.backend.interoperabilityType.InteroperabilityType
import mz.org.fgh.sifmoz.backend.patientAttribute.PatientAttribute
import mz.org.fgh.sifmoz.backend.patientIdentifier.PatientServiceIdentifier
import mz.org.fgh.sifmoz.backend.patientIdentifier.PatientServiceIdentifierService
import mz.org.fgh.sifmoz.backend.patientVisit.PatientVisit
import mz.org.fgh.sifmoz.backend.prescription.Prescription
import mz.org.fgh.sifmoz.backend.restUtils.RestOpenMRSClient
import mz.org.fgh.sifmoz.backend.screening.AdherenceScreening
import mz.org.fgh.sifmoz.backend.service.ClinicalService
import mz.org.fgh.sifmoz.backend.tansreference.PatientTransReference
import mz.org.fgh.sifmoz.backend.utilities.JSONSerializer
import mz.org.fgh.sifmoz.backend.report.ReportGenerator
import org.hibernate.SessionFactory
import org.springframework.orm.hibernate5.SessionFactoryUtils

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.Transactional

class PatientController extends RestfulController {

    IPatientService patientService
    LocalidadeService localidadeService
    PatientServiceIdentifierService patientServiceIdentifierService

    def SessionFactory sessionFactory

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    PatientController() {
        super(Patient)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        render JSONSerializer.setObjectListJsonResponse(patientService.list(params)) as JSON
    }

    def show(String id) {
        Patient patient = patientService.get(id)
        render JSONSerializer.setJsonObjectResponse(patient) as JSON
        //respond patientService.get(id)
    }

        @Transactional
        def save() {
            Patient patient = new Patient()
            def objectJSON = request.JSON
            patient = objectJSON as Patient
            def patientAux = objectJSON as Patient

            patient.beforeInsert()
            patient.identifiers = []
            patient.validate()
//            patient = patientAux

            if(objectJSON.id){
                patient.id = UUID.fromString(objectJSON.id)
            }

            if (patient.hasErrors()) {
                transactionStatus.setRollbackOnly()
                respond patient.errors
                return
            }

            try {
                if (patient.bairro) {
                    Localidade localidade = Localidade.findByCode(patient.bairro.code)
                    if (!localidade) {
                        patient.bairro.id = objectJSON.bairro.id
                        localidadeService.save(patient.bairro)
                    }
                }

                patientService.save(patient)


                if (!patientAux.identifiers.isEmpty()) {
                    patient.identifiers = patientAux.identifiers
                    patient.identifiers.each { item ->
                        if(item){
                            item.id = UUID.randomUUID().toString()
                            item.patient = patient
                            patientServiceIdentifierService.save(item)
                        }
                    }
                }


            } catch (ValidationException e) {
                respond patient.errors
                return
            }

            respond patient, [status: CREATED, view: "show"]
        }

        @Transactional
        def update() {

            def objectJSON = request.JSON
            Patient patient = Patient.get(objectJSON.id)
            def patientFromJSON = (parseTo(objectJSON.toString()) as Map) as Patient

            bindData(patient, patientFromJSON, [exclude: ['id', 'clinicId', 'his','hisId', 'provinceId','districtId','bairroId' ,'clinic', 'attributes', 'appointments', 'patientTransReference', 'validated','postoAdministrativoId', 'entity']])
            List<PatientServiceIdentifier> identifiersList = new ArrayList<>()

            patient.identifiers = [].withDefault { new PatientServiceIdentifier() }

            (objectJSON.identifiers as List).collect { item ->
                if (item) {
                    def identifier = PatientServiceIdentifier.get(item.id)
                    identifier.patient = patient
                    identifiersList.add(identifier)
                }
            }
            patient.identifiers = identifiersList
            if (patient == null) {
                render status: NOT_FOUND
                return
            }

            if (patient.hasErrors()) {
                transactionStatus.setRollbackOnly()
                respond patient.errors
                return
            }

            try {
                if (patient.bairro) {
                    Localidade localidade = Localidade.findByCode(patient.bairro.code)
                    if (!localidade) {
                        if (!patient.bairro.id)
                            patient.bairro.id = UUID.randomUUID().toString()
                        patient.bairro.id = patient.bairro.id
                        localidadeService.save(patient.bairro)
                    }
                }
                patientService.save(patient)
            } catch (ValidationException e) {
                respond patient.errors
                return
            }

            render JSONSerializer.setJsonLightObjectResponse(Patient.get(patient.id)) as JSON
        }

        @Transactional
        def delete(Long id) {
            if (id == null || patientService.delete(id) == null) {
                render status: NOT_FOUND
                return
            }

            render status: NO_CONTENT
        }

        def getByClinicId(String clinicId, int offset, int max) {
            List<String> toIncludeProps = new ArrayList<>()
            toIncludeProps.add("identifiers")
            toIncludeProps.add("clinic")
           // patientService.getAllByClinicId(clinicId, offset, max)
            //render JSONSerializer.setLightObjectListJsonResponse(patientService.getAllByClinicId(clinicId, offset, max), toIncludeProps) as JSON
            render JSONSerializer.setObjectListJsonResponse(patientService.getAllByClinicId(clinicId, offset, max)) as JSON
            //respond patientService.getAllByClinicId(clinicId, offset, max)
        }

    def search() {
        Patient patient = new Patient()
        def objectJSON = request.JSON
        patient = objectJSON as Patient

        List<Patient> patientList = patientService.search(patient)
        def include = ['province' , 'district','episodes']
        def childInclude = ['name', 'code']
        def exclude = []
     //   render patientList as JSON, include: include ,exclude: exclude
        render JSONSerializer.setLightObjectListJsonResponse(patientList, include ) as JSON
       // render JSONSerializer.setObjectListJsonResponse(patientList) as JSON
    }

    def searchByParam(String searchString, String clinicId) {
        List<Patient> patientList = patientService.search(searchString, clinicId)
        render JSONSerializer.setObjectListJsonResponse(patientList) as JSON
    }

    def getOpenMRSSession(String interoperabilityId, String openmrsBase64) {

        HealthInformationSystem healthInformationSystem = HealthInformationSystem.get(interoperabilityId)
        InteroperabilityType interoperabilityType = InteroperabilityType.findByCode("URL_BASE")
        InteroperabilityAttribute interoperabilityAttribute = InteroperabilityAttribute.findByHealthInformationSystemAndInteroperabilityType(healthInformationSystem, interoperabilityType)

       render RestOpenMRSClient.getResponseOpenMRSClient(openmrsBase64, null, interoperabilityAttribute.value, "session", "GET")

    }

    def getOpenMRSPatient(String interoperabilityId, String nid, String openmrsBase64) {

        HealthInformationSystem healthInformationSystem = HealthInformationSystem.get(interoperabilityId)
        InteroperabilityType interoperabilityType = InteroperabilityType.findByCode("URL_BASE")
        InteroperabilityAttribute interoperabilityAttribute = InteroperabilityAttribute.findByHealthInformationSystemAndInteroperabilityType(healthInformationSystem, interoperabilityType)

        String urlPath = "patient?q="+nid.replaceAll("-","/") + "&v=full&limit=100"

        render RestOpenMRSClient.getResponseOpenMRSClient(openmrsBase64, null, interoperabilityAttribute.value, urlPath, "GET")

    }

    def getOpenMRSPatientProgramDetails(String interoperabilityId, String uuid, String openmrsBase64) {

        HealthInformationSystem healthInformationSystem = HealthInformationSystem.get(interoperabilityId)
        InteroperabilityType interoperabilityType = InteroperabilityType.findByCode("URL_BASE")
        InteroperabilityAttribute interoperabilityAttribute = InteroperabilityAttribute.findByHealthInformationSystemAndInteroperabilityType(healthInformationSystem, interoperabilityType)

        String urlPath = "programenrollment?patient="+uuid+ "&v=default"

        render RestOpenMRSClient.getResponseOpenMRSClient(openmrsBase64, null, interoperabilityAttribute.value, urlPath, "GET")

    }

    def getPatientsInClinicSector(String clinicSectorId) {
        render JSONSerializer.setObjectListJsonResponse(patientService.getAllPatientsInClinicSector(ClinicSector.findById(clinicSectorId))) as JSON
    }

    private static def parseTo(String jsonString) {
        return new JsonSlurper().parseText(jsonString)
    }

}
