package dev.jlcorradi.monolithseed.core.service;

import dev.jlcorradi.monolithseed.common.EntityNotFoundException;
import dev.jlcorradi.monolithseed.common.service.CrudService;
import dev.jlcorradi.monolithseed.core.entities.BaseEntity;
import dev.jlcorradi.monolithseed.core.event.AfterDeleteEntityEvent;
import dev.jlcorradi.monolithseed.core.event.AfterSaveEntityEvent;
import dev.jlcorradi.monolithseed.core.event.OnDeleteEntityEvent;
import dev.jlcorradi.monolithseed.core.event.OnSaveEntityEvent;
import dev.jlcorradi.monolithseed.core.mappers.EntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractCrudService<D, T extends BaseEntity, K, R extends JpaRepository<T, K>, M extends EntityMapper<D, T>>
        implements CrudService<D, K> {

    public static final int ENTITY_GENERIC_PARAM_IDX = 1;
    private final R repository;
    private final M mapper;

    private final ApplicationEventPublisher eventPublisher;

    @Override
    @org.springframework.transaction.annotation.Transactional
    public D create(D dto) {
        log.debug("Creating object: \n{}", dto);
        T entity = mapper.dto2Entity(getInstanceOfTypeParameter(super.getClass(), ENTITY_GENERIC_PARAM_IDX), dto);
        performValidations(entity);
        eventPublisher.publishEvent(new OnSaveEntityEvent<>(this, entity));
        T newlySaved = repository.save(entity);
        eventPublisher.publishEvent(new AfterSaveEntityEvent<>(this, newlySaved));
        return mapper.entity2Dto(newlySaved);
    }

    @Transactional
    @Override
    public D update(K id, D dto) {
        log.debug("Updating object: \n{}", dto);
        T entity = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        performValidations(entity);
        log.debug("Publishing OnSaveEntityEvent: \n{}", entity);
        eventPublisher.publishEvent(new OnSaveEntityEvent<>(this, entity));
        T newlySaved = mapper.dto2Entity(entity, dto);
        T updated = repository.save(newlySaved);
        log.debug("Publishing AfterSaveEntityEvent: \n{}", entity);
        eventPublisher.publishEvent(new AfterSaveEntityEvent<>(this, newlySaved));
        return mapper.entity2Dto(updated);
    }

    @Override
    public D get(K id) {
        return repository.findById(id)
                .map(mapper::entity2Dto)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void delete(K id) {
        log.debug("Publishing OnDeleteEntityEvent: \n{}", id);
        eventPublisher.publishEvent(new OnDeleteEntityEvent<>(this, id));
        repository.deleteById(id);
        log.debug("Publishing AfterDeleteEntityEvent: \n{}", id);
        eventPublisher.publishEvent(new AfterDeleteEntityEvent<>(this, id));
    }

    @Override
    public Page<D> list(PageRequest pageRequest) {
        Page<T> all = repository.findAll(pageRequest);
        List<D> content = all.getContent().stream().map(mapper::entity2Dto).toList();
        return new PageImpl<>(content, pageRequest, all.getTotalElements());
    }

    /***
     * Perform validations before saving.
     * <p>
     * Override this method for best abstraction
     */
    public void performValidations(T entity) {
    }

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
}
