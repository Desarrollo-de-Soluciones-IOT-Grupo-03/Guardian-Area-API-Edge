package com.digitaldart.guardian.area.monitoring.interfaces.rest.transform;

import com.digitaldart.guardian.area.monitoring.domain.model.aggregates.Device;
import com.digitaldart.guardian.area.monitoring.interfaces.rest.resource.DeviceResource;

public class DeviceResourceFromEntityAssembler {
    public static DeviceResource toResourceFromEntity(Device device){
        return new DeviceResource(
                device.getDeviceRecordId(),
                device.getDeviceStatuses().toString(),
                device.getApiKey().apiKey()
        );
    }
}
