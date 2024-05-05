package dev.jlcorradi.mapstructdemo.api;

import dev.jlcorradi.mapstructdemo.common.dto.KeyValuePairDTO;
import dev.jlcorradi.mapstructdemo.common.service.KeyValuePairService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(EndPointConstants.KEY_VALUE_PAIRS)
public class KeyValuePairApi extends AbstractCrudApi<KeyValuePairDTO, UUID, KeyValuePairService> {
    public KeyValuePairApi(KeyValuePairService service) {
        super(service);
    }
}
