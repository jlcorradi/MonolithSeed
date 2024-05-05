package dev.jlcorradi.mapstructdemo.core.mappers;

import dev.jlcorradi.mapstructdemo.common.dto.KeyValuePairDTO;
import dev.jlcorradi.mapstructdemo.core.entities.KeyValuePair;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface KeyValuePairMapper extends EntityMapper<KeyValuePairDTO, KeyValuePair> {

}
