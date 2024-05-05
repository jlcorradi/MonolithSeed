package dev.jlcorradi.mapstructdemo.core.service;

import dev.jlcorradi.mapstructdemo.common.dto.KeyValuePairDTO;
import dev.jlcorradi.mapstructdemo.common.service.KeyValuePairService;
import dev.jlcorradi.mapstructdemo.core.entities.KeyValuePair;
import dev.jlcorradi.mapstructdemo.core.mappers.KeyValuePairMapper;
import dev.jlcorradi.mapstructdemo.core.repository.KeyValuePairRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class KeyValuePairServiceImpl
        extends AbstractCrudService<KeyValuePairDTO, KeyValuePair, UUID, KeyValuePairRepository, KeyValuePairMapper>
        implements KeyValuePairService {

    public KeyValuePairServiceImpl(KeyValuePairRepository repository, KeyValuePairMapper mapper) {
        super(repository, mapper);
    }
}
