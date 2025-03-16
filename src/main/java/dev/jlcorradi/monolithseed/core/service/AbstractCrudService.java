package dev.jlcorradi.monolithseed.core.service;

import dev.jlcorradi.monolithseed.common.EntityNotFoundException;
import dev.jlcorradi.monolithseed.common.service.CrudService;
import dev.jlcorradi.monolithseed.core.entities.BaseEntity;
import dev.jlcorradi.monolithseed.core.event.CrudOperation;
import dev.jlcorradi.monolithseed.core.event.CrudOperationEventHandler;
import dev.jlcorradi.monolithseed.core.mappers.EntityMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractCrudService<D, T extends BaseEntity, K, R extends JpaRepository<T, K>, M extends EntityMapper<D, T>>
    implements CrudService<D, K> {

  public static final int ENTITY_GENERIC_PARAM_IDX = 1;
  private final R repository;
  private final M mapper;

  private final List<CrudOperationEventHandler<T>> handlers;

  @SuppressWarnings("unchecked")
  public static <T> T getInstanceOfTypeParameter(Class<?> contextClass, int paramIndex) {
    ParameterizedType superClass = (ParameterizedType) contextClass.getGenericSuperclass();
    try {
      Class<T> actualTypeArgument = (Class<T>) superClass.getActualTypeArguments()[paramIndex];
      Constructor<T> constructor = actualTypeArgument.getConstructor();
      return constructor.newInstance();
    } catch (Exception e) {
      // Oops, no default constructor
      throw new IllegalArgumentException(
          String.format("Default constructor not found for class %s", superClass.getTypeName()), e);
    }
  }

  @Override
  @Transactional
  public D create(D dto) {
    T entity = mapper.toEntity(getInstanceOfTypeParameter(super.getClass(), ENTITY_GENERIC_PARAM_IDX), dto);
    performValidations(entity);
    T newlySaved = repository.save(entity);
    notifyListeners(newlySaved, CrudOperation.INSERT);
    return mapper.toDto(newlySaved);
  }

  @Transactional
  @Override
  public D update(K id, D dto) {
    T entity = repository.findById(id).orElseThrow(EntityNotFoundException::new);
    performValidations(entity);
    T newlyUpdated = mapper.toEntity(entity, dto);
    T updated = repository.save(newlyUpdated);
    notifyListeners(newlyUpdated, CrudOperation.UPDATE);
    return mapper.toDto(updated);
  }

  @Override
  public D get(K id) {
    return repository.findById(id)
        .map(mapper::toDto)
        .orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public void delete(K id) {
    repository.findById(id)
        .ifPresent(t -> {
          repository.deleteById(id);
          notifyListeners(t, CrudOperation.DELETE);
        });
  }

  @Override
  public Page<D> list(PageRequest pageRequest) {
    Page<T> all = repository.findAll(pageRequest);
    List<D> content = all.getContent().stream().map(mapper::toDto).toList();
    return new PageImpl<>(content, pageRequest, all.getTotalElements());
  }

  /***
   * Perform validations before saving.
   * <p>
   * Override this method for best abstraction
   */
  public void performValidations(T entity) {
  }

  private void notifyListeners(T entity, CrudOperation crudOperation) {
    handlers.stream().filter(handler -> handler.supports(entity, crudOperation))
        .forEach(handler -> handler.onPerformCrudOperation(this, entity, crudOperation));
  }
}
