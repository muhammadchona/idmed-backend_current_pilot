package mz.org.fgh.sifmoz.backend.Task;

import mz.org.fgh.sifmoz.backend.healthInformationSystem.ISystemConfigsService;
import mz.org.fgh.sifmoz.backend.healthInformationSystem.SystemConfigs;
import mz.org.fgh.sifmoz.backend.healthInformationSystem.SystemConfigsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.*;

public abstract class SynchronizerTask {

    @Autowired
    ISystemConfigsService systemConfigsService;

    protected boolean isProvincialOrUs() {

        SystemConfigs systemConfig = systemConfigsService.getByKey("instalation_type");

        if (systemConfig.getValue().equals("PROVINCIAL")) {
            return true;
        } else {
             return false;
        }
    }

    protected String getUsOrProvince() {
        SystemConfigs systemConfig = systemConfigsService.getByKey("instalation_type");
        return systemConfig.getDescription();
    }
}
