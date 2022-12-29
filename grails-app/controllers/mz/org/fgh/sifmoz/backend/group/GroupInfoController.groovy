package mz.org.fgh.sifmoz.backend.group

import grails.converters.JSON
import grails.rest.RestfulController
import grails.validation.ValidationException
import mz.org.fgh.sifmoz.backend.episode.Episode
import mz.org.fgh.sifmoz.backend.episode.IEpisodeService
import mz.org.fgh.sifmoz.backend.groupMember.GroupMember
import mz.org.fgh.sifmoz.backend.groupMember.GroupMemberService
import mz.org.fgh.sifmoz.backend.patient.IPatientService
import mz.org.fgh.sifmoz.backend.patient.Patient
import mz.org.fgh.sifmoz.backend.utilities.JSONSerializer

import static org.springframework.http.HttpStatus.ACCEPTED
import static org.springframework.http.HttpStatus.CONFLICT
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.Transactional

class GroupInfoController extends RestfulController{

    IGroupService groupService
    IEpisodeService episodeService
    GroupMemberService groupMemberService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    GroupInfoController() {
        super(GroupInfo)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        render JSONSerializer.setObjectListJsonResponse(groupService.list(params)) as JSON
    }

    def show(String id) {
        render JSONSerializer.setJsonObjectResponse(groupService.get(id)) as JSON
    }

    @Transactional
    def save() {
        GroupInfo group = new GroupInfo()
        def objectJSON = request.JSON
        group = objectJSON as GroupInfo

        group.validate()

        if(objectJSON.id){
            group.id = UUID.fromString(objectJSON.id)
            group.members.eachWithIndex { item, index ->
                item.id = UUID.fromString(objectJSON.members[index].id)
            }
        }

        if (group.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond group.errors
            return
        }
        group.beforeInsert()
        try {
            HashSet<GroupMember> memberList = group.members
            group.members = null
            groupService.save(group)
            for (GroupMember member : memberList) {
                member.group = group
                groupMemberService.save(member)
            }
        } catch (ValidationException e) {
            transactionStatus.setRollbackOnly()
            respond group.errors
            return
        }

        respond group, [status: CREATED, view:"show"]
    }

    @Transactional
    def update() {

        GroupInfo group
        def objectJSON = request.JSON

        if(objectJSON.id){
            group = GroupInfo.get(objectJSON.id)
            if (group == null) {
                render status: NOT_FOUND
                return
            }
            group.properties = objectJSON
            group.members.eachWithIndex { item, index ->
                item.id = UUID.fromString(objectJSON.members[index].id)
            }
        }

        if (group.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond group.errors
            return
        }

        try {
            group.members.each { item ->
                groupMemberService.save(item)
            }

            groupService.save(group)
        } catch (ValidationException e) {
            respond group.errors
            return
        }

        respond group, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || groupService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }

    def getByClinicId(String clinicId, int offset, int max) {
        render JSONSerializer.setObjectListJsonResponse(groupService.getAllByClinicId(clinicId, offset, max)) as JSON
        //respond patientService.getAllByClinicId(clinicId, offset, max)
    }

    def validadePatient(String patientId, String serviceCode) {
        Patient patient = Patient.findById(patientId)
        patient.setGroups(groupService.getAllActiveOfPatientOnService(patient, serviceCode))
        String msg = "Accepted"

        Episode lastPatientEpisode = episodeService.getLastEpisodeByIdentifier(patient, serviceCode)

        if (lastPatientEpisode == null) {
            msg = "O paciente selecionado não possui episódios."
            render msg
        } else if (!lastPatientEpisode.startStopReason.isStartReason) {
            msg = "O Último episódio do paciente não é de inicio."
            render msg
        }else
        if (patient.isActiveOnGroup(serviceCode)) {
            msg = "O paciente selecionado ja se encontra associado a um grupo activo do serviço [" + serviceCode + "]"
            render msg
        }
        render msg
    }
}
