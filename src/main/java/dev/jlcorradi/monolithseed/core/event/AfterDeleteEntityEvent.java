package dev.jlcorradi.monolithseed.core.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class AfterDeleteEntityEvent<K> extends ApplicationEvent {
    private final K payload;

    public AfterDeleteEntityEvent(Object source, K payload) {
        super(source);
        this.payload = payload;
    }
}
