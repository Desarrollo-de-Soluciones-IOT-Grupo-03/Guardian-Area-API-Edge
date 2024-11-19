package com.digitaldart.guardian.area.shared.intefaces.rest.resources;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public record ErrorMessage(int statusCode, String message, String description, LocalDateTime timestamp) {
    public ErrorMessage(int statusCode, String message, String description) {
        this(statusCode, message, description, LocalDateTime.now(ZoneOffset.UTC));
    }
}
