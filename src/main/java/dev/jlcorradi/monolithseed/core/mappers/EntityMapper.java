package dev.jlcorradi.monolithseed.core.mappers;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

public interface EntityMapper<D, T> {
  @Mapping(target = "id", ignore = true)
  T toEntity(@MappingTarget T target, D dto);

  D toDto(T entity);
}
