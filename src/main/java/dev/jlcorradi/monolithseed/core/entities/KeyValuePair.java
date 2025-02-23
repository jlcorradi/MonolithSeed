package dev.jlcorradi.monolithseed.core.entities;

import dev.jlcorradi.monolithseed.common.KeyValuePairType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Data
@Entity
public class KeyValuePair implements BaseEntity {
  @Id
  @GeneratedValue(generator = "UUID")
  @JdbcTypeCode(SqlTypes.VARCHAR)
  private UUID id;

  @Enumerated(EnumType.STRING)
  private KeyValuePairType type;
  private String description;
  private boolean active;
}
