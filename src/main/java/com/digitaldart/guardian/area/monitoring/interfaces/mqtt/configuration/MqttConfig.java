package com.digitaldart.guardian.area.monitoring.interfaces.mqtt.configuration;

import com.digitaldart.guardian.area.monitoring.domain.services.DeviceCommandService;
import com.digitaldart.guardian.area.monitoring.interfaces.rest.resource.DeviceHealthMeasureResource;
import com.digitaldart.guardian.area.monitoring.interfaces.rest.transform.UpdateHealthThresholdsCommandFromResourceAssembler;
import com.digitaldart.guardian.area.monitoring.interfaces.websocket.resource.CurrentLocationResource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfig {
    @Value("${mqtt.url}")
    private String MQTT_BROKER_URL;

    @Value("${mqtt.client}")
    private String CLIENT_ID;

    @Value("${mqtt.credentials.username}")
    private String USERNAME;

    @Value("${mqtt.credentials.password}")
    private String PASSWORD;

    private final DeviceCommandService deviceCommandService;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final String[] SUBSCRIBE_TOPICS = {
            "guardian-area/health-thresholds"
    };

    public MqttConfig(DeviceCommandService deviceCommandService) {
        this.deviceCommandService = deviceCommandService;
    }

    @Bean
    public MqttClient mqttClient() throws MqttException {
        MqttClient client = new MqttClient(MQTT_BROKER_URL, CLIENT_ID, new MemoryPersistence());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setUserName(USERNAME);
        options.setPassword(PASSWORD.toCharArray());

        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                System.out.println("Connection lost: " + cause.getMessage());
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                var response = new String(message.getPayload());
                System.out.println("Message received on topic: " + topic + ". Content: " + response);
                // Procesamiento según el topic
                switch (topic) {
                    case "guardian-area/health-thresholds":
                        try {
                            var responseObj = objectMapper.readValue(response, DeviceHealthMeasureResource.class);
                            var command = UpdateHealthThresholdsCommandFromResourceAssembler.toCommandFromResource(responseObj);
                            deviceCommandService.handle(command);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    default:
                        System.out.println("Unrecognized topic: " + topic);
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                // Esta función es opcional para suscripciones
            }
        });

        client.connect(options);
        for (String topic : SUBSCRIBE_TOPICS) {
            client.subscribe(topic);
        }
        return client;
    }

}

