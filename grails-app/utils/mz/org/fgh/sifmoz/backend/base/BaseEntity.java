package mz.org.fgh.sifmoz.backend.base;

import mz.org.fgh.sifmoz.backend.migration.base.record.MigratedRecord;

public abstract class BaseEntity implements MigratedRecord {

    public boolean isValidated() {
        return false;
    }

    @Override
    public String getEntity() {
        return this.getClass().getName();
    }
}
