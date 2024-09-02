package dev.jlcorradi.monolithseed.core.service;

import dev.jlcorradi.monolithseed.common.dto.KeyValuePairDTO;
import dev.jlcorradi.monolithseed.common.service.KeyValuePairService;
import dev.jlcorradi.monolithseed.core.entities.KeyValuePair;
import dev.jlcorradi.monolithseed.core.mappers.KeyValuePairMapper;
import dev.jlcorradi.monolithseed.core.repository.KeyValuePairRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class KeyValuePairServiceImpl
        extends AbstractCrudService<KeyValuePairDTO, KeyValuePair, UUID, KeyValuePairRepository, KeyValuePairMapper>
        implements KeyValuePairService {

    public KeyValuePairServiceImpl(KeyValuePairRepository repository,
                                   KeyValuePairMapper mapper,
                                   ApplicationEventPublisher eventPublisher) {
        super(repository, mapper, eventPublisher);
    }
}
