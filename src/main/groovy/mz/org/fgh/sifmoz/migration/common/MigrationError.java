package mz.org.fgh.sifmoz.migration.common;

public enum MigrationError {
    CLINIC_NOT_FOUND("E001", "A database error has occurred."),
    PROVINCE_NOT_FOUND("E002", "This user already exists.");

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
