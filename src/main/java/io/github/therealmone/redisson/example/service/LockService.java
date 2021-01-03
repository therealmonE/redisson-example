package io.github.therealmone.redisson.example.service;

import io.github.therealmone.redisson.example.model.Document;

import java.util.UUID;
import java.util.function.Consumer;

public interface LockService {

    void lockDocument(UUID documentId, Consumer<Document> documentConsumer);

    void lockDocumentAsync(UUID documentId, Consumer<Document> documentConsumer);

}
