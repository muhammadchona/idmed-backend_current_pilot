package mz.org.fgh.sifmoz.backend.task;

import mz.org.fgh.sifmoz.backend.healthInformationSystem.ISystemConfigsService;
import mz.org.fgh.sifmoz.backend.healthInformationSystem.SystemConfigs;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class SynchronizerTask implements ISynchronizerTask {

    @Autowired
    ISystemConfigsService systemConfigsService;

    SystemConfigs instalationConfig;

    public SynchronizerTask() {
        SystemConfigs.withTransaction {
            instalationConfig = SystemConfigs.findByKey("instalation_type");
        }
    }

    protected boolean isProvincial() {
        if (instalationConfig.getValue().equals("PROVINCIAL")) {
            return true;
        } else {
             return false;
        }
    }

    protected String getUsOrProvince() {
        return instalationConfig.getDescription();
    }
}
