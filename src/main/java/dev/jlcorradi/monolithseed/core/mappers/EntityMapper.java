package dev.jlcorradi.monolithseed.core.mappers;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

public interface EntityMapper<D, T> {
  /**
   * <p>Maps dto to an entity. When receiving the payload from the client it is important that the id attribute
   * does not get passed on to the entity since most likely it will be incoming from a PUT request and
   * it's important to avoid the risk of having that value change which will impact hibernate persistence.
   * </p>
   * <p>
   * When overriding this method it's advisable to keep ignoring the id field. <br/>
   * <b>It is paramount to bring in the @MappingTarget annotation since AbstractCrudService#update
   * relies on it to populate the the existing entity with data coming from the DTO</b>
   * </p>
   *
   * @param target Must be annotated with @MappingTarget
   * @param dto    Dto
   * @return Entity
   */
  @Mapping(target = "id", ignore = true)
  T toEntity(@MappingTarget T target, D dto);

  D toDto(T entity);
}
