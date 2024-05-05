package dev.jlcorradi.mapstructdemo.core.repository;

import dev.jlcorradi.mapstructdemo.core.entities.KeyValuePair;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface KeyValuePairRepository extends JpaRepository<KeyValuePair, UUID> {
}
