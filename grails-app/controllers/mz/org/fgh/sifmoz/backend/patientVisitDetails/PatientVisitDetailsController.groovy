package mz.org.fgh.sifmoz.backend.patientVisitDetails

import grails.converters.JSON
import grails.rest.RestfulController
import grails.validation.ValidationException
import mz.org.fgh.sifmoz.backend.packagedDrug.PackagedDrug
import mz.org.fgh.sifmoz.backend.packagedDrug.PackagedDrugStock
import mz.org.fgh.sifmoz.backend.packaging.IPackService
import mz.org.fgh.sifmoz.backend.packaging.Pack
import mz.org.fgh.sifmoz.backend.patientVisit.IPatientVisitService
import mz.org.fgh.sifmoz.backend.patientVisit.PatientVisit
import mz.org.fgh.sifmoz.backend.prescription.IPrescriptionService
import mz.org.fgh.sifmoz.backend.prescription.Prescription
import mz.org.fgh.sifmoz.backend.stock.IStockService
import mz.org.fgh.sifmoz.backend.stock.Stock
import mz.org.fgh.sifmoz.backend.utilities.JSONSerializer

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.Transactional

class PatientVisitDetailsController extends RestfulController{

    IPatientVisitDetailsService patientVisitDetailsService
    IPatientVisitService patientVisitService
    IPrescriptionService prescriptionService
    IPackService packService
    IStockService stockService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    PatientVisitDetailsController() {
        super(PatientVisitDetails)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        render JSONSerializer.setObjectListJsonResponse(patientVisitDetailsService.list(params)) as JSON
    }

    def show(String id) {
        render JSONSerializer.setJsonObjectResponse(patientVisitDetailsService.get(id)) as JSON
    }

    @Transactional
    def save() {
        PatientVisitDetails patientVisitDetails = new PatientVisitDetails()
        def objectJSON = request.JSON
        patientVisitDetails = objectJSON as PatientVisitDetails

        patientVisitDetails.beforeInsert()
        patientVisitDetails.validate()

        if(objectJSON.id){
            patientVisitDetails.id = UUID.fromString(objectJSON.id)
        }
        if (patientVisitDetails.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond patientVisitDetails.errors
            return
        }

        try {
            determinePrescriptionPatientType(patientVisitDetails)
            patientVisitDetailsService.save(patientVisitDetails)
        } catch (ValidationException e) {
            respond patientVisitDetails.errors
            return
        }

        respond patientVisitDetails, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(PatientVisitDetails patientVisitDetails) {
        if (patientVisitDetails == null) {
            render status: NOT_FOUND
            return
        }
        if (patientVisitDetails.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond patientVisitDetails.errors
            return
        }

        try {
            patientVisitDetailsService.save(patientVisitDetails)
        } catch (ValidationException e) {
            respond patientVisitDetails.errors
            return
        }

        respond patientVisitDetails, [status: OK, view:"show"]
    }

    @Transactional
    def delete(String id) {
        PatientVisitDetails patientVisitDetail = PatientVisitDetails.findById(id)
        PatientVisit patientVisit = PatientVisit.findById(patientVisitDetail.patientVisit.id)
        if (patientVisit.patientVisitDetails.size() == 1) {
            patientVisitService.delete(patientVisit.id)
            patientVisit.patientVisitDetails.each {item ->
                Pack pack = Pack.findById(item.packId)
                this.restoreStock(pack)
                packService.delete(item.packId)
                prescriptionService.delete(item.prescriptionId)
            }
            render status: NO_CONTENT
            return
        }
        else {
            patientVisitDetail.each {item ->
                packService.delete(item.packId)
                this.restoreStock(pack)
                prescriptionService.delete(item.prescriptionId)
            }
            patientVisit.patientVisitDetails.remove(patientVisitDetail)
            if (id == null || patientVisitDetailsService.delete(id) == null) {
                render status: NOT_FOUND
                return
            }
        }
        render status: NO_CONTENT
    }

    def getAllLastVisitOfClinic(String clinicId, int offset, int max) {
        render JSONSerializer.setObjectListJsonResponse(patientVisitDetailsService.getAllLastVisitOfClinic(clinicId, offset, max)) as JSON
    }

    def getAllByClinicId(String clinicId, int offset, int max) {
        render JSONSerializer.setObjectListJsonResponse(patientVisitDetailsService.getAllByClinicId(clinicId, offset, max)) as JSON
    }

    def getAllByEpisodeId(String episodeId, int offset, int max) {
        render JSONSerializer.setObjectListJsonResponse(patientVisitDetailsService.getAllByEpisodeId(episodeId, offset, max)) as JSON
    }

    def getAllofPrecription(String prescriptionId) {
        render JSONSerializer.setObjectListJsonResponse(PatientVisitDetails.findAllByPrescription(Prescription.findById(prescriptionId))) as JSON
    }

    def getLastByEpisodeId(String episodeId) {
        PatientVisitDetails pvd = patientVisitDetailsService.getLastByEpisodeId(episodeId)
        if (pvd != null) {
            render JSONSerializer.setJsonObjectResponse(pvd) as JSON
        } else render status: NO_CONTENT
    }

    void determinePrescriptionPatientType(PatientVisitDetails patientVisitDetails) {
        PatientVisit patientVisit = patientVisitService.getLastVisitOfPatient(patientVisitDetails.getPatientVisit().getPatient().id)

        if (patientVisitDetails.episode.startStopReason.isManutencao() || patientVisitDetails.episode.startStopReason.isTransferido()) {
            patientVisitDetails.prescription.setPatientType(patientVisitDetails.episode.startStopReason.code)
        }  else if (patientVisitDetails.prescription.patientType == "Alterar") {
            patientVisitDetails.prescription.setPatientType(Prescription.PATIENT_TYPE_ALTERACAO)
        } else if (patientVisitDetails.episode.startStopReason.isNew() && patientVisit == null) {
            patientVisitDetails.prescription.setPatientType(Prescription.PATIENT_TYPE_NOVO)
        } else if (patientVisitDetails.episode.startStopReason.isNew() && patientVisit != null) {
            patientVisitDetails.prescription.setPatientType(Prescription.PATIENT_TYPE_MANUTENCAO)
        } else {
            patientVisitDetails.prescription.setPatientType(Prescription.PATIENT_TYPE_MANUTENCAO)
        }
        prescriptionService.save(patientVisitDetails.prescription)
    }

    def mySave(PatientVisitDetails patientVisit) {
        this.save(patientVisit)
        render patientVisit
    }

    void restoreStock(Pack pack) {
        for (PackagedDrug packagedDrug : pack.packagedDrugs) {
            List<PackagedDrugStock> packagedDrugStocks = PackagedDrugStock.findAllByPackagedDrug(packagedDrug)
            for (PackagedDrugStock packagedDrugStock : packagedDrugStocks) {
                Stock stock = Stock.findById(packagedDrugStock.stock.id)
                stock.stockMoviment = packagedDrugStock.quantitySupplied + stock.stockMoviment
                stockService.save(stock)
            }
        }
    }
}
