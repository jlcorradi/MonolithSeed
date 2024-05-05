package dev.jlcorradi.mapstructdemo.core.mappers;

import org.mapstruct.MappingTarget;

public interface EntityMapper<D, T> {
    T dto2Entity(@MappingTarget T target, D dto);
    D entity2Dto(T entity);
}
