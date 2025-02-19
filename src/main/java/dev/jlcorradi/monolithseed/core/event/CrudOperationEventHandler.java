package dev.jlcorradi.monolithseed.core.event;

import dev.jlcorradi.monolithseed.core.entities.BaseEntity;

import java.util.Arrays;
import java.util.Optional;

public interface CrudOperationEventHandler<T extends BaseEntity> {
  void onPerformCrudOperation(Object source, T target, CrudOperation operation);

  default boolean supports(T entity, CrudOperation operation) {
    return Optional.ofNullable(getClass().getDeclaredAnnotation(CrudEventListener.class))
        .map(crudEventListener -> Arrays.asList(crudEventListener.supports()).contains(operation))
        .orElse(true);
  }

  default int getOrder() {
    return Optional.ofNullable(getClass().getDeclaredAnnotation(CrudEventListener.class))
        .map(CrudEventListener::order)
        .orElse(Integer.MAX_VALUE);
  }
}
