package dev.jlcorradi.mapstructdemo.common.dto;

import dev.jlcorradi.mapstructdemo.common.KeyValuePairType;

import java.util.UUID;

public record KeyValuePairDTO(
        UUID id,
        KeyValuePairType type,
        String description,
        boolean active
) {
}
