package de.app.fivegla.integration.farm21.job;

import de.app.fivegla.integration.farm21.SensorIntegrationService;
import de.app.fivegla.integration.fiware.FiwareIntegrationServiceWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Scheduled data import from Farm21 API.
 */
@Slf4j
@Service
public class ScheduledSensorImport {

    private final SensorIntegrationService soilScoutSensorIntegrationService;
    private final FiwareIntegrationServiceWrapper fiwareIntegrationServiceWrapper;

    public ScheduledSensorImport(SensorIntegrationService soilScoutSensorIntegrationService,
                                 FiwareIntegrationServiceWrapper fiwareIntegrationServiceWrapper) {
        this.soilScoutSensorIntegrationService = soilScoutSensorIntegrationService;
        this.fiwareIntegrationServiceWrapper = fiwareIntegrationServiceWrapper;
    }

    /**
     * Run scheduled data import.
     */
    @Scheduled(cron = "${app.scheduled.sensor-import.cron}}")
    public void run() {
        log.info("Running scheduled sensor import from Farm21 API");
        var sensors = soilScoutSensorIntegrationService.findAll();
        log.info("Found {} sensors", sensors.size());
        sensors.forEach(fiwareIntegrationServiceWrapper::persist);
    }

}
