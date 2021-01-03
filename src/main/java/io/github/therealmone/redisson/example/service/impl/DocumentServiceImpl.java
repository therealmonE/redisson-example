package io.github.therealmone.redisson.example.service.impl;

import io.github.therealmone.redisson.example.dto.UpdateDocumentRequest;
import io.github.therealmone.redisson.example.model.Document;
import io.github.therealmone.redisson.example.repository.DocumentRepository;
import io.github.therealmone.redisson.example.service.DocumentService;
import io.github.therealmone.redisson.example.service.LockService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final LockService lockService;

    @Override
    public Document getDocumentById(UUID documentId) {
        return documentRepository.findById(documentId).orElseThrow();
    }

    @Override
    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    @Override
    public Document createNewDocument() {
        val document = new Document();

        document.setLocked(false);

        return documentRepository.save(document);
    }

    @Override
    @SneakyThrows
    public void updateDocument(UUID documentId, UpdateDocumentRequest updateDocumentRequest) {
        lockService.lockDocument(documentId, document ->
                document.setData(updateDocumentRequest.getData()));
    }
}
