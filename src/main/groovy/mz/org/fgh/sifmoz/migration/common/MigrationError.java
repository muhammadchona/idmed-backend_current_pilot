package mz.org.fgh.sifmoz.migration.common;

public enum MigrationError {
    CLINIC_NOT_FOUND("E001", "A clinica com code: {0} no IDART, nao existe no IDMED"),
    PROVINCE_NOT_FOUND("E002", "A Provincia com description: {0} do IDART, nao existe no IDMED"),
    PATIENT_NOT_FOUND("E003", "O paciente com patient_id: {0} do IDART, nao existe no IDMED"),

    IDENTIFIER_TYPE_NOT_FOUND("E004", "O tipo de Identificador com o code: {0} do IDART, nao existe no IDMED");

    private final String code;
    private final String description;

    private MigrationError(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code + ": " + description;
    }
}
