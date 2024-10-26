package dev.jlcorradi.monolithseed.core.service;

import dev.jlcorradi.monolithseed.core.entities.KeyValuePair;
import dev.jlcorradi.monolithseed.core.event.CrudEventListener;
import dev.jlcorradi.monolithseed.core.event.CrudOperation;
import dev.jlcorradi.monolithseed.core.event.CrudOperationEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@CrudEventListener(supports = CrudOperation.INSERT)
public class KeyValuePairOnSaveEventHandler implements CrudOperationEventHandler<KeyValuePair> {
    @Override
    public void onPerformCrudOperation(Object source, KeyValuePair target, CrudOperation operation) {
        log.info("Will Save entity: {} target", target);
    }

}
