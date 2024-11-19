package com.digitaldart.guardian.area.monitoring.domain.model.aggregates;

import com.digitaldart.guardian.area.monitoring.domain.model.commands.AssignDeviceCommand;
import com.digitaldart.guardian.area.monitoring.domain.model.commands.RegisterDeviceCommand;
import com.digitaldart.guardian.area.monitoring.domain.model.commands.UpdateDeviceCommand;
import com.digitaldart.guardian.area.monitoring.domain.model.commands.UpdateHealthThresholdsCommand;
import com.digitaldart.guardian.area.monitoring.domain.model.valueobjects.*;
import com.digitaldart.guardian.area.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Device extends AuditableAbstractAggregateRoot<Device> {

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private DeviceStatuses deviceStatuses;

    @Getter
    @Embedded
    @Column(name = "guardian_area_device_id")
    private GuardianAreaDeviceRecordId guardianAreaDeviceRecordId;

    @Getter
    @Embedded
    private ApiKey apiKey;

    @Getter
    @Embedded
    private HealthThresholds healthThresholds;

    public Device() {
        this.healthThresholds = new HealthThresholds();
        this.deviceStatuses = DeviceStatuses.DISCONNECTED;
        this.guardianAreaDeviceRecordId = new GuardianAreaDeviceRecordId();
    }

    public void assignDevice(AssignDeviceCommand command){
        this.guardianAreaDeviceRecordId = command.guardianAreaDeviceRecordId();
        this.deviceStatuses = DeviceStatuses.CONNECTED;
    }

    public Device(RegisterDeviceCommand command) {
        this.healthThresholds = new HealthThresholds();
        this.deviceStatuses = DeviceStatuses.CONNECTED;
        this.guardianAreaDeviceRecordId = command.guardianAreaDeviceRecordId();
    }

    public void updateDevice(UpdateDeviceCommand command){
        this.deviceStatuses = command.deviceStatuses();
    }

    public void UpdateHealthThresholds(UpdateHealthThresholdsCommand command) {
        this.healthThresholds = new HealthThresholds(command.minBpm(), command.maxBpm(),command.minSpO2(), command.maxSpO2());

    }

    public String getDeviceRecordId() {
        return this.guardianAreaDeviceRecordId.deviceRecordId();
    }

}
