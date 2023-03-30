package de.app.fivegla.integration.farm21.job;

import de.app.fivegla.integration.farm21.SensorDataIntegrationService;
import de.app.fivegla.integration.fiware.FiwareIntegrationServiceWrapper;
import de.app.fivegla.persistence.ApplicationDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Scheduled data import from Farm21 API.
 */
@Slf4j
@Service
public class ScheduledSensorDataImport {

    private final SensorDataIntegrationService soilScoutMeasurementIntegrationService;
    private final ApplicationDataRepository applicationDataRepository;
    private final FiwareIntegrationServiceWrapper fiwareIntegrationServiceWrapper;

    public ScheduledSensorDataImport(SensorDataIntegrationService sensorDataIntegrationService,
                                     ApplicationDataRepository applicationDataRepository,
                                     FiwareIntegrationServiceWrapper fiwareIntegrationServiceWrapper) {
        this.soilScoutMeasurementIntegrationService = sensorDataIntegrationService;
        this.applicationDataRepository = applicationDataRepository;
        this.fiwareIntegrationServiceWrapper = fiwareIntegrationServiceWrapper;
    }

    /**
     * Run scheduled data import.
     */
    @Scheduled(cron = "${app.scheduled.data-import.cron}}")
    public void run() {
        if (applicationDataRepository.getLastRun() != null) {
            log.info("Running scheduled data import from Farm21 API");
            var measurements = soilScoutMeasurementIntegrationService.findAll(applicationDataRepository.getLastRun(), Instant.now());
            log.info("Found {} measurements", measurements.size());
            log.info("Persisting {} measurements", measurements.size());
            measurements.forEach(fiwareIntegrationServiceWrapper::persist);
        } else {
            log.info("Running initial data import from Farm21 API, this may take a while");
            var measurements = soilScoutMeasurementIntegrationService.findAll(Instant.now().minus(14, ChronoUnit.DAYS), Instant.now());
            log.info("Found {} measurements", measurements.size());
            log.info("Persisting {} measurements", measurements.size());
            measurements.forEach(fiwareIntegrationServiceWrapper::persist);
        }
        applicationDataRepository.updateLastRun();
    }

}
