package io.github.therealmone.redisson.example.service.impl;

import io.github.therealmone.redisson.example.model.Document;
import io.github.therealmone.redisson.example.repository.DocumentRepository;
import io.github.therealmone.redisson.example.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    @Override
    public Document getDocumentById(UUID documentId) {
        return documentRepository.findById(documentId).orElseThrow();
    }
}
