package mz.org.fgh.sifmoz.migration.base.log;

public class AbstractMigrationLog {
    private String id;
    private String errorCode;
    private String description;

    public AbstractMigrationLog(String id, String errorCode, String description) {
        this.id = id;
        this.errorCode = errorCode;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
