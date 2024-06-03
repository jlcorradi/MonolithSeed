package dev.jlcorradi.monolithseed.core.repository;

import dev.jlcorradi.monolithseed.core.entities.KeyValuePair;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface KeyValuePairRepository extends JpaRepository<KeyValuePair, UUID> {
}
