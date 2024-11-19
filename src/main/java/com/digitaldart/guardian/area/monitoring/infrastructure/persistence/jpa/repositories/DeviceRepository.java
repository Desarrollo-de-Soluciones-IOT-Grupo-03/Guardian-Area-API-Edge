package com.digitaldart.guardian.area.monitoring.infrastructure.persistence.jpa.repositories;

import com.digitaldart.guardian.area.monitoring.domain.model.aggregates.Device;
import com.digitaldart.guardian.area.monitoring.domain.model.valueobjects.ApiKey;
import com.digitaldart.guardian.area.monitoring.domain.model.valueobjects.GuardianAreaDeviceRecordId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    Optional<Device> findByGuardianAreaDeviceRecordId(GuardianAreaDeviceRecordId guardianAreaDeviceRecordId);
    boolean existsByGuardianAreaDeviceRecordId(GuardianAreaDeviceRecordId guardianAreaDeviceRecordId);
    Optional<Device> findByGuardianAreaDeviceRecordIdAndApiKey(GuardianAreaDeviceRecordId guardianAreaDeviceRecordId, ApiKey apiKey);
}
