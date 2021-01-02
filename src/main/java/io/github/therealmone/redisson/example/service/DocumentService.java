package io.github.therealmone.redisson.example.service;

import io.github.therealmone.redisson.example.model.Document;

import java.util.UUID;

public interface DocumentService {

    Document getDocumentById(UUID documentId);

}
