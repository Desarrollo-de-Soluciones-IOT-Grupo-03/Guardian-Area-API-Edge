package com.digitaldart.guardian.area.monitoring.application.internal.queyservices;

import com.digitaldart.guardian.area.monitoring.domain.model.aggregates.Device;
import com.digitaldart.guardian.area.monitoring.domain.model.queries.GetAllDevicesByUserIdQuery;
import com.digitaldart.guardian.area.monitoring.domain.model.queries.GetDeviceByGuardianAreaDeviceRecordIdQuery;
import com.digitaldart.guardian.area.monitoring.domain.services.DeviceQueryService;
import com.digitaldart.guardian.area.monitoring.infrastructure.persistence.jpa.repositories.DeviceRepository;
import com.digitaldart.guardian.area.shared.domain.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceQueryServiceImpl implements DeviceQueryService {

    private final DeviceRepository deviceRepository;

    public DeviceQueryServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }


    @Override
    public Optional<Device> handle(GetDeviceByGuardianAreaDeviceRecordIdQuery query) {
        var device = deviceRepository.findByGuardianAreaDeviceRecordId(query.guardianAreaDeviceRecordId());
        return device;
    }
}
