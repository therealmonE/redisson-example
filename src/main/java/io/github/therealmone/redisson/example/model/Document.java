package io.github.therealmone.redisson.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;

import java.util.UUID;

@Data
@org.springframework.data.mongodb.core.mapping.Document("document")
@JsonIgnoreProperties(ignoreUnknown = false)
@ApiModel
public class Document implements Persistable<UUID> {

    @Id
    private UUID id;

    @Override
    @JsonIgnore
    public boolean isNew() {
        return this.getId() == null;
    }
}
