package de.app.fivegla.integration.fiware;


import de.app.fivegla.api.Constants;
import de.app.fivegla.fiware.DeviceIntegrationService;
import de.app.fivegla.fiware.DeviceMeasurementIntegrationService;
import de.app.fivegla.fiware.api.enums.DeviceCategoryValues;
import de.app.fivegla.fiware.model.Device;
import de.app.fivegla.fiware.model.DeviceCategory;
import de.app.fivegla.integration.farm21.model.Sensor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service for integration with FIWARE.
 */
@Slf4j
@Service
public class FiwareIntegrationServiceWrapper {
    private final DeviceIntegrationService deviceIntegrationService;
    private final DeviceMeasurementIntegrationService deviceMeasurementIntegrationService;

    public FiwareIntegrationServiceWrapper(DeviceIntegrationService deviceIntegrationService,
                                           DeviceMeasurementIntegrationService deviceMeasurementIntegrationService) {
        this.deviceIntegrationService = deviceIntegrationService;
        this.deviceMeasurementIntegrationService = deviceMeasurementIntegrationService;
    }

    private static String getFiwareId(int sensorId) {
        return Constants.FIWARE_FARM21_SENSOR_ID_PREFIX + sensorId;
    }

    private static String getFiwareId() {
        return Constants.FIWARE_FARM21_SENSOR_ID_PREFIX + UUID.randomUUID();
    }

    /**
     * Create a new Farm21 sensor in FIWARE.
     * @param sensor
     */
    public void persist(Sensor sensor) {
        var device = Device.builder()
                .id(getFiwareId(sensor.getId()))
                .deviceCategory(DeviceCategory.builder()
                        .value(List.of(DeviceCategoryValues.Farm21Sensor.getKey()))
                        .build())
                .build();
        deviceIntegrationService.persist(device);
    }
}
