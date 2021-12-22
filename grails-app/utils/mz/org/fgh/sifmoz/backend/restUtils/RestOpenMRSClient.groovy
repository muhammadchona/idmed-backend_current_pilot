package mz.org.fgh.sifmoz.backend.restUtils

import mz.org.fgh.sifmoz.backend.interoperabilityAttribute.InteroperabilityAttribute
import mz.org.fgh.sifmoz.backend.packagedDrug.PackagedDrug
import mz.org.fgh.sifmoz.backend.packaging.Pack
import mz.org.fgh.sifmoz.backend.patient.Patient
import mz.org.fgh.sifmoz.backend.prescription.Prescription
import mz.org.fgh.sifmoz.backend.prescriptionDetail.PrescriptionDetail
import mz.org.fgh.sifmoz.backend.utilities.Utilities

import java.nio.charset.StandardCharsets
import java.sql.Timestamp

import static mz.org.fgh.sifmoz.backend.convertDateUtils.ConvertDateUtils.formatToYYYYMMDD
import static mz.org.fgh.sifmoz.backend.convertDateUtils.ConvertDateUtils.formatToYYYYMMDD

class RestOpenMRSClient {

    RestOpenMRSClient() {

    }

    String createOpenMRSFILA(Pack pack, Patient patient) {

        String inputAddPerson = "{}"
        String customizedDosage = ""
        String obsGroupsJson = null
        String dispenseMod = ""
        int packSize = 0
        Patient.withNewSession {
            try {
                List<String> obsGroups = new ArrayList<>()
                List<InteroperabilityAttribute> interoperabilityAttributes = Patient.get(patient.id).his.interoperabilityAttributes as List<InteroperabilityAttribute>
                String filaUuid = interoperabilityAttributes.find { it.interoperabilityType.code == "FORM_FILA_UUID" }.value
                String dispenseModeUuid = interoperabilityAttributes.find { it.interoperabilityType.code == "DISPENSE_MODE_CONCEPT_UUID" }.value
                String encounterType = interoperabilityAttributes.find { it.interoperabilityType.code == "FILA_ENCOUNTER_TYPE_CONCEPT_UUID" }.value
                String providerUuid = interoperabilityAttributes.find { it.interoperabilityType.code == "UNIVERSAL_PROVIDER_UUID" }.value
                String regimeUuid = interoperabilityAttributes.find { it.interoperabilityType.code == "FILA_REGIMEN_CONCEPT_UUID" }.value
                String dispensedAmountUuid = interoperabilityAttributes.find { it.interoperabilityType.code == "DISPENSED_AMOUNT_CONCEPT" }.value
                String dosageUuid = interoperabilityAttributes.find { it.interoperabilityType.code == "DOSAGE_CONCEPT_UUID" }.value
                String returnVisitUuid = interoperabilityAttributes.find { it.interoperabilityType.code == "FILA_NEXT_VISIT_CONCEPT_UUID" }.value
                String strRegimenAnswerUuid = PrescriptionDetail.findByPrescription(Prescription.findByPatientVisitDetails(pack.patientVisitDetails)).therapeuticRegimen.openmrsUuid

                for (PackagedDrug pd : pack.packagedDrugs) {
                    String formulationString = "{\"" +
                            "person\":\"" + patient.hisUuid + "\"," +
                            "\"obsDatetime\":\"" + formatToYYYYMMDD(pack.pickupDate) + "\"," +
                            "\"concept\":\"7956cd89-2ef6-4d25-90f9-f8940507eee8\"," +
                            "\"value\":\"" + pd.drug.uuidOpenmrs + "\"," +
                            "\"comment\":\"IDART\"" +
                            "}"

                    String quantityString = "{\"" +
                            "person\":\"" + patient.hisUuid + "\"," +
                            "\"obsDatetime\":\"" + formatToYYYYMMDD(pack.pickupDate) + "\"," +
                            "\"concept\":\"e1de2ca0-1d5f-11e0-b929-000c29ad1d07\"," +
                            "\"value\":\"" + pd.quantitySupplied + "\"," +
                            "\"comment\":\"IDART\"" +
                            "}"

                    String dosageString = "{\"" +
                            "person\":\"" + patient.hisUuid + "\"," +
                            "\"obsDatetime\":\"" + formatToYYYYMMDD(pack.pickupDate) + "\"," +
                            "\"concept\":\"e1de28ae-1d5f-11e0-b929-000c29ad1d07\"," +
                            "\"value\":\"" + pd.getDrug().getDefaultTimes() + "\"," +
                            "\"comment\":\"IDART\"" +
                            "}"

                    String obsGroup = "{\"" +
                            "person\":\"" + patient.hisUuid + "\"," +
                            "\"obsDatetime\":\"" + formatToYYYYMMDD(pack.pickupDate) + "\"," +
                            "\"concept\":\"5ad593a4-bea2-4eef-ac88-11654e79d9da\"," +
                            "\"comment\":\"IDART\"," +
                            "\"groupMembers\": [" + formulationString + "," + quantityString + "," + dosageString + "]" +
                            "}"

                    obsGroups.add(obsGroup)
                    //posologia
                    customizedDosage = "Tomar "
                    customizedDosage.concat(String.valueOf(pd.getDrug().getDefaultTreatment()))
                    customizedDosage.concat(" ")
                    customizedDosage.concat(pd.getDrug().getForm().getDescription())
                    customizedDosage.concat(" ")
                    customizedDosage.concat(String.valueOf(pd.getDrug().getDefaultTimes()))
                    customizedDosage.concat(" ")
                    customizedDosage.concat(pd.getDrug().getDefaultPeriodTreatment())

                    //Dispensed amount
                    packSize = packSize + pd.getQuantitySupplied()
                }

                for (String group : obsGroups) {
                    if (!Utilities.stringHasValue(obsGroupsJson))
                        obsGroupsJson = group
                    else {
                        obsGroupsJson = obsGroupsJson + "," + group
                    }
                }

                if (pack.dispenseMode.openmrsUuid) {
                    dispenseMod = "{\"person\":\""
                            .concat(patient.hisUuid + "\",")
                            .concat("\"obsDatetime\":\"" + formatToYYYYMMDD(pack.pickupDate))
                            .concat("\",\"concept\":\"" + dispenseModeUuid + "\",\"value\":\"")
                            .concat(pack.dispenseMode.openmrsUuid + "\",\"comment\":\"IDART\"},")
                }

                String buildDispenseMap = "{\"encounterDatetime\": \"" + formatToYYYYMMDD(pack.pickupDate) + "\", \"patient\": \"" + patient.hisUuid + "\", \"encounterType\": \"" + encounterType + "\", "
                        .concat("\"location\":\"" + patient.hisLocation + "\", \"form\":\"" + filaUuid + "\", \"encounterProviders\":[{\"provider\":\"" + providerUuid + "\", \"encounterRole\":\"a0b03050-c99b-11e0-9572-0800200c9a66\"}], ")
                        .concat("\"obs\":[")
                        .concat("{\"person\":\"" + patient.hisUuid + "\",\"obsDatetime\":\"" + formatToYYYYMMDD(pack.pickupDate) + "\",\"concept\":\"" + regimeUuid + "\",\"value\":\"" + strRegimenAnswerUuid + "\", \"comment\":\"IDART\"},")
                        .concat("{\"person\":\"" + patient.hisUuid + "\",\"obsDatetime\":\"" + formatToYYYYMMDD(pack.pickupDate) + "\",\"concept\":\"" + dispensedAmountUuid + "\",\"value\":\"" + packSize + "\",\"comment\":\"IDART\"},")
                        .concat("{\"person\":\"" + patient.hisUuid + "\",")
                        .concat("\"obsDatetime\":\"" + formatToYYYYMMDD(pack.pickupDate) + "\",\"concept\":\"" + dosageUuid + "\",\"value\":\"" + customizedDosage + "\",\"comment\":\"IDART\"},")
                        .concat("{\"person\":\"" + patient.hisUuid + "\",")
                        .concat("\"obsDatetime\":\"" + formatToYYYYMMDD(pack.pickupDate) + "\",\"concept\":\"" + returnVisitUuid + "\",\"value\":\"" + formatToYYYYMMDD(pack.nextPickUpDate) + "\",\"comment\":\"IDART\"},")
                        .concat(dispenseMod)
                        .concat(obsGroupsJson)
                        .concat("]")
                        .concat("}")

                inputAddPerson = new String(buildDispenseMap.getBytes(), StandardCharsets.UTF_8)
            } catch (Exception e) {
                e.printStackTrace()
            }

            return inputAddPerson
        }
    }

}
