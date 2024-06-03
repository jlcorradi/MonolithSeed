package dev.jlcorradi.monolithseed.core.mappers;

import dev.jlcorradi.monolithseed.common.dto.KeyValuePairDTO;
import dev.jlcorradi.monolithseed.core.entities.KeyValuePair;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface KeyValuePairMapper extends EntityMapper<KeyValuePairDTO, KeyValuePair> {

}
