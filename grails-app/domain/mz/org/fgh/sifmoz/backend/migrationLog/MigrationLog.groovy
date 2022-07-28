package mz.org.fgh.sifmoz.backend.migrationLog

import mz.org.fgh.sifmoz.backend.base.BaseEntity

import java.sql.Timestamp

class MigrationLog extends BaseEntity{

    String id;
    String errorCode;
    String errorDescription;
    int sourceId;
    String sourceEntity;
    String iDMEDId;
    String iDMEDEntity;
    Date creationDate;

    static constraints = {
        iDMEDId nullable: true
        iDMEDEntity nullable: true
        creationDate nullable: true
    }

    MigrationLog() {
    }

    MigrationLog(String errorCode, String errorDescription, int sourceId, String sourceEntity) {
        this.errorCode = errorCode
        this.errorDescription = errorDescription
        this.sourceId = sourceId
        this.sourceEntity = sourceEntity
    }

    MigrationLog(String errorCode, String errorDescription, int sourceId, String sourceEntity, String iDMEDId, String iDMEDEntity) {
        this.errorCode = errorCode
        this.errorDescription = errorDescription
        this.sourceId = sourceId
        this.sourceEntity = sourceEntity
        this.iDMEDId = iDMEDId
    }
    static mapping = {
        id generator: "uuid"
    }

    @Override
    String getId() {
        return this.id
    }

}
