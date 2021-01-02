package io.github.therealmone.redisson.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;

import java.util.UUID;

@Data
@org.springframework.data.mongodb.core.mapping.Document("document")
@JsonIgnoreProperties(ignoreUnknown = false)
public class Document implements Persistable<UUID> {

    @Id
    private UUID id;

    @Override
    public boolean isNew() {
        return this.getId() == null;
    }
}
