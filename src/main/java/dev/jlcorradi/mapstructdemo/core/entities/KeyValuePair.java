package dev.jlcorradi.mapstructdemo.core.entities;

import dev.jlcorradi.mapstructdemo.common.KeyValuePairType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class KeyValuePair implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private KeyValuePairType type;
    private String description;
    private boolean active;
}
