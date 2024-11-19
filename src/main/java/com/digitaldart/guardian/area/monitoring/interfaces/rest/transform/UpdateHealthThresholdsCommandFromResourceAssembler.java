package com.digitaldart.guardian.area.monitoring.interfaces.rest.transform;

import com.digitaldart.guardian.area.monitoring.domain.model.commands.UpdateHealthThresholdsCommand;
import com.digitaldart.guardian.area.monitoring.domain.model.valueobjects.GuardianAreaDeviceRecordId;
import com.digitaldart.guardian.area.monitoring.interfaces.rest.resource.DeviceHealthMeasureResource;

public class UpdateHealthThresholdsCommandFromResourceAssembler {
    public static UpdateHealthThresholdsCommand toCommandFromResource(DeviceHealthMeasureResource resource){
        return new UpdateHealthThresholdsCommand(
                new GuardianAreaDeviceRecordId(resource.deviceRecordId()),
                resource.minBpm(),
                resource.maxBpm(),
                resource.minSpO2(),
                resource.maxSpO2()
        );
    }
}
