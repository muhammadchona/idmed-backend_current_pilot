package mz.org.fgh.sifmoz.backend.migrationLog

import mz.org.fgh.sifmoz.backend.base.BaseEntity

import java.sql.Timestamp

class MigrationLog extends BaseEntity{

    private String id;
    private String errorCode;
    private String errorDescription;
    private String sourceId;
    private String sourceEntity;
    private Date creationDate;

    static constraints = {
        errorCode(nullable: false, blank: false)
        errorDescription(nullable: false)
        sourceId(nullable: false)
        sourceEntity(nullable: false)
        creationDate(nullable: false)
    }

    MigrationLog(String errorCode, String errorDescription, String sourceId, String sourceEntity) {
        this.errorCode = errorCode
        this.errorDescription = errorDescription
        this.sourceId = sourceId
        this.sourceEntity = sourceEntity
        this.creationDate=System.currentTimeMillis()
    }
    static mapping = {
        id generator: "uuid"
    }

    String getId() {
        return id
    }

    void setId(String id) {
        this.id = id
    }

    String getErrorCode() {
        return errorCode
    }

    void setErrorCode(String errorCode) {
        this.errorCode = errorCode
    }

    String getErrorDescription() {
        return errorDescription
    }

    void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription
    }

    String getSourceId() {
        return sourceId
    }

    void setSourceId(String sourceId) {
        this.sourceId = sourceId
    }

    String getSourceEntity() {
        return sourceEntity
    }

    void setSourceEntity(String sourceEntity) {
        this.sourceEntity = sourceEntity
    }

    Date getCreationDate() {
        return creationDate
    }

    void setCreationDate(Date creationDate) {
        this.creationDate = creationDate
    }
}
