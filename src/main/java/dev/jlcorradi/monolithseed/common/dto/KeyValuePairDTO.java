package dev.jlcorradi.monolithseed.common.dto;

import dev.jlcorradi.monolithseed.common.KeyValuePairType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record KeyValuePairDTO(
    UUID id,
    @NotNull
    KeyValuePairType type,
    @NotEmpty
    String description,
    boolean active
) {
}
