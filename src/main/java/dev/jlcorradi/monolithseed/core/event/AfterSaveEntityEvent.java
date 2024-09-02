package dev.jlcorradi.monolithseed.core.event;

import dev.jlcorradi.monolithseed.core.entities.BaseEntity;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class AfterSaveEntityEvent<T extends BaseEntity> extends ApplicationEvent {

    private final T payload;

    public AfterSaveEntityEvent(Object source, T payload) {
        super(source);
        this.payload = payload;
    }
}
