package dev.jlcorradi.mapstructdemo.core.service;

import dev.jlcorradi.mapstructdemo.common.EntityNotFoundException;
import dev.jlcorradi.mapstructdemo.common.service.CrudService;
import dev.jlcorradi.mapstructdemo.core.mappers.EntityMapper;
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
public abstract class AbstractCrudService<D, T, K, R extends JpaRepository<T, K>, M extends EntityMapper<D, T>>
        implements CrudService<D, K> {

    public static final int ENTITY_GENERIC_PARAM_IDX = 1;
    private final R repository;
    private final M mapper;

    @Override
    @Transactional
    public D create(D dto) {
        T entity = mapper.dto2Entity(getInstanceOfTypeParameter(super.getClass(), ENTITY_GENERIC_PARAM_IDX), dto);
        performValidations(entity);
        return mapper.entity2Dto(repository.save(entity));
    }

    @Transactional
    @Override
    public D update(K id, D dto) {
        T entity = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        performValidations(entity);
        T updated = repository.save(mapper.dto2Entity(entity, dto));
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
        repository.deleteById(id);
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
