package dev.jlcorradi.monolithseed.core.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnDeleteEntityEvent<K> extends ApplicationEvent {
    private final K payload;

    public OnDeleteEntityEvent(Object source, K payload) {
        super(source);
        this.payload = payload;
    }
}
