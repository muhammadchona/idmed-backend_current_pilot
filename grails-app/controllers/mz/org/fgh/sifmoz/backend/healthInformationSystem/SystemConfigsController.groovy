package mz.org.fgh.sifmoz.backend.healthInformationSystem

import grails.validation.ValidationException
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.clinicSector.ClinicSector
import mz.org.fgh.sifmoz.backend.clinicSectorType.ClinicSectorType

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class SystemConfigsController {

    ISystemConfigsService systemConfigsService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond systemConfigsService.list(params), model:[systemConfigsCount: systemConfigsService.count()]
    }

    def show(Long id) {
        respond systemConfigsService.get(id)
    }

    @Transactional
    def save(SystemConfigs systemConfigs) {
        systemConfigs.beforeInsert()
        if (systemConfigs == null) {
            render status: NOT_FOUND
            return
        }
        if (systemConfigs.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond systemConfigs.errors
            return
        }

        try {
            systemConfigsService.save(systemConfigs)
        } catch (ValidationException e) {
            respond systemConfigs.errors
            return
        }
        updateMainClinic(systemConfigs)
        respond systemConfigs, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(SystemConfigs systemConfigs) {
        if (systemConfigs == null) {
            render status: NOT_FOUND
            return
        }
        if (systemConfigs.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond systemConfigs.errors
            return
        }

        try {
            systemConfigsService.save(systemConfigs)
        } catch (ValidationException e) {
            respond systemConfigs.errors
            return
        }

        respond systemConfigs, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || systemConfigsService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }

    def updateMainClinic(SystemConfigs systemConfigs) {

        if(systemConfigs.value.equalsIgnoreCase('LOCAL') && systemConfigs.key.equalsIgnoreCase('INSTALATION_TYPE')){
            Clinic mainClinic = Clinic.findById(systemConfigs.description)

            if(mainClinic){
                mainClinic.setMainClinic(true)
                mainClinic.save(flush: true, insert: true, failOnError: true)
                initClinicSector()
            }
        }
    }

    void initClinicSector() {
        for (clinicSectorObject in listClinicSector()) {
            if (!ClinicSector.findById(clinicSectorObject.id)) {
                ClinicSector clinicSector = new ClinicSector()
                clinicSector.id = clinicSectorObject.id
                clinicSector.code = clinicSectorObject.code
                clinicSector.description = clinicSectorObject.description
                clinicSector.active = clinicSectorObject.active
                clinicSector.uuid = clinicSectorObject.uuid
                clinicSector.clinicSectorType = ClinicSectorType.findById(clinicSectorObject.clinicSectorType_id)
                clinicSector.clinic = Clinic.findByMainClinic(true)
                clinicSector.save(flush: true, failOnError: true)
            }
        }
    }
    List<Object> listClinicSector() {
        List<Object> clinicSectorList = new ArrayList<>()
        clinicSectorList.add(new LinkedHashMap(id: '8a8a823b81900fee0181901608880000', code: 'CPN', description: 'Consulta Pre-Natal', clinicSectorType_id: '8a8a823b81c7fa9d0181c801ab120000', uuid: '8a8a823b81900fee0181901608890000', active: 'true'))
        clinicSectorList.add(new LinkedHashMap(id: '8a8a823b81900fee018190163i0c0001', code: 'TB', description: 'Tuberculose',  clinicSectorType_id: '8a8a823b81c7fa9d0181c801ab120000', uuid: '8a8a823b81900fee018190163e0c0001', active: 'true'))
        clinicSectorList.add(new LinkedHashMap(id: '8a8a823b81900fee0181901074b20002', code: 'PREP', description: 'Profilaxia Pré-Exposição',  clinicSectorType_id: '8a8a823b81c7fa9d0181c801ab120000', uuid: '8a8a823b81900fee0181901674b20002', active: 'true'))
        clinicSectorList.add(new LinkedHashMap(id: '8a8a823b81900fee0181902674b20003', code: 'SAAJ', description: 'Serviços Amigos dos Adolescentes e Jovens',  clinicSectorType_id: '8a8a823b81c7fa9d0181c801ab120000', uuid: '8a8a823b81900fee0181901674b20003', active: 'true'))
        clinicSectorList.add(new LinkedHashMap(id: '8a8a823b81900fee0181902674b20005', code: 'CCR', description: 'Consulta Criança em Risco',  clinicSectorType_id: '8a8a823b81c7fa9d0181c801ab120000', uuid: '8a8a823b81900fee0181901674b20005', active: 'true'))
        clinicSectorList.add(new LinkedHashMap(id: '8a8a823b81900fee0181902674b20004', code: 'NORMAL', description: 'Atendimento Geral',  clinicSectorType_id: '8a8a823b81c7fa9d0181c802d7ec0006', uuid: '8a8a823b81900fee0181901674b20004', active: 'true'))

        return clinicSectorList
    }
}
