package dev.jlcorradi.monolithseed.core.service;

import dev.jlcorradi.monolithseed.common.dto.KeyValuePairDTO;
import dev.jlcorradi.monolithseed.common.service.KeyValuePairService;
import dev.jlcorradi.monolithseed.core.entities.KeyValuePair;
import dev.jlcorradi.monolithseed.core.event.CrudOperationEventHandler;
import dev.jlcorradi.monolithseed.core.mappers.KeyValuePairMapper;
import dev.jlcorradi.monolithseed.core.repository.KeyValuePairRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class KeyValuePairServiceImpl
        extends AbstractCrudService<KeyValuePairDTO, KeyValuePair, UUID, KeyValuePairRepository, KeyValuePairMapper>
        implements KeyValuePairService {


    public KeyValuePairServiceImpl(KeyValuePairRepository repository, KeyValuePairMapper mapper,
                                   List<CrudOperationEventHandler<KeyValuePair>> crudOperationEventHandlers) {
        super(repository, mapper, crudOperationEventHandlers);
    }
}
