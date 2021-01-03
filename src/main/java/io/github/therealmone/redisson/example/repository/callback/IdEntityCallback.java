package io.github.therealmone.redisson.example.repository.callback;

import io.github.therealmone.redisson.example.model.Document;
import org.springframework.core.Ordered;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertCallback;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class IdEntityCallback implements BeforeConvertCallback<Document>, Ordered {

    @Override
    public Document onBeforeConvert(Document entity, String collection) {
        if (entity.isNew()) {
            entity.setId(UUID.randomUUID());
        }
        return entity;
    }

    @Override
    public int getOrder() {
        return 101;
    }
}
