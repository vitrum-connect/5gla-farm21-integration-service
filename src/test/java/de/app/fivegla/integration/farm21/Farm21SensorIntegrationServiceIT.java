package de.app.fivegla.integration.farm21;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Farm21SensorIntegrationServiceIT {

    @Autowired
    private SensorIntegrationService farm21SensorIntegrationService;

    @Test
    void givenExistingSensorsWhenSearchingViaApiTheServiceShouldReturnAllOfThem() {
        var sensors = farm21SensorIntegrationService.findAll();
        Assertions.assertNotNull(sensors);
        Assertions.assertFalse(sensors.isEmpty());
    }

}